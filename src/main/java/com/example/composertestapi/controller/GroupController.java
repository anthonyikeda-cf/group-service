package com.example.composertestapi.controller;

import com.example.composertestapi.dao.GroupDAO;
import com.example.composertestapi.dto.GroupDTO;
import com.example.composertestapi.service.GroupService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group/v1")
public class GroupController {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Counter groupCreateCounter;
    private Counter groupDeleteCounter;
    private Timer groupTimer;
    private Timer getTimer;
    private GroupService groupService;

    @Autowired
    public GroupController(MeterRegistry registry, GroupService service) {
        this.groupCreateCounter = registry.counter("group.create");
        this.groupDeleteCounter = registry.counter("group.delete");
        this.groupTimer = registry.timer("create.group");
        this.getTimer = registry.timer("get.groups.all");

        this.groupService = service;
    }

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody GroupDTO group) {
        return groupTimer.record(() -> {
            GroupDAO dao = new GroupDAO();
            dao.setGroupName(group.getGroupName());
            dao.setGroupDescription(group.getDescription());
            GroupDAO saved = this.groupService.saveGroup(dao);

            groupCreateCounter.increment();

            GroupDTO dto = convertDAO(saved);
            this.groupService.indexGroup(dto);

            URI uri = URI.create("/group/v1/" + saved.getGroupId());
            return ResponseEntity.created(uri).build();
        });
    }

    @DeleteMapping("/{group_id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("group_id") Integer groupId) {
        return groupTimer.record(() -> {
            this.groupService.deleteGroup(groupId);
            groupDeleteCounter.increment();
            return ResponseEntity.ok().build();
        });
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getGroups(@RequestParam(name = "_limit", defaultValue = "100") Integer batchSize,
                                                    @RequestParam(name = "_offset", defaultValue = "0") Integer offset,
                                                    HttpServletRequest request,
                                                    Principal subject, @AuthenticationPrincipal Object jwt) {
        return getTimer.record(()-> {
            log.debug(jwt.toString());
            log.debug("Username is: {}, Class: {}", subject.getName(), subject.getClass().getName());
            OAuth2Authentication auth = (OAuth2Authentication) subject;
            log.debug("Credentials: {}, Credentials class: {}",auth.getCredentials(), auth.getCredentials().getClass().getName());

//            auth.getOAuth2Request().getAuthorities().contains("")
            log.debug("Auth Details: {}", auth.getOAuth2Request().getExtensions());
            String hostname = request.getHeader("Host");

            Page<GroupDAO> results = this.groupService.findAll(offset, batchSize);

            String nextUrl = "http://"+ hostname +"/group/v1?_offset=" + (offset + 1) + "&_limit=" + batchSize;
            String link = "<" + nextUrl + ">; rel = \"next\"; title = \"next page\"; total = " + results.getTotalElements();

            List<GroupDTO> groups = results.get()
                    .map(this::convertDAO)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).header("link", link).body(groups);
        });
    }

    @GetMapping("/{group_id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable("group_id") Integer id, @AuthenticationPrincipal Object jwt) {
    log.debug(jwt.toString());
        return groupTimer.record(() -> {
            GroupDAO dao = this.groupService.findGroupById(id);
            GroupDTO group = convertDAO(dao);
            return ResponseEntity.ok(group);
        });

    }

    private GroupDTO convertDAO(GroupDAO dao) {
        GroupDTO group = new GroupDTO();
        group.setGroupId(dao.getGroupId());
        group.setGroupName(dao.getGroupName());
        group.setDescription(dao.getGroupDescription());
        return group;
    }
}
