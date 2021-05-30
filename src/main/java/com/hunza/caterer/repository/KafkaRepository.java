package com.hunza.caterer.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Repository
@Slf4j
public class KafkaRepository
{
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @PostConstruct
    public void initialise()
    {
        log. info("Loaded Kafka Repository, using servers : {}", bootstrapServers);
    }

    public void send(String topic, String message)
    {
        String key = System.currentTimeMillis() + "-" + Thread.currentThread().getId();
        log.debug("Sending Kafka message, key : {}, topic : {}, message : {}", key, topic, message);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<>()
        {
            @Override
            public void onFailure(Throwable e)
            {
                log.error("Kafka error on sending, key : {}", key);
                log.error(e.getMessage(), e);
            }

            @Override
            public void onSuccess(SendResult<String, String> result)
            {
                log.debug("Kafka sent message OK, key : {}", key);
            }
        });
    }

    public void sendAsync(String topic, String message)
    {
        (new Thread(() -> send(topic, message))).start();
    }
}
