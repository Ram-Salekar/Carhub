package com.backend.carhub.Controller;

import com.backend.carhub.Model.Role;
import com.backend.carhub.Model.User;
import com.backend.carhub.Service.JwtService;
import com.backend.carhub.Service.UserService;
import com.backend.carhub.dao.UserDao;
import com.backend.carhub.dao.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    private final UserService userService;
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    AuthController(UserService userService,PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            logger.info("Received request to add user: {}", user);
            if(user.getUserName().contains("admin")) {
                user.setRole(Role.ADMIN);
                user.setUserName(user.getUserName().split(" admin")[0]);
            }
            else {
                user.setRole(Role.USER);
            }
            String result = userService.addUser(user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error adding user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDao userDao){
        System.out.println("Inside login method");
        if(userDao!=null){
            if(!userDao.getEmail().isEmpty() && !userDao.getPassword().isEmpty()){
                var u= this.userService.getUserByEmail(userDao.getEmail());
                if(u!=null && passwordEncoder.matches(userDao.getPassword(),u.getPassword())){
                    var hm = new HashMap();
                    var token = this.jwtService.generateToken(u);
                    hm.put("user",new UserDto(u.getUserId(),u.getEmail(),u.getRole()));
                    hm.put("token",token);
                    return ResponseEntity.ok(hm);
                }
            }
        }
        return ResponseEntity.status(404).body("Password is Incorrect");
    }
}
