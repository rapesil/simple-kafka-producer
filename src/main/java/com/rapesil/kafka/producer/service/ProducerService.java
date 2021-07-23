package com.rapesil.kafka.producer.service;

import com.rapesil.kafka.producer.dto.CarDTO;
import com.rapesil.kafka.producer.producer.CarProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private CarProducer carProducer;

    public ResponseEntity<CarDTO> sendMessage(CarDTO carDTO) {
        carProducer.send(carDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carDTO);
    }
}
