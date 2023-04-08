package com.snews.server.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value ="/error")
    public String error() {
        return "forward:/index.html";
    }

    public String getErrorPath() {
        return PATH;
    }
}

