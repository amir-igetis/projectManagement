package com.amirul.projectmanagement.repository;

import com.amirul.projectmanagement.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
