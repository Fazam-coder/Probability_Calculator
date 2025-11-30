package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ContentBegin {
    private InlineKeyboardMarkup markup;
    private Chapter chapter;
    private SubChapter subChapter;
    private String message;
    private final ContentCombinatorics contentCombinatorics = new ContentCombinatorics();
    private final ContentProbability contentProbability = new ContentProbability();

    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    public String getMessage() {
        return message;
    }

    //TODO: Create new message and new markup
    public void newMessage(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        if ("Combinatorics".equals(data) || chapter == Chapter.COMBINATORICS) {
            contentCombinatorics.newMessage(callbackQuery);
            message = contentCombinatorics.getMessage();
            markup = contentCombinatorics.getMarkup();
//            message = "Вы выбрали расчет комбинаторики. Как вы хотите считать? С повторениями или без?";
//            markup = new InlineKeyboardMarkup();
//            setKeyboardRepetitions(markup);
            chapter = Chapter.COMBINATORICS;
        } else if ("Ver".equals(data) || chapter == Chapter.PROBABILITY) {
            contentProbability.newMessage(callbackQuery);
            message = contentProbability.getMessage();
            markup = contentProbability.getMarkup();
//            message = "Вы выбрали расчет вероятностей с помощью урновой модели. " +
//                    "Вы хотите решать задачу, когда все меченные, или только нескольно?";
//            markup = new InlineKeyboardMarkup();
//            setKeyboardMarked(markup);
//            markup = null;
            chapter = Chapter.PROBABILITY;
        }
    }

    public void newMessage(String prevMessage) {
        switch (chapter) {
            case COMBINATORICS -> {

            } case PROBABILITY -> {

            } case null -> {
                message = "Привет, я - калькулятор теории вероятностей и я могу тебе помочь решать задачи. " +
                        "Нажмите на кнопку, что вы хотите посчитать";
                markup = new InlineKeyboardMarkup();
                setKeyboardWhatCalculate(markup);
                contentCombinatorics.clearNumberMessage();
                contentProbability.clearNumberMessage();
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

    private void setKeyboardRepetitions(InlineKeyboardMarkup markupKeyboard) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Без повторений");
        button.setCallbackData("WithoutRepetitions");
        buttons1.add(button);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("С повторениями");
        button2.setCallbackData("WithRepetitions");
        buttons1.add(button2);

        buttons.add(buttons1);

        markupKeyboard.setKeyboard(buttons);
    }

    private void setKeyboardMarked(InlineKeyboardMarkup markupKeyboard) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Все меченные");
        button.setCallbackData("AllMarked");
        buttons1.add(button);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Не все меченные");
        button2.setCallbackData("RMarked");
        buttons1.add(button2);

        buttons.add(buttons1);

        markupKeyboard.setKeyboard(buttons);
    }
}
