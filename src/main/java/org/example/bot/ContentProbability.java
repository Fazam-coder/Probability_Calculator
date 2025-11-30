package org.example.bot;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class ContentProbability {
    private int numberMessage = 1;
    private SubChapter subChapter;
    private String message;
    private InlineKeyboardMarkup markup;

    public void newMessage(String prevMessage) {

    }

    public void newMessage(CallbackQuery callbackQuery) {

    }

    public String getMessage() {
        return message;
    }

    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    public void setSubChapter(SubChapter subChapter) {
        this.subChapter = subChapter;
    }

    public void clearNumberMessage() {
        numberMessage = 1;
    }
}
