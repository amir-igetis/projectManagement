package com.amirul.projectmanagement.controller;

import com.amirul.projectmanagement.model.Chat;
import com.amirul.projectmanagement.model.Message;
import com.amirul.projectmanagement.model.User;
import com.amirul.projectmanagement.request.CreateMessageRequest;
import com.amirul.projectmanagement.service.MessageService;
import com.amirul.projectmanagement.service.ProjectService;
import com.amirul.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request)
            throws Exception {
        User user = userService.findUserById(request.getSenderId());
//        you dont have to add the user not found exception line
        if (user == null)
            throw new Exception("User not found with id " + request.getSenderId());

        Chat chat = projectService.getProjectById(request.getProjectId()).getChat();
        if (chat == null)
            throw new Exception("Chats no found");
        Message sentMessage = messageService.sendMessage(request.getSenderId(),
                request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId)
            throws Exception {

        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }

}
