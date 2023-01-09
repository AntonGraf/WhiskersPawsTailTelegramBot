package pro.sky.whiskerspawstailtelegrambot.util;

public enum StatusRegistration {
    SUCCESS("зареган"),
    ONLY_NAME("ввел только имя"),
    ONLY_PHONE("ввел только телефон"),
    THE_FIRST_STATE("только начал");

    StatusRegistration(String name) {
    }
}
