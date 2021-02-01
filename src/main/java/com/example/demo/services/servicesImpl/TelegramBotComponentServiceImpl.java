package com.example.demo.services.servicesImpl;

import com.example.demo.services.TelegramBotComponentService;
import com.example.demo.services.TelegramDbService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;

//@Service
public class TelegramBotComponentServiceImpl implements TelegramBotComponentService {

    Logger logger = LoggerFactory.getLogger(TelegramBotComponentServiceImpl.class);

    @Override
    public InlineKeyboardButton inlineKeyboardButtons (String buttonName, String data){
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(buttonName);
        inlineKeyboardButton.setCallbackData(data);
        return inlineKeyboardButton;
    }

    @Override
    public String bankValue(int typeOfNote,String from) throws IOException {
        logger.info("<-- Receiving data from https://minfin.com.ua/company/" + from + "/currency/");

        Document doc = Jsoup.connect("https://minfin.com.ua/company/"+ from + "/currency/")
                .get();
        Elements elements = doc.getElementsByTag("tr");
        String strVal1 = elements.get(typeOfNote).child(1).getElementsByTag("td").text();
        String strVal2 = elements.get(typeOfNote).child(2).getElementsByTag("td").text();

        String result = "Покупка: " + strVal1.substring(0,5) + "Продажа: " + strVal2.substring(0,5);


        return result;
    }

    @Override
    public String moneyTwentyFour(int typeOfNote) throws IOException {
        String note;
        if(typeOfNote == 1){
            note = "usd";
        }else if(typeOfNote == 2){
            note = "eur";
        }else{
            note = "rub";
        }
        logger.info("<-- Receiving data from https://money24.kharkov.ua/"+ note + "-uah");

        Document doc = Jsoup.connect("https://money24.kharkov.ua/"+ note +"-uah").get();
//        System.out.println(doc.getElementsByClass("rate-number").first());
//        System.out.println(doc.getElementsByClass("rate-sell"));


        String result = "Покупка: " + doc.getElementsByClass("rate-number").first().text().substring(0,5) +
                "Продажа: " + doc.getElementsByClass("rate-number").last().text().substring(0,5);

        return result;
    }
}
