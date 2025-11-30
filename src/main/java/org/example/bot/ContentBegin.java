package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ContentBegin {
    private InlineKeyboardMarkup markup;
    private Chapter chapter;
    private String message;
    private final ContentCombinatorics contentCombinatorics = new ContentCombinatorics();
    private final ContentProbability contentProbability = new ContentProbability();

    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    public String getMessage() {
        return message;
    }

    public void newMessage(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        if ("Combinatorics".equals(data) || chapter == Chapter.COMBINATORICS) {
            contentCombinatorics.newMessage(callbackQuery);
            message = contentCombinatorics.getMessage();
            markup = contentCombinatorics.getMarkup();
            chapter = Chapter.COMBINATORICS;
        } else if ("Ver".equals(data) || chapter == Chapter.PROBABILITY) {
            contentProbability.newMessage(callbackQuery);
            message = contentProbability.getMessage();
            markup = contentProbability.getMarkup();
            chapter = Chapter.PROBABILITY;
        }
    }

    public void newMessage(String prevMessage) {
        switch (chapter) {
            case COMBINATORICS -> {
                contentCombinatorics.newMessage(prevMessage);
                message = contentCombinatorics.getMessage();
                markup = contentCombinatorics.getMarkup();
            } case PROBABILITY -> {
                contentProbability.newMessage(prevMessage);
                message = contentProbability.getMessage();
                markup = contentProbability.getMarkup();
            } case null -> {
                message = "Привет, я - калькулятор теории вероятностей и я могу тебе помочь решать задачи. " +
                        "Нажмите на кнопку, что вы хотите посчитать";
                markup = new InlineKeyboardMarkup();
                setKeyboardWhatCalculate(markup);
                contentCombinatorics.clear();
                contentProbability.clear();
            }
        }
    }

    private void setKeyboardWhatCalculate(InlineKeyboardMarkup markupKeyboard) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Комбинаторика");
        button.setCallbackData("Combinatorics");
        buttons1.add(button);
        
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Вероятность");
        button2.setCallbackData("Ver");
        buttons1.add(button2);
        
        buttons.add(buttons1);

        markupKeyboard.setKeyboard(buttons);
    }
}
