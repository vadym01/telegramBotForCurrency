package com.example.demo.bot;


import com.example.demo.entity.TelegramDb;
import com.example.demo.services.TelegramBotComponentService;
import com.example.demo.services.TelegramDbService;
import com.example.demo.services.servicesImpl.TelegramBotComponentServiceImpl;
import com.example.demo.services.servicesImpl.TelegramDbServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    Logger logger = LoggerFactory.getLogger(Bot.class);

    private TelegramBotComponentService componentsService = new TelegramBotComponentServiceImpl();
    private TelegramDbService telegramDbService= new TelegramDbServiceImpl();

    int currency = 0;

    @Override
    public String getBotUsername() {
        return "moneyB";
    }

    @Override
    public String getBotToken() {
        return "1612607246:AAGLC0MGxIAB1sHW6gJgedrKNshGIN6v9VI";
    }


    @Override
    public void onUpdateReceived(Update update) {

        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        inlineKeyboardButtons.add(componentsService.inlineKeyboardButtons(" $ ","usd"));
        inlineKeyboardButtons.add(componentsService.inlineKeyboardButtons(" € ","eur"));
        inlineKeyboardButtons.add(componentsService.inlineKeyboardButtons(" ₽ ","rub"));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineListMarkup = new ArrayList<>();
        inlineListMarkup.add(inlineKeyboardButtons);
        inlineKeyboardMarkup.setKeyboard(inlineListMarkup);

        if (update.hasMessage() && update.getMessage() != null) {

            try {
                logger.info("<-- New user detected -->");
                if(!telegramDbService.ifUserPresent(update.getMessage().getFrom().getId())){
                    telegramDbService.saveTelegramData(new TelegramDb(
                            update.getMessage().getFrom().getFirstName(),
                            update.getMessage().getFrom().getLastName(),
                            update.getMessage().getFrom().getId()));
                };
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Добрый день " + update.getMessage().getFrom().getFirstName() + "\n Выберите валюту, которую будем искать: ");
            message.setReplyMarkup(inlineKeyboardMarkup);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }else if(update.hasCallbackQuery()){
            try {
                String selectedData = update.getCallbackQuery().getData();
                SendMessage message = new SendMessage();
                if(new String("usdeurrub").contains(selectedData)){
                    if(selectedData.equals("usd")){
                        currency = 1;
                    }else if(selectedData.equals("eur")){
                        currency = 2;
                    }else{
                        currency = 3;
                    }
                    message.setText("Выберите источник данных: ");
                }else{
                    message.setText(selectedData);
                }
                message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
                message.setReplyMarkup(inlineKeyboardMarkup);

                inlineKeyboardButtons.clear();
                try {
                    inlineKeyboardButtons.add(componentsService.inlineKeyboardButtons(" Ощад банк", componentsService.bankValue(currency,"oschadbank")));
                    inlineKeyboardButtons.add(componentsService.inlineKeyboardButtons(" Приват банк",componentsService.bankValue(currency,"privatbank")));
                    inlineKeyboardButtons.add(componentsService.inlineKeyboardButtons(" Money 24", componentsService.moneyTwentyFour(currency)));
                }catch (Exception e){
                    e.printStackTrace();
                }
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


}