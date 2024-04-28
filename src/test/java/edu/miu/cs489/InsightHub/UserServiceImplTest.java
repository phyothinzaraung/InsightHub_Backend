package edu.miu.cs489.InsightHub;

import edu.miu.cs489.InsightHub.dto.user.UserRequest;
import edu.miu.cs489.InsightHub.dto.user.UserResponse;
import edu.miu.cs489.InsightHub.exception.UserNotFoundException;
import edu.miu.cs489.InsightHub.model.User;
import edu.miu.cs489.InsightHub.repository.UserRepository;
import edu.miu.cs489.InsightHub.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp(){
        user = new User(1, "John", "Smith", "j@example.com", "123", "Writer");
        userResponse = new UserResponse(1, "John", "Smith", "j@example.com", "Writer");
    }

    @Test
    void getAllUsers_shouldReturnListOfUsersResponse(){
        when (userRepository.findAll()).thenReturn( Arrays.asList(user));
        List<UserResponse> userResponseList = userService.getAllUsers();
        assertEquals(1, userResponseList.size());
        assertEquals(userResponse, userResponseList.get(0));
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_shouldReturnOneUser() throws UserNotFoundException {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        UserResponse userResult = userService.getUserById(1);
        assertEquals(user.getUserId(), userResult.userId());
        verify(userRepository).findById(1);

    }

    @Test
    void registerUser_shouldReturnResponseUser(){
        UserRequest userRequest = new UserRequest( "John", "Smith", "j@example.com", "123", "Writer");
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserResponse response = userService.registerUser(userRequest);
        assertEquals(1, response.userId());
        assertEquals("John", response.firstName());
        assertEquals("Smith", response.lastName());
        assertEquals("j@example.com", response.email());
        assertEquals("Writer", response.description());
    }
}
