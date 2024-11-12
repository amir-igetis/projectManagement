package com.amirul.projectmanagement.controller;

import com.amirul.projectmanagement.model.Chat;
import com.amirul.projectmanagement.model.Invitation;
import com.amirul.projectmanagement.model.Project;
import com.amirul.projectmanagement.model.User;
import com.amirul.projectmanagement.request.InviteRequest;
import com.amirul.projectmanagement.response.MessageResponse;
import com.amirul.projectmanagement.service.InvitationService;
import com.amirul.projectmanagement.service.ProjectService;
import com.amirul.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping()
    public ResponseEntity<List<Project>> getProjects(@RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String tag,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);


        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);


        return new ResponseEntity<>(project, HttpStatus.OK);
    }

//    @GetMapping("{/userId}")
//    public ResponseEntity<Project> getProjectByUserId(
//            @RequestHeader("Authorization")String token,
//            @PathVariable Long userId
//    ) throws Exception{
//
//        User user = userService.findUserProfileByJwt(token);
//        Project project = projectService.getProjectById(userId)
//    }

    @PostMapping()
    public ResponseEntity<Project> createProject(@RequestHeader("Authorization") String jwt,
                                                 @RequestBody Project project) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);


        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(project, projectId);

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse res = new MessageResponse("Project deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProject(keyword, user);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{ProjectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody InviteRequest req,
            @RequestBody Project project) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(), req.getProjectId());
        MessageResponse res = new MessageResponse("User invitation sent");

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInvitationProject(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String token,
            @RequestBody Project project) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);

    }


}
