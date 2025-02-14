package com.proxidize_assienment.length.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LengthController {

    @PostMapping(value = "/length", produces = MediaType.TEXT_PLAIN_VALUE)
    public String computeLength(@RequestBody String text) {
        // Remove surrounding quotes if present
        String processed = text.replaceAll("^\"|\"$", "");
        
        // Remove all spaces
        processed = processed.replaceAll(" ", "");
        
        // Calculate length and add newline
        return processed.length() + "\n";
    }
}
