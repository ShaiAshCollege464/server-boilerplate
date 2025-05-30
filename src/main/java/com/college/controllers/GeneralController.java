package com.college.controllers;


import com.college.entities.User;
import com.college.responses.AvailableResponse;
import com.college.responses.BasicResponse;
import com.college.responses.UsersResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GeneralController {

    private List<User> allUsers = new ArrayList<>();

    @RequestMapping ("/users")
    public List<User> getAllUsers () {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    @RequestMapping("/add-user")
    public void addUser (String username, String password) {
        User user = new User(username, password);
        allUsers.add(user);
    }

}
