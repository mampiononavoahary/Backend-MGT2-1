package com.mgt2.backendproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {
    @GetMapping("/Document")
    public String getDocument(){
        return "this is your document";
    }
}
