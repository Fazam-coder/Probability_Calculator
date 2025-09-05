package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String message = update.getMessage().getText();
            sendMsg(update.getMessage().getChatId().toString(), message + message);
        } else if (update.hasCallbackQuery()){
            CallbackQuery cq = update.getCallbackQuery();
            sendMsg(cq.getMessage().getChatId().toString(), cq.getData());
            answerCallbackQuery(cq.getId(), "gdhgooygueogh");
        }
    }

    private void setInline(InlineKeyboardMarkup markupKeyboard) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Кнопка");
        button.setCallbackData("1");
        buttons1.add(button);
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Кнопка2");
        button2.setCallbackData("2");
        buttons1.add(button2);
        buttons.add(buttons1);

        markupKeyboard.setKeyboard(buttons);
    }

    public Bot(String botToken) {
        super(botToken);
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        setInline(markupKeyboard);
        sendMessage.setReplyMarkup(markupKeyboard);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void answerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "TerVerCalculatorFazulzyanovBot";
    }
}
