package com.example.demo.services.servicesImpl;

import com.example.demo.entity.TelegramDb;
import com.example.demo.repositories.TelegramRepository;
import com.example.demo.repositories.TelegramRepositoryImpl;
import com.example.demo.services.TelegramDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Optional;

//@Service
public class TelegramDbServiceImpl implements TelegramDbService {

    private TelegramRepository telegramRepository = new TelegramRepositoryImpl();


    @Override
    public void saveTelegramData(TelegramDb telegramUserDb) throws SQLException {
        telegramRepository.save(telegramUserDb);
    }

    @Override
    public boolean ifUserPresent(int telegramId) throws SQLException {
       boolean isPresent = telegramRepository.ifTelegramIdIsPresent(telegramId);
        return isPresent;
    }

    @Override
    public TelegramDb getLastTelegramDbRecordFromDb() throws SQLException {
        TelegramDb telegramDb = telegramRepository.findLast();
        return telegramDb;
    }
}
