package com.example.demo;

import com.example.demo.entity.TelegramDb;
import com.example.demo.repositories.TelegramRepository;
import com.example.demo.repositories.TelegramRepositoryImpl;
import com.example.demo.services.TelegramDbService;
import com.example.demo.services.servicesImpl.TelegramDbServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	TelegramRepository telegramRepository;
	TelegramDbService telegramDbService;

	@BeforeEach
	void init(){
		telegramRepository = new TelegramRepositoryImpl();
		telegramDbService = new TelegramDbServiceImpl();
	}

	@Test
	void saveNewDataAndCheckForLastUpdatedEowFromDb() {
		String firstName = "someFName";
		String lastName = "someLName";
		int telegramId = 121241;
		TelegramDb telegramDb = null;

		try {
			 telegramDbService.saveTelegramData(new TelegramDb(firstName,lastName,telegramId));
			  telegramDb = telegramDbService.getLastTelegramDbRecordFromDb();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		System.out.println(telegramDb);

		Assert.isTrue(telegramDb.getTelegramId() == telegramId,"telegram id  is equal to condition value");
		Assert.isTrue(telegramDb.getFirstName().trim().equals(firstName),"first name is equal to condition value");
		Assert.isTrue(telegramDb.getLastName().trim().equals(lastName),"last name is equal to condition value");
		Assert.notNull(telegramDb,"telegram db is not empty");

	}

	@Test
	void checkIfIdIsPresentByReceivingBooleanValue(){
		try {
			boolean isPresent = telegramRepository.ifTelegramIdIsPresent(234);
			Assert.isTrue(isPresent,"telegram id is present");
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

}
