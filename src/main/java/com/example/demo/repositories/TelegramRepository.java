package com.example.demo.repositories;

import com.example.demo.entity.TelegramDb;

import java.sql.SQLException;

//@Repository
//public interface TelegramRepository extends JpaRepository<TelegramDb,Long> {
//    TelegramDb findByTelegramId(int telegramId);
//}

public interface TelegramRepository {
    boolean ifTelegramIdIsPresent(int telegramId) throws SQLException;
    void save(TelegramDb telegramDb) throws SQLException;
    TelegramDb findLast() throws SQLException;
}

