package com.example.dispatcher.service.impl;

import com.example.dispatcher.service.UpdateProducer;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Log4j
public class UpdateProducerImpl implements UpdateProducer {
    private RabbitTemplate template;

    @Autowired
    public UpdateProducerImpl(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void produce(String rabbitmq, Update update) {
        log.debug(update.getMessage().getText());
        template.convertAndSend(rabbitmq, update);
    }
}
