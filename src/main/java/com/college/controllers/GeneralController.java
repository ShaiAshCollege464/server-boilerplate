package com.college.controllers;


import com.college.utils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GeneralController {


    @Autowired
    private DbUtils dbUtils;

    @RequestMapping ("/get-data")
    public String getAllUsers () {
        return "Hello world!";
    }


}
