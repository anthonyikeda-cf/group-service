package com.example.composertestapi.service;

import com.example.composertestapi.dao.GroupDAO;
import com.example.composertestapi.dto.GroupDTO;
import com.example.composertestapi.publisher.GroupIndexPublisher;
import com.example.composertestapi.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GroupServiceImpl implements GroupService {

    private Logger log = LoggerFactory.getLogger(getClass());
    private GroupIndexPublisher indexPublisher;
    private GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository repository, GroupIndexPublisher publisher) {
        this.indexPublisher = publisher;
        this.groupRepository = repository;
    }

    @Override
    public void indexGroup(GroupDTO group) {
        log.debug("Indexing group {}", group.getGroupName());
        this.indexPublisher.indexGroup(group);
    }

    @Override
    public GroupDAO saveGroup(GroupDAO groupDAO) {
        return this.groupRepository.save(groupDAO);
    }

    @Override
    public void deleteGroup(Integer groupId) {
        Optional<GroupDAO> toDelete = this.groupRepository.findById(groupId);

        toDelete.ifPresent(group -> {
            log.debug("Deleting group {}", group.getGroupName());
            this.groupRepository.deleteById(groupId);
            GroupDTO dto = new GroupDTO();
            dto.setGroupId(group.getGroupId());
            dto.setGroupName(group.getGroupName());
            dto.setDescription(group.getGroupDescription());
            this.indexPublisher.deIndexGroup(dto);
        });
    }

    public Page<GroupDAO> findAll(Integer offset, Integer batchSize) {
        Pageable page = PageRequest.of(offset, batchSize);
        return this.groupRepository.findAll(page);
    }

    public GroupDAO findGroupById(Integer groupId) {
        return this.groupRepository.getOne(groupId);
    }
}
