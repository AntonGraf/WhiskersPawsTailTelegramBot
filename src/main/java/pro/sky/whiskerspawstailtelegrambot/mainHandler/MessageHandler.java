package pro.sky.whiskerspawstailtelegrambot.mainHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.whiskerspawstailtelegrambot.entity.AdoptiveParent;
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
        if(textMessage.equals(AllText.REGISTRATION_CANCEL)){
            AdoptiveParentRecord adoptiveParent = adoptiveParentService
                    .findAdoptiveParentByChatId(Long.parseLong(chatId));
            if(adoptiveParent!=null){
                adoptiveParentService
                        .deleteAdoptiveParentByID(adoptiveParent.getId());
            }
            sendMessage = formReplyMessages.replyMessage(message,"Вы в главном меню",configKeyboard.initKeyboardOnClickStart());
        }
        if (adoptiveParentService
                .findAdoptiveParentByChatId(Long.parseLong(chatId)) != null &&
                adoptiveParentService
                        .findAdoptiveParentByChatId((Long.parseLong(chatId)))
                        .getState()
                        .equals(StatusRegistration.THE_FIRST_STATE.name())) {
            //проверка имени, пока только длина, а и зачем еще что то....
            if (textMessage.length() > 6) {
                AdoptiveParentRecord adoptiveParentOld = adoptiveParentService
                        .findAdoptiveParentByChatId(Long.parseLong(chatId));
                AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
                adoptiveParentRecord.setFullName(textMessage);
                adoptiveParentRecord.setPhone("somePhone");
                adoptiveParentRecord.setState(StatusRegistration.ONLY_NAME.name());
                adoptiveParentRecord.setChatId(adoptiveParentOld.getChatId());
                adoptiveParentService.updateAdoptiveParent(adoptiveParentOld.getId(),
                        adoptiveParentRecord);
                sendMessage = new SendMessage(chatId, AllText.REG_PHONE);
            } else {
                sendMessage = new SendMessage(chatId, "Введите правильное имя, длина больше 6 символов.");
            }
        } else if (adoptiveParentService
                .findAdoptiveParentByChatId(Long.parseLong(chatId)) != null &&
                adoptiveParentService
                        .findAdoptiveParentByChatId((Long.parseLong(chatId)))
                        .getState()
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
                sendMessage = formReplyMessages.replyMessage(message,AllText.REGISTRATION_SUCCESS + adoptiveParentOld.getId(),configKeyboard.initKeyboardOnClickStart());

            } else {
                sendMessage = new SendMessage(chatId, "Введите правильный телефон, длина больше 6 символов.");
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

                    //регистрация
                    case (AllText.REGISTRATION_BUTTON):
                        //если уже есть такой в таблице со статусом зареган, то просто сообщение что вы уже есть у нас
                        if (adoptiveParentService
                                .findAdoptiveParentByChatId(Long.parseLong(chatId)) != null &&
                                adoptiveParentService
                                        .findAdoptiveParentByChatId((Long.parseLong(chatId)))
                                        .getState()
                                        .equals(StatusRegistration.SUCCESS.name())) {
                            sendMessage = new SendMessage(chatId, AllText.ALREADY_REGISTERED);
                            break;
                        }
                        AdoptiveParentRecord adoptiveParentRecord = new AdoptiveParentRecord();
                        adoptiveParentRecord.setFullName("newParent");
                        adoptiveParentRecord.setPhone("somePhone");
                        adoptiveParentRecord.setState(StatusRegistration.THE_FIRST_STATE.name());
                        adoptiveParentRecord.setChatId(Long.parseLong(chatId));
                        adoptiveParentService.addAdoptiveParent(adoptiveParentRecord);
                        sendMessage = formReplyMessages.replyMessage(message,AllText.REG_FULL_NAME,configKeyboard.initKeyboardOnClickRegistration());

                        break;

                    default:
                        sendMessage = new SendMessage(chatId, AllText.UNKNOWN_COMMAND_TEXT);
                        break;
                }
            }
        }
        return sendMessage;
    }


}


