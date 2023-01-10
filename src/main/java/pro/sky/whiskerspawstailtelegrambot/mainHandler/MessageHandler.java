package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.record.AdoptiveParentRecord;
import pro.sky.whiskerspawstailtelegrambot.service.AdoptiveParentService;
import pro.sky.whiskerspawstailtelegrambot.service.ShelterService;
import pro.sky.whiskerspawstailtelegrambot.service.VolunteerService;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.AllText;
import pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard.ConfigKeyboard;
import pro.sky.whiskerspawstailtelegrambot.util.FormReplyMessages;
import pro.sky.whiskerspawstailtelegrambot.util.ParserToBot;
import pro.sky.whiskerspawstailtelegrambot.util.StatusRegistration;


/**
 * обработка текстового сообщения
 */
@Slf4j
@Component("MessageHandler")
public class MessageHandler implements MainHandler {

    final ConfigKeyboard configKeyboard;
    private final ReportAddHandler reportAddHandler;
    private final FormReplyMessages formReplyMessages;

    private final VolunteerService volunteerService;

    private final AdoptiveParentService adoptiveParentService;

    private final ParserToBot parserToBot;
    private final ShelterService shelterService;


    public MessageHandler(ConfigKeyboard configKeyboard, ReportAddHandler reportAddHandler,
                          FormReplyMessages formReplyMessages, VolunteerService volunteerService, AdoptiveParentService adoptiveParentService, ParserToBot parserToBot, ShelterService shelterService) {
        this.configKeyboard = configKeyboard;
        this.reportAddHandler = reportAddHandler;
        this.formReplyMessages = formReplyMessages;
        this.volunteerService = volunteerService;
        this.adoptiveParentService = adoptiveParentService;
        this.parserToBot = parserToBot;
        this.shelterService = shelterService;
    }

