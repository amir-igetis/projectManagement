package com.amirul.projectmanagement.service;

import com.amirul.projectmanagement.model.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long senderId, Long ChatId, String content) throws Exception;

    List<Message> getMessagesByProjectId(Long ProjectId) throws Exception;
}
