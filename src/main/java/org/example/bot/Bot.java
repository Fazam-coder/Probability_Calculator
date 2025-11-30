package org.example.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private ContentBegin content = new ContentBegin();
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String message = update.getMessage().getText();
            content.newMessage(message);
            sendMsg(update.getMessage().getChatId().toString(), content.getMessage(), content.getMarkup());
        } else if (update.hasCallbackQuery()){
            CallbackQuery cq = update.getCallbackQuery();
            content.newMessage(cq);
            sendMsg(cq.getMessage().getChatId().toString(), content.getMessage(), content.getMarkup());
        }
    }

    public Bot(String botToken) {
        super(botToken);
    }

    public synchronized void sendMsg(String chatId, String s, InlineKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        sendMessage.setReplyMarkup(markup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "TerVerCalculatorFazulzyanovBot";
    }
}
