package com.example.dispatcher.controller;

import com.example.dispatcher.service.CurrencyManipulation;
import com.example.dispatcher.utils.Commands;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.util.Objects.nonNull;


@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${app.botName}")
    private String botUsername;

    private UpdateController updateController;

    @PostConstruct
    public void init(){
        updateController.registerBot(this);
    }

    @Autowired
    public TelegramBot(@Value("${app.botToken}") String botToken, UpdateController controller) {
        super(botToken);
        this.updateController = controller;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if(nonNull(message.getText())) {
            switch (message.getText().toLowerCase()) {
                case "/start" -> {
                    startSection(message.getChat().getFirstName(), message.getChatId());
                }
                case "/rub", "/usd", "/eur" -> updateController.processUpdate(update);

                default -> {
                    updateController.processUpdate(update);
                }
            }
        }
    }
    public void sentAnswerMessage(SendMessage sendMessage){
        if(sendMessage != null){
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void startSection(String username, Long chatId){
        String textSection = """
                | Welcome, %s!
                | This bot is constructed for selfStudy, so it can do the following : 
                | Can rework your http from youtube to mp3 file
                """;
        String format = String.format(textSection, username);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(format);
        sentAnswerMessage(sendMessage);
    }
}
