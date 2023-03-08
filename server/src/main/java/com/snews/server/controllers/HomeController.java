package com.snews.server.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController implements ErrorController {

//package com.snews.server.controllers;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@CrossOrigin
//@Controller
//public class HomeController implements ErrorController {
//
//    private static final String ERROR_PATH = "/error";
//
//    @RequestMapping(value = ERROR_PATH)
//    public String error() {
//        return "forward:/index.html";
//    }
    @GetMapping("/")
    public String getHome(){
        return "index.html";
    }
//
//    @GetMapping("/")
//    public String getHome() {
//        return "index.html";
//    }
//
//    @RequestMapping(value = "/{path:[^\\.]*}")
//    public String redirect() {
//        return "forward:/";
//    }

}
//}
//
//}
//
