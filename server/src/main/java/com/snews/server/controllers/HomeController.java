package com.snews.server.controllers;

import com.snews.server.services.file.FileService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController implements ErrorController {


//    @GetMapping("/")
//    public String getHome(){
//        return "index.html";
//    }

    private static final String PATH = "/error";

    @RequestMapping(value ="/error")
    public String error() {
        return "forward:/index.html";
    }

    //    @Override
    public String getErrorPath() {
        return PATH;
    }
}

