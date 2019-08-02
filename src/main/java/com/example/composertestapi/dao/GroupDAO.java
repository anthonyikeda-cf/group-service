package com.example.composertestapi.dao;

import javax.persistence.*;

@Entity
public class GroupDAO {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_description")
    private String groupDescription;

    public Integer getGroupId() {
        return groupId;
    }

    public GroupDAO setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupDAO setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public GroupDAO setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
        return this;
    }
}
