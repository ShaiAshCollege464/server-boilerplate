package com.college.controllers;


import com.college.entities.User;
import com.college.responses.BasicResponse;
import com.college.responses.UserResponse;
import com.college.responses.UsersResponse;
import com.college.utils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.college.utils.Errors.*;


@RestController
public class GeneralController {

    @Autowired
    private DbUtils dbUtils;

    @PostConstruct
    public void init() {
    }


    @RequestMapping("/delete")
    public BasicResponse delete(int id) {
        if (id != 0) {
            dbUtils.delete(id);
            return new BasicResponse(true, null);

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
        return new UsersResponse(true, null, dbUtils.getAllUsers());
    }

    @RequestMapping("sign-up")
    public BasicResponse createUser(String username, String password) {
        if (username != null) {
            if (password != null) {
                if (!usernameExistsOnTheDn(username)) {
                    User newUser = new User(username, password);
                    dbUtils.insertUser(newUser);
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
        return dbUtils.doesUsernameExist(username);
    }

    //CRUD
    //Create - INSERT
    //Read -> SELECT
    //Update -> UPDATE
    //Delete -> DELETE



    public User getUser(String username, String password) {
        return dbUtils.getUser(username,password);

    }


}
