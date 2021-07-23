package com.live.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.live.kafka.producer.controller.CarDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Component
public class ConsumerTest {

    private CountDownLatch latch = new CountDownLatch(1);
    public static String payload;

    public static CarDTO getCarDTO() {
        return carDTO;
    }

    private static CarDTO carDTO;


    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) throws InterruptedException, IOException {
        payload = consumerRecord.value().toString();
        ObjectMapper mapper = new ObjectMapper();
        String testJson = payload;
        carDTO = mapper.readValue(testJson, CarDTO.class);
        latch.countDown();
//        System.out.println("readValue = " + readValue);
//
//
//        File file = new File("src/test/resources/test.txt");
//
//        // Se o arquivo nao existir, ele gera
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//
//        // Prepara para escrever no arquivo
//        FileWriter fw = new FileWriter(file.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw);
//
//        // Escreve e fecha arquivo
//        bw.write(payload);
//        bw.close();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return this.payload;
    }


}
