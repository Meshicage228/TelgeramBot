package com.example.node.service.impl;

import com.example.node.service.ConsumerService;
import com.example.node.service.MainService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.constraints.util.RabbitConstants.*;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {
    MainServiceImpl mainService;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("NODE : TEXT MESSAGE");
        mainService.processText(update);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocumentMessageUpdates(Update update) {
        log.debug("NODE : DOCUMENT MESSAGE");
    }

    @Override
    @RabbitListener(queues = AUDIO_MESSAGE_UPDATE)
    public void consumeAudioMessageUpdates(Update update) {
        log.debug("NODE : AUDIO MESSAGE");
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
        log.debug("NODE : PHOTO MESSAGE");
    }
}
