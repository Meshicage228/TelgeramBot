package com.example.node.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MainService {
    void processText(Update update);
}
