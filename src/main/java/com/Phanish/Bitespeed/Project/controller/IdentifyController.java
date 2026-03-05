package com.Phanish.Bitespeed.Project.controller;



import com.Phanish.Bitespeed.Project.dto.IdentifyRequest;
import com.Phanish.Bitespeed.Project.dto.IdentifyResponse;
import com.Phanish.Bitespeed.Project.service.ContactService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/identify")
public class IdentifyController {

    private final ContactService service;

    public IdentifyController(ContactService service){
        this.service = service;
    }

    @PostMapping
    public IdentifyResponse identify(@RequestBody IdentifyRequest request){
        return service.identify(request);
    }
}
