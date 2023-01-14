package pro.sky.whiskerspawstailtelegrambot.handlers.stateHandlers;

public class GetPetIdFromString {

  public Long get(String textMessage) {

    if (textMessage == null) {
      return null;
    }
    try {
      int index = textMessage.indexOf("ID: ");
      String id = textMessage.substring(index, index + 1);
      return Long.parseLong(id);
    } catch (Exception e) {
      return null;
    }
  }


}


