package com.example.node.service.impl;

import com.example.node.entity.RawData;
import com.example.node.repository.RawDataRepository;
import com.example.node.service.MainService;
import com.example.node.service.ProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

@Service
public class MainServiceImpl implements MainService {
    RawDataRepository repository;
    ProducerServiceImpl producerService;

    @Override
    @Transactional
    public void processText(Update update) {
        RawData build = RawData.builder()
                .update(update)
                .build();

        repository.save(build);

        String query = String.format("https://api.nbrb.by/exrates/rates/%s?parammode=2", update.getMessage().getText().toUpperCase());
        LinkedHashMap forObject = new RestTemplate().getForObject(query, LinkedHashMap.class);

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId().toString());

        String answerMessage = String.format("Существующее отношение : 1 BYN : \n %s - %s", forObject.get("Cur_OfficialRate"), forObject.get("Cur_Name"));

        sendMessage.setText(answerMessage);
        producerService.producerAnswer(sendMessage);
    }
}
