package pro.sky.whiskerspawstailtelegrambot.util.stateAdaptiveParent;

public enum StateAdoptiveParent {



    FREE("FREE"),
    THE_FIRST_STATE("только начал"),
    ONLY_NAME("ввел только имя"),
    SUCCESS_REG("зареган"),
//    NULL(null),


    IN_PROCESS_SEND_REPORT("WAIT_SEND_REPORT"),
    NOT_STARTED("NOT_STARTED"),
    START_REPORT_1("START_SEND_REPORT"),
    ENTER_ID_PET_WAIT_PHOTO_REPORT_2("WAIT_ID_PET_REPORT"),
    ENTER_PHOTO_WAIT_DIET_REPORT_3("WAIT_PHOTO_REPORT"),
    ENTER_DIET_WAIT_FEELINGS_REPORT_4("WAIT_DIET_REPORT"),
    ENTER_FEELINGS_WAIT_HABITS_REPORT_5("WAIT_HEALTH_REPORT"),
    ENTER_HABITS_WAIT_FINISHED_REPORT_6("WAIT_HABITS_REPORT"),
    WAIT_CLICK_SEND_REPORT("WAIT_CLICK_SEND_REPORT"),
    FINISHED_REPORT_7("FINISHED");


    StateAdoptiveParent(String name) {

    }

    public String getText(){
        return name();
    }


}
