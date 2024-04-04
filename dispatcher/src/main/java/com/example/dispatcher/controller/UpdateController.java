package com.example.dispatcher.controller;

import com.example.dispatcher.service.UpdateProducer;
import com.example.dispatcher.utils.MessageUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.constraints.util.RabbitConstants.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor

@Component
@Log4j
public class UpdateController {
    @NotNull
    private TelegramBot telegramBot;

    private final MessageUtils messageUtils;

    private final UpdateProducer updateProducer;


    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (isNull(update)) {
            log.error("Error while getting message accured");
            return;
        }
        if (nonNull(update.getMessage())) {
            distributeMessageByType(update);
        } else {
            log.error("Received unsupported message!");
        }
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            processText(update);
        } else if (message.hasPhoto()) {
            processPhoto(update);
        } else if (message.hasAudio()) {
            processAudio(update);
        } else if (message.hasDocument()) {
            processDocument(update);
        } else {
            setUnsupportedTypeView(update);
        }
    }

    private void setUnsupportedTypeView(Update update) {
        var sendMessage = messageUtils.generateText(update, "Unsupported type for this application!");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sentAnswerMessage(sendMessage);
    }

    private void processDocument(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
        sendMessageIsGet(update, DOC_MESSAGE_UPDATE);
    }

    private void processAudio(Update update) {
        updateProducer.produce(AUDIO_MESSAGE_UPDATE, update);
        sendMessageIsGet(update, AUDIO_MESSAGE_UPDATE);
    }

    private void processPhoto(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
        sendMessageIsGet(update, PHOTO_MESSAGE_UPDATE);
    }

    private void processText(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
        sendMessageIsGet(update, TEXT_MESSAGE_UPDATE);
    }

    private void sendMessageIsGet(Update update, String option){
        var sendMessage = new SendMessage();
        switch (option){
            case DOC_MESSAGE_UPDATE -> {
                sendMessage = messageUtils.generateText(update, "Document received, please, wait...");
            }
            case TEXT_MESSAGE_UPDATE -> {
                sendMessage = messageUtils.generateText(update, "Text received, please, wait...");
            }
            case AUDIO_MESSAGE_UPDATE -> {
                sendMessage = messageUtils.generateText(update, "Audio received, please, wait...");
            }
            case PHOTO_MESSAGE_UPDATE -> {
                sendMessage= messageUtils.generateText(update, "Photo received, please, wait...");
            }
        }
        setView(sendMessage);
    }
}
