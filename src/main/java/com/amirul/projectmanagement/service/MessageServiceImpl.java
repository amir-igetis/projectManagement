package com.amirul.projectmanagement.service;

import com.amirul.projectmanagement.model.Chat;
import com.amirul.projectmanagement.model.Message;
import com.amirul.projectmanagement.model.User;
import com.amirul.projectmanagement.repository.MessageRepository;
import com.amirul.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    //    Long chatId or projectId
    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new Exception("User not found with id : " + senderId));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);
        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);

        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long ProjectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(ProjectId);

        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());

    }
}
