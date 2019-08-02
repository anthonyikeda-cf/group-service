package com.example.composertestapi;

import com.example.composertestapi.dao.GroupDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupDAO, Integer>{

}
