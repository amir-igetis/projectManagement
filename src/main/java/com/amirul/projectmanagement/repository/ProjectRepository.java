package com.amirul.projectmanagement.repository;

import com.amirul.projectmanagement.model.Project;
import com.amirul.projectmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, Long> {

//    List<Project> findByOwner(User user);

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

//    @Query("SELECT p FROM Project P JOIN p.team t WHERE t=:user")
//    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);


}
