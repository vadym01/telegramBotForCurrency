package com.example.demo.entity;



//@Entity
public class TelegramDb {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int telegramId;

    public TelegramDb(Long id, String firstName, String lastName, int telegramId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telegramId = telegramId;
    }

    public TelegramDb(String firstName, String lastName, int telegramId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telegramId = telegramId;
    }

    public TelegramDb() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(int telegramId) {
        this.telegramId = telegramId;
    }

    @Override
    public String toString() {
        return "TelegramDb{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telegramId=" + telegramId +
                '}';
    }
}
