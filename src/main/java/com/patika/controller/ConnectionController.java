package com.patika.controller;

import com.patika.dto.request.ConnectionDto;
import com.patika.dto.response.PatikaResponse;
import com.patika.service.ConnectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection")
public class ConnectionController {
    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
    @PostMapping
    public ResponseEntity<PatikaResponse> saveConnection(@RequestBody ConnectionDto dto){
        connectionService.saveConnection(dto);

        PatikaResponse response = new PatikaResponse();
        response.setMessage("Connection saved");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
}
