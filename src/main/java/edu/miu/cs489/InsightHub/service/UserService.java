package edu.miu.cs489.InsightHub.service;

import edu.miu.cs489.InsightHub.dto.user.UserRequest;
import edu.miu.cs489.InsightHub.dto.user.UserRequestForUpdate;
import edu.miu.cs489.InsightHub.dto.user.UserResponse;
import edu.miu.cs489.InsightHub.exception.UserNotFoundException;
import edu.miu.cs489.InsightHub.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserResponse registerUser(UserRequest user);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Integer userId) throws UserNotFoundException;
    UserResponse updateUser(UserRequestForUpdate userRequestForUpdate);
    String deleteUser(Integer userId);
    User getUserByEmail(String email);
}
