package com.college.controllers;


import com.college.utils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;


@RestController
public class GeneralController {

    @Autowired
    private DbUtils dbUtils;

    @PostConstruct
    public void init() {
    }


}
