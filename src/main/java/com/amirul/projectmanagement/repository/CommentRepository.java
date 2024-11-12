package com.amirul.projectmanagement.repository;

import com.amirul.projectmanagement.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByIssueId(Long issueId);

}
