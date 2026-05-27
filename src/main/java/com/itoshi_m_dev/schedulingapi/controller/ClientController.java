package com.itoshi_m_dev.schedulingapi.controller;


import com.itoshi_m_dev.schedulingapi.model.Client;
import com.itoshi_m_dev.schedulingapi.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Client registerClient(@RequestBody Client client){
        return service.registerClient(client);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Client getClientById(@PathVariable String clientId){
        return service.getClientById(clientId);
    }

    @GetMapping("/me")
    public Object me(Authentication authentication){
        return authentication.getAuthorities();
    }
}
