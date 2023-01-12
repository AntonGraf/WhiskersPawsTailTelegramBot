package pro.sky.whiskerspawstailtelegrambot.textAndButtonsAndKeyboard;

/**
 * Текстовые поля используемые в боте
 */
public abstract class AllText {

  public static final String START_TEXT = "/start";
  public static final String START_MENU_TEXT = "init to conversation";
  public static final String CALL_TO_VOLUNTEER_TEXT = "/calltovolunteer";
  public static final String INFO_SHELTER_TEXT = "Информация о приюте";
  public static final String WELCOME_MESSAGE_TEXT = "Добро пожаловать в чат бот приюта для животных, для получения информации воспользуйтесь кнопками или командами меню";
  public static final String CALL_VOLUNTEER_TEXT = "call to volunteer";
  public static final String UNKNOWN_COMMAND_TEXT = "Воспользуйтесь кнопками, либо командами меню";
  public static final String SEND_PET_REPORT_TEXT = "Отправить отчет о питомце";
  public static final String SEND_REPORT_TEXT = "Отправить отчет";
  public static final String HOW_TAKE_DOG = "Как получить собаку";
  public static final String HOW_TAKE_DOG_SHELTER = "Обратитесь к волонтеру за подробной информацией";
  public static final String SHOW_ALL_YOUR_PET_TEXT = "Показать имена всех ваших животных";
  public static final String CANCEL_TEXT = "Отмена";
  public static final String SEND_TEXT = "Отправить";
  public static final String MENU_SEND_PET_REPORT_TEXT = "В этом меню вы можете прислать отчет о вашем питомце";

  public static final String CANCEL_RETURN_MAIN_MENU_TEXT = "Отмена.\nВозврат в главное меню.";
  public static final String ERROR_REPLY_TEXT = "Произошла ошибка. Повторите.";
  public static final String YOU_HAVE_NO_ADOPTED_PETS_TEXT = "У вас нет животных требующих отправки отчета";
  public static final String DESCRIPTION_SEND_REPORT_TEXT = "В каждом отчете должна быть 1 фотография и текст не менее 15 слов, о состоянии животного."
      + "\nВведите id, напишите тект, добавьте фотографию и отправьте сообщение.";
  public static final String RESULT_MESSAGE_SEND_REPORT_TEXT = "Отчет успешно отправлен, волотеры ознакомятся с ним в ближайшее время.\n"
      + "При возникновении вопросов, они свяжутся с вами.";
  public static final String NO_TEXT_SEND_REPORT_TEXT = "В отчете нехватает текста, попробуйте снова.";
  public static final String NO_PHOTO_SEND_REPORT_TEXT = "В отчете нехватает фото, попробуйте снова.";
  public static final String NO_TEXT_AND_PHOTO_SEND_REPORT_TEXT = "В отчете нехватает текста и фото, попробуйте снова.";

    //кнопки для регистрации
    public static final String REGISTRATION_BUTTON = "Регистрация";
    public static final String REGISTRATION_CANCEL = "Отмена регистрации и выход в меню";
    public static final String REGISTRATION_SUCCESS = "Регистрация прошла успешно, ваш Id : \n";
    public static final String REG_FULL_NAME = "Как к Вам обращаться?";
    public static final String REG_PHONE = "По какому номеру с Вами связаться?";
    public static final String ALREADY_REGISTERED = "Вы уже зарегестрированы";

    //кнопки для регистрации

//    public static final String CANCEL_RETURN_MAIN_MENU_TEXT = "Отмена.\nВозврат в главное меню.";
//  public static final String ERROR_REPLY_TEXT = "Произошла ошибка. Повторите.";
//  public static final String YOU_HAVE_NO_ADOPTED_PETS_TEXT = "У вас нет животных требующих отправки отчета";
//  public static final String DESCRIPTION_SEND_REPORT_TEXT = "В каждом отчете должна быть 1 фотография и текст не менее 15 слов, о состоянии животного."
//      + "\nВведите id, напишите тект, добавьте фотографию и отправьте сообщение.";
//  public static final String RESULT_MESSAGE_SEND_REPORT_TEXT = "Отчет успешно отправлен, волотеры ознакомятся с ним в ближайшее время.\n"
//      + "При возникновении вопросов, они свяжутся с вами.";
//  public static final String NO_TEXT_SEND_REPORT_TEXT = "В отчете нехватает текста, попробуйте снова.";
//  public static final String NO_PHOTO_SEND_REPORT_TEXT = "В отчете нехватает фото, попробуйте снова.";
//  public static final String NO_TEXT_AND_PHOTO_SEND_REPORT_TEXT = "В отчете нехватает текста и фото, попробуйте снова.";
//
//    //кнопки для регистрации
//    public static final String REGISTRATION_BUTTON = "Регистрация";
//    public static final String REGISTRATION_CANCEL = "Отмена регистрации и выход в меню";
//    public static final String REGISTRATION_SUCCESS = "Регистрация прошла успешно, ваш Id : \n";
//    public static final String REG_FULL_NAME = "Как к Вам обращаться?";
//    public static final String REG_PHONE = "По какому номеру с Вами связаться?";
//    public static final String ALREADY_REGISTERED = "Вы уже зарегестрированы";

    //кнопки для регистрации

  public static final String INFO = "Выберете нужную информацию по номеру из списка:  /1 - Цель приюта\n\n /2 - График приюта\n\n" +
          "/3 - Правила поведения\n\n /4 - Правила перевозки\n\n /5 - Подготовка жилья\n\n" +
          "/6 - Обустройства места питомца\n\n /7 - Собаки инвалиды\n\n /8 - В первый день";






}
