package com.amirul.projectmanagement.service;

import com.amirul.projectmanagement.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

    User updateUserProjectSize(User user, int number);


}
