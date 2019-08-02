package com.example.composertestapi.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupDTO {

    @JsonProperty("group_id")
    private Integer groupId;

    @JsonProperty("name")
    private String groupName;

    @JsonProperty("description")
    private String description;

    public Integer getGroupId() {
        return groupId;
    }

    public GroupDTO setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupDTO setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GroupDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
