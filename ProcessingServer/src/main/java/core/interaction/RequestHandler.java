package core.interaction;

public interface RequestHandler {

    //Выполнить запрос
    boolean executeRequest(ResponseRecipient responseRecipient, Request request); // Ответ Получателя и запрос
}
