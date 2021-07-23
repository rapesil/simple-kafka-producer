package com.live.kafka.producer.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private String id;
    private String model;
    private String color;

    public String toString() {
        return id + model + color;
    }

}
