package pro.sky.whiskerspawstailtelegrambot.util;

public enum StateAdoptiveParent {

    FREE("FREE"),
    THE_FIRST_STATE("только начал"),
    ONLY_NAME("ввел только имя"),
    SUCCESS_REG("зареган"),
//    NULL(null),

    START_SEND_REPORT("WAIT_SEND_REPORT"),
    IN_PROCESS_SEND_REPORT("WAIT_SEND_REPORT"),

    NOT_STARTED("NOT_STARTED"),
    WAIT_ID_PET_REPORT("WAIT_ID_PET_REPORT"),
    WAIT_PHOTO_REPORT("WAIT_PHOTO_REPORT"),
    WAIT_DIET_REPORT("WAIT_DIET_REPORT"),
    WAIT_FEELINGS_REPORT("WAIT_HEALTH_REPORT"),
    WAIT_HABITS_REPORT("WAIT_HABITS_REPORT"),
    WAIT_CLICK_SEND_REPORT("WAIT_CLICK_SEND_REPORT"),
    FINISHED("FINISHED");
    
    
    

    StateAdoptiveParent(String name) {
    }

    public String getText(){
        return name();
    }
}