    /**
     * Метод, который отвечает на входящее сообщение, либо на выбор в меню
     *
     * @param update адейт от пользователя в виде текста
     * @return отправляем ответ
     */
    @Override
    public SendMessage handler(Update update) {
        SendMessage sendMessage = null;
        String chatId = String.valueOf(update.getMessage().getChatId());
        boolean checkUpdate = !update.getMessage().hasText();
        Message message = update.getMessage();
        String textMessage = message.getText();
        AdoptiveParentRecord adoptiveParent = checkExistAdoptiveParent(chatId);
        String statusRegistration = checkStatusAdoptiveParent(chatId);

        if (textMessage.equals(AllText.REGISTRATION_CANCEL)) {
            if (adoptiveParent != null)
                adoptiveParentService .deleteAdoptiveParentByID(adoptiveParent.getId());
            return formReplyMessages.replyMessage(message, "/start",
                    configKeyboard.initKeyboardOnClickStart());
        }

        if (adoptiveParent != null && statusRegistration.equals(StatusRegistration.THE_FIRST_STATE.name())) {
            //проверка имени, пока только длина, а и зачем еще что то....
            if (textMessage.length() > 6) {
                AdoptiveParentRecord newAdoptiveParent = new AdoptiveParentRecord();
                newAdoptiveParent.setFullName(textMessage);
                newAdoptiveParent.setPhone("somePhone");
                newAdoptiveParent.setState(StatusRegistration.ONLY_NAME.name());
                newAdoptiveParent.setChatId(adoptiveParent.getChatId());
                adoptiveParentService.updateAdoptiveParent(adoptiveParent.getId(),
                        newAdoptiveParent);
                return newMessage(chatId, AllText.REG_PHONE);
            } else {
                return newMessage(chatId, "Введите правильное имя, длина больше 6 символов.");
            }
        } else if (checkExistAdoptiveParent(chatId) != null &&
                checkStatusAdoptiveParent(chatId)
                        .equals(StatusRegistration.ONLY_NAME.name())) {
            //проверка phone, пока только длина, а и зачем еще что то....
            if (textMessage.length() > 6) {
                AdoptiveParentRecord adoptiveParentOld = adoptiveParentService
                        .findAdoptiveParentByChatId(Long.parseLong(chatId));
                AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
                adoptiveParentRecord.setFullName(adoptiveParentOld.getFullName());
                adoptiveParentRecord.setPhone(textMessage);
                adoptiveParentRecord.setState(StatusRegistration.SUCCESS.name());
                adoptiveParentRecord.setChatId(adoptiveParentOld.getChatId());
                adoptiveParentService.updateAdoptiveParent(adoptiveParentOld.getId(),
                        adoptiveParentRecord);
                sendMessage = formReplyMessages.replyMessage(message,
                        AllText.REGISTRATION_SUCCESS + adoptiveParentOld.getId(), configKeyboard.initKeyboardOnClickStart());

            } else {
                return new SendMessage(chatId, "Введите правильный телефон, длина больше 6 символов.");
            }
        } else {

            if (!checkUpdate) {
                log.debug("Обработка сообщения в виде текста");
                //здесь инжект текст кнопок, любой текст крч
                switch (textMessage) {

                    case (AllText.START_TEXT):
                        sendMessage = formReplyMessages.replyMessage(message, AllText.WELCOME_MESSAGE_TEXT,
                                configKeyboard.initKeyboardOnClickStart());
                        break;

                    case (AllText.CALL_TO_VOLUNTEER_TEXT): //ответ на позвать волонтера, просто инфа про волонтеров
                        sendMessage = formReplyMessages.replyMessage(message, parserToBot.parserVolunteer(volunteerService.getAllVolunteers()),
                                configKeyboard.initKeyboardOnClickStart());
                        break;

                    case (AllText.SEND_PET_REPORT_TEXT):     // реализация логики отправить отчет
                        sendMessage = reportAddHandler.handlerReport(message, AllText.MENU_SEND_PET_REPORT_TEXT,
                                configKeyboard.initKeyboardOnClickSendPetReport());
                        break;
                    case (AllText.HOW_TAKE_DOG):
                        sendMessage = formReplyMessages.replyMessage(message, AllText.HOW_TAKE_DOG_SHELTER,
                                configKeyboard.initKeyboardOnClickStart());
                        break;
                    case (AllText.INFO_SHELTER_TEXT):
                        sendMessage = formReplyMessages.replyMessage(message, shelterService.getOfShelterMessage(1L),
                                configKeyboard.initKeyboardOnClickStart());
                        break;

                    //------------------> регистрация

                    case (AllText.REGISTRATION_BUTTON):
                    //если уже есть такой в таблице со статусом зареган, то просто сообщение что вы уже есть у нас
                        if (checkExistAdoptiveParent(chatId) != null && checkStatusAdoptiveParent(chatId)
                                .equals(StatusRegistration.SUCCESS.name())) {
                            return new SendMessage(chatId, AllText.ALREADY_REGISTERED);
                        }
                    //если нет в таблице со статусом зареган, то добавляем и ставим статус ферст стэйт в методе addToTable,
                    // так же там меняем клаву на кнопку отмена регистрации
                    //при следующем сообщении регистрация будет продолжаться в методе continueRegist, пока не зарегается до конца,
                    // либо отменет регистрацию

                        return addToTable(message, chatId);

                    //------------------> регистрация

                    default:
                        sendMessage = new SendMessage(chatId, AllText.UNKNOWN_COMMAND_TEXT);
                        break;
                }
            }
        }
        return sendMessage;
    }

    //добавляет нового пользвователя в таблиц при нажатии кнопки регистрация. Меняет статус и ракладку
    //отправляет новое сообщение
    private SendMessage addToTable(Message message, String chatId) {
        AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
        adoptiveParentRecord.setFullName("newParent");
        adoptiveParentRecord.setPhone("somePhone");
        adoptiveParentRecord.setState(StatusRegistration.THE_FIRST_STATE.name());
        adoptiveParentRecord.setChatId(Long.parseLong(chatId));
        adoptiveParentService.addAdoptiveParent(adoptiveParentRecord);
        return formReplyMessages.replyMessage(message, AllText.REG_FULL_NAME, configKeyboard.initKeyboardOnClickRegistration());
    }

    //проверяет есть ли усыновитель в таблице по чат айди
    private AdoptiveParentRecord checkExistAdoptiveParent(String chatId){
        return adoptiveParentService
                .findAdoptiveParentByChatId(Long.parseLong(chatId));
    }

    //проверяет статус усыновителя в таблице по чат айди
    private String checkStatusAdoptiveParent(String chatId){
        return adoptiveParentService
                .findAdoptiveParentByChatId((Long.parseLong(chatId)))
                .getState();
    }

    private SendMessage newMessage(String chatId,String textMessage){
        return new SendMessage(chatId,textMessage);
    }


}


