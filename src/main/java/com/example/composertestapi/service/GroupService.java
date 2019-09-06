package com.example.composertestapi.service;

import com.example.composertestapi.dao.GroupDAO;
import com.example.composertestapi.dto.GroupDTO;
import org.springframework.data.domain.Page;

public interface GroupService {

    void indexGroup(GroupDTO group);
    GroupDAO saveGroup(GroupDAO groupDAO);
    void deleteGroup(Integer groupId);
    Page<GroupDAO> findAll(Integer offset, Integer bacthSize);
    GroupDAO findGroupById(Integer groupId);
}
