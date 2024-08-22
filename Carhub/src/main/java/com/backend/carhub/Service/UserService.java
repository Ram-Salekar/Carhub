package com.backend.carhub.Service;

import com.backend.carhub.Model.Role;
import com.backend.carhub.Model.User;
import com.backend.carhub.Repo.UserRepo;
import com.backend.carhub.dao.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {

    @Autowired
    private UserRepo userRepo;


    public String addUser(User user) {


        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepo.save(user);
        return "User added successfully";
    }
    public User getUser(long id) {

        Optional<User> a  = userRepo.findById(id);

        if(a.isPresent()) {
            User user = a.get();


            return user;
        }

        else {
            return null;
        }

    }
    public User getUserByEmail(String email) {
        return this.userRepo.findByEmail(email);

    }
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public String deleteUser(long id) {
        if(userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return "Successfully Deleted User";
        }
        else {
            return "not found";
        }
    }


    public String updateUser(long id , String name, String email, String password) {
        Optional  <User>user = userRepo.findById(id);
        if(user.isPresent()) {
            User u = user.get();
            u.setUserName(name);
            u.setEmail(email);
            u.setPassword(password);
            userRepo.save(u);
            return "Successfully Updated User";
        }


        return "not found";
    }




}
