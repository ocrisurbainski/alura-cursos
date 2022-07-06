package br.com.alura.forum.controller.impl;

import br.com.alura.forum.controller.HelloController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControllerImpl implements HelloController {

    @Override
    @ResponseBody
    @GetMapping(value = "/", produces = "text/plain")
    public String hello() {
        return "Hello World!";
    }

}
