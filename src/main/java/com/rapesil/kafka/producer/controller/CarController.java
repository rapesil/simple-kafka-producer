package com.rapesil.kafka.producer.controller;

import com.rapesil.kafka.producer.dto.CarDTO;
import com.rapesil.kafka.producer.producer.CarProducer;
import com.rapesil.kafka.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private ProducerService producerService;

    @PostMapping
    public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDTO){
        CarDTO car = CarDTO.builder()
                .id(UUID.randomUUID().toString())
                .color(carDTO.getColor())
                .model(carDTO.getModel()).build();
        ResponseEntity<CarDTO> carDTOResponseEntity = producerService.sendMessage(car);
        return carDTOResponseEntity;
    }

}
