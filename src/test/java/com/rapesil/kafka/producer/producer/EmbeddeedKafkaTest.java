package com.rapesil.kafka.producer.producer;

import com.rapesil.kafka.producer.dto.CarDTO;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
        topics = {"embedded-test-topic"})
public class EmbeddeedKafkaTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private CarProducer producer;

    @Value("${test.topic}")
    private String topic;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingToCarProducer_thenMessageReceived() throws Exception {
        //Create a simple consumer
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "baeldung", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ConsumerFactory cf = new DefaultKafkaConsumerFactory<String, CarDTO>(
                consumerProps, new StringDeserializer(),
                new JsonDeserializer<>(CarDTO.class,
                        false));
        Consumer<String, CarDTO> consumerServiceTest = cf.createConsumer();

        // Make the consumer can read from embedded kafka broker and topic
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumerServiceTest, topic);

        // Create a simple CarDto object to send in message
        CarDTO carDtoMessage = CarDTO.builder()
                .id("1")
                .color("preto")
                .model("hb20s")
                .build();

        // Sending message
        producer.send(carDtoMessage);

        // Use Kafka Test Utils to get a single record for topic
        ConsumerRecord<String, CarDTO> consumer = KafkaTestUtils.getSingleRecord(consumerServiceTest, topic);
        // Get value from message receive
        CarDTO valueReceived = consumer.value();

        // Assertions
        assertThat(valueReceived.getId()).isEqualTo("1");
        assertThat(valueReceived.getColor()).isEqualTo("preto");
        assertThat(valueReceived.getModel()).isEqualTo("hb20s");

        // Close the consumer
        consumerServiceTest.close();
    }
}
