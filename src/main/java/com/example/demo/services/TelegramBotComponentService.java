package com.example.demo.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;

public interface TelegramBotComponentService {
    InlineKeyboardButton inlineKeyboardButtons (String buttonName, String data);

    String bankValue(int typeOfNote,String from) throws IOException;
    String moneyTwentyFour(int typeOfNote) throws IOException;


}
