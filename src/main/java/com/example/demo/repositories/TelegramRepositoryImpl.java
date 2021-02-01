package com.example.demo.repositories;

import com.example.demo.entity.TelegramDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class TelegramRepositoryImpl  implements TelegramRepository{

    Logger logger = LoggerFactory.getLogger(TelegramRepositoryImpl.class);

    static Connection connection = DatabaseConnection.getConnection();


    @Override
    public boolean ifTelegramIdIsPresent(int telegramId) throws SQLException {
        String query = "SELECT telegram_id from telegram_db where telegram_id=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,telegramId);
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }



    @Override
    public void save(TelegramDb telegramDb) throws SQLException {
        logger.info("<-- Starting data update processing -->");
        String query = "INSERT INTO telegram_db(first_name, last_name, telegram_id) VALUES(?,?,?)";
      PreparedStatement ps = connection.prepareStatement(query);
      ps.setString(1,telegramDb.getFirstName());
      ps.setString(2,telegramDb.getLastName());
      ps.setInt(3,telegramDb.getTelegramId());
      int n = ps.executeUpdate();
        logger.info("<-- New data is saved successfully: added " + n + " row(s) -->");
    }

    @Override
    public TelegramDb findLast() throws SQLException {
        TelegramDb telegramDb = new TelegramDb();
        String query = "SELECT id, first_name, last_name, telegram_id FROM telegram_db ORDER BY id DESC LIMIT 1";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet resSet = ps.executeQuery();
        while (resSet.next()){
            telegramDb.setId(resSet.getLong("id"));
            telegramDb.setFirstName(resSet.getString("first_name"));
            telegramDb.setLastName(resSet.getString("last_name"));
            telegramDb.setTelegramId(resSet.getInt("telegram_id"));
        }
        return telegramDb;
    }
}
