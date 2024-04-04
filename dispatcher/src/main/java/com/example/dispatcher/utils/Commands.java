package com.example.dispatcher.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum Commands {
    START ("/start"),
    EUR ("/eur"),
    USD ("/usd"),
    RUB ("/rub");

    public final String command;
}
