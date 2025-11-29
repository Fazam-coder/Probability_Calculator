package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Content {
    private InlineKeyboardMarkup markup;
    private Chapter chapter;
    private SubChapter subChapter;
    private String message;

    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    public String getMessage() {
        return message;
    }

    //TODO: Create new message and new markup
    public void newMessage(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        if ("Combinatorics".equals(data)) {
            message = "Вы выбрали расчет комбинаторики. Как вы хотите считать? С повторениями или без?";
            markup = new InlineKeyboardMarkup();
            setKeyboardRepetitions(markup);
            chapter = Chapter.COMBINATORICS;
        } else if ("Ver".equals(data)) {
            message = "Вы выбрали расчет вероятностей с помощью урновой модели. " +
                    "Вы хотите решать задачу, когда все меченные, или только нескольно?";
            markup = new InlineKeyboardMarkup();
            setKeyboardMarked(markup);
//            markup = null;
            chapter = Chapter.PROBABILITY;
        }
    }

    public void newMessage(String prevMessage) {
        if (chapter == null) {
            message = "Привет, я - калькулятор теории вероятностей и я могу тебе помочь решать задачи. " +
                    "Нажмите на кнопку, что вы хотите посчитать";
            markup = new InlineKeyboardMarkup();
            setKeyboardWhatCalculate(markup);
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

    private void setKeyboardRepetitions(InlineKeyboardMarkup markup) {
    }

    private void setKeyboardMarked(InlineKeyboardMarkup markup) {
    }
}
