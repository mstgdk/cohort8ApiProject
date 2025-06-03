package com.patika.service;

import com.patika.dto.request.ConnectionDto;
import com.patika.entity.Connection;
import com.patika.exception.ConflictException;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.ConnectionRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    private static  final Logger log = LoggerFactory.getLogger(ConnectionService.class);
    private final ConnectionRespository connectionRespository;

    public ConnectionService(ConnectionRespository connectionRespository) {
        this.connectionRespository = connectionRespository;
    }

    public void saveConnection(ConnectionDto dto) {
        Connection connectionInDb = connectionRespository.findByEmail(dto.getEmail());
        if (connectionInDb!=null){
            //throw  new IllegalArgumentException("This email address is already exists");
            throw  new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS_EXCEPTION,dto.getId()));
        }

        Connection connection = new Connection();
        connection.setEmail(dto.getEmail());
        connection.setMobileNumber(dto.getMobileNumber());
        connectionRespository.save(connection);
        log.info("Connection save successfully for email : {}" ,connection.getEmail());

    }
}
