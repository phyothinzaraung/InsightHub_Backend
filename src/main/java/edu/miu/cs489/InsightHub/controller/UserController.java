package edu.miu.cs489.InsightHub.controller;

import edu.miu.cs489.InsightHub.dto.user.UserAuthResponse;
import edu.miu.cs489.InsightHub.dto.user.UserRequest;
import edu.miu.cs489.InsightHub.dto.user.UserRequestForUpdate;
import edu.miu.cs489.InsightHub.dto.user.UserResponse;
import edu.miu.cs489.InsightHub.exception.UserNotFoundException;
import edu.miu.cs489.InsightHub.model.User;
import edu.miu.cs489.InsightHub.service.UserService;
import edu.miu.cs489.InsightHub.util.Constants;
import edu.miu.cs489.InsightHub.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_VERSION + "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.registerUser(userRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequestForUpdate userRequest){
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId){
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> login(@RequestBody @Valid UserRequest userRequest) throws Exception{
        UserAuthResponse userAuthResponse = null;
        try {
            var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.email(), userRequest.password()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            var token = jwtUtil.generateToken(auth);
            String email = jwtUtil.extractUsername(token);
            User user = userService.getUserByEmail(email);
            if (user != null){
                userAuthResponse = new UserAuthResponse(
                        user.getUserId(),
                        token,
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                );
            }
        }catch (Exception e){
            System.out.println("User Authentication Fail.");
            throw e;
        }

        return ResponseEntity.ok(userAuthResponse);
    }

}
