package com.example.dispatcher.service.impl;

import com.example.dispatcher.controller.UpdateController;
import com.example.dispatcher.service.AnswerConsumer;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.constraints.util.RabbitConstants.ANSWER_MESSAGE;

@Service
@Log4j
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateController updateController;

    @Autowired
    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
