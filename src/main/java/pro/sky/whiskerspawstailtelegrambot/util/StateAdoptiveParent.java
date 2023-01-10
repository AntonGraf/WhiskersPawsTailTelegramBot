package pro.sky.whiskerspawstailtelegrambot.util;

public enum StateAdoptiveParent {

    FREE("FREE"),
    THE_FIRST_STATE("только начал"),
    ONLY_NAME("ввел только имя"),
    SUCCESS_REG("зареган"),
    WAIT_SEND_REPORT("WAIT_SEND_REPORT"),
    NO_SEND_REPORT("NO_SEND_REPORT");

    StateAdoptiveParent(String name) {
    }

    public String getText(){
        return name();
    }
}
