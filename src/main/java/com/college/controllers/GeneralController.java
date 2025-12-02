package com.college.controllers;


import com.college.entities.User;
import com.college.responses.BasicResponse;
import com.college.responses.UserResponse;
import com.college.responses.UsersResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.college.utils.Errors.*;


@RestController
public class GeneralController {
    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {

    }

    public User getUser(String username, String password) {
        User found = null;
        for (User user : this.users) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                found = user;
                break;
            }
        }
        return found;
    }

    public int getUserIndex(String username) {
        int userIndex = 0;
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getName().equals(username)) {
                userIndex = i;
                break;
            }
        }
        return userIndex;
    }

    @RequestMapping("/delete")
    public BasicResponse delete(String username) {
        if (username != null) {
            if (usernameExistsOnTheDn(username)) {
                int userDeleteIndex = getUserIndex(username);
                this.users.remove(userDeleteIndex);
                return new BasicResponse(true, null);
            } else {
                return new BasicResponse(false, ERROR_USERNAME_NOT_SIGNED_UP);
            }
        } else {
            return new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
    }


    @RequestMapping("/login")
    public BasicResponse login(String username, String password) {
        if (username != null) {
            if (password != null) {
                if (usernameExistsOnTheDn(username)) {
                    User user = getUser(username, password);
                    if (user != null) {
                        return new UserResponse(true, null, user);
                    } else {
                        return new BasicResponse(false, ERROR_WRONG_PASSWORD);
                    }
                } else {
                    return new BasicResponse(false, ERROR_USERNAME_NOT_SIGNED_UP);
                }
            } else {
                return new BasicResponse(false, ERROR_MISSING_PASSWORD);
            }
        } else {
            return new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
    }

    @RequestMapping("returnAllusers")
    public BasicResponse returnAllusers (){
        return new UsersResponse(true, null, users);
    }

    @RequestMapping("sign-up")
    public BasicResponse createUser(String username, String password) {
        if (username != null) {
            if (password != null) {
                if (!usernameExistsOnTheDn(username)) {
                    User newUser = new User(username, password);
                    users.add(newUser);
                    return new BasicResponse(true, null);
                } else {
                    return new BasicResponse(false, ERROR_USERNAME_TAKEN);
                }
            } else {
                return new BasicResponse(false, ERROR_MISSING_PASSWORD);
            }
        } else {
            return new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
    }

    private boolean usernameExistsOnTheDn(String username) {
        for (User user : users) {
            if (user.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }



}
