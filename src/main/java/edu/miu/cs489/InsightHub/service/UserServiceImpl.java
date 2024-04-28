package edu.miu.cs489.InsightHub.service;

import edu.miu.cs489.InsightHub.dto.user.UserRequest;
import edu.miu.cs489.InsightHub.dto.user.UserRequestForUpdate;
import edu.miu.cs489.InsightHub.dto.user.UserResponse;
import edu.miu.cs489.InsightHub.exception.UserNotFoundException;
import edu.miu.cs489.InsightHub.model.User;
import edu.miu.cs489.InsightHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        User newUser = new User();
        newUser.setFirstName(userRequest.firstName());
        newUser.setLastName(userRequest.lastName());
        newUser.setEmail(userRequest.email());
        newUser.setPassword(bCryptPasswordEncoder.encode(userRequest.password()));
        newUser.setDescription(userRequest.description());

        User user = userRepository.save(newUser);

        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDescription()
        );

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                                user.getUserId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.getDescription()
                        )).toList();
    }

    @Override
    public UserResponse getUserById(Integer userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return new UserResponse(
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getDescription()
            );
        }else {
            throw new UserNotFoundException(String.format("Error: user with Id - %d, is not found.", userId));
        }
    }

    @Override
    public UserResponse updateUser(UserRequestForUpdate userRequestForUpdate) {
        User user = userRepository.findById(userRequestForUpdate.userId()).orElse(null);
        if(user != null){
            user.setFirstName(userRequestForUpdate.firstName());
            user.setLastName(userRequestForUpdate.lastName());
            user.setDescription(userRequestForUpdate.description());
            User updatedUser = userRepository.save(user);
            return new UserResponse(
                    updatedUser.getUserId(),
                    updatedUser.getFirstName(),
                    updatedUser.getLastName(),
                    updatedUser.getEmail(),
                    updatedUser.getDescription());
        }else {
            return null;
        }
    }

    @Override
    public String deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        return String.format("User with Id - %d deleted successfully.", userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findUserByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
