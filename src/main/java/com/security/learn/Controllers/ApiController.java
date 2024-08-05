package com.security.learn.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    //first end point
    @GetMapping("/route1")
    public String route1() {
        return "Menu Changed: This is protected route1";
    }

    //second protected endpoint

    @GetMapping("/route2")
    public String route2() {
        return "Changed Price: This is protected route2";
    }

    @GetMapping("/route3")
    public String route3() {
        return "Order Food: This is protected route3";
    }

    @GetMapping("/route4")
    public String route4() {
        return "Pay Bill: This is protected route4";
    }

    @GetMapping("/route5")
    @PreAuthorize("hasRole('ADMIN')")
    public String route5() {
        return "Drink Water: This is protected route5";
    }

    //first end point
    @PostMapping("/route6")
    public String route6() {
        return "Menu Changed: This is protected route1";
    }
}
