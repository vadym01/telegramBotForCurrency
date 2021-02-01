package com.example.demo.services;

import com.example.demo.entity.TelegramDb;

import java.sql.SQLDataException;
import java.sql.SQLException;

public interface TelegramDbService {

    void saveTelegramData(TelegramDb telegramUserDb) throws SQLException;
    boolean ifUserPresent(int telegramId) throws SQLException;
    TelegramDb getLastTelegramDbRecordFromDb() throws SQLException;
}
