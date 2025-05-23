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
    public static final int ERROR_USERNAME_NOT_AVAILABLE = 2000;

    private List<User> users = new ArrayList<>();

    @RequestMapping("/get-all-users")
    public UsersResponse getUsers () {
        return new UsersResponse(true, null, this.users);
    }

    @RequestMapping("add-user")
    public BasicResponse addUser (String username, String password) {
        if (available(username).isAvailabe()) {
            User user = new User(username, password);
            this.users.add(user);
            return new BasicResponse(true, null);
        } else {
            return new BasicResponse(false, ERROR_USERNAME_NOT_AVAILABLE);
        }
    }

    @RequestMapping ("/available")
    public AvailableResponse available (String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return new AvailableResponse(true, null, false);
            }
        }
        return new AvailableResponse(true, null, true);
    }
}
