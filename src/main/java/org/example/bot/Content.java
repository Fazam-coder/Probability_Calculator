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

    }

    public void newMessage(String prevMessage) {
        if (chapter == null) {
            String s = "Привет, я - калькулятор теории вероятностей и я могу тебе помочь решать задачи." +
                    "Нажми на кнопку, что ты хочешь";
        }
    }

    private void setInline(InlineKeyboardMarkup markupKeyboard) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Кнопка");
        button.setCallbackData("Combinatorics");
        buttons1.add(button);
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Кнопка2");
        button2.setCallbackData("Ver");
        buttons1.add(button2);
        buttons.add(buttons1);

        markupKeyboard.setKeyboard(buttons);
    }
}
