package org.example.bot;

import org.example.probability.UrnModel;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContentProbability {
    public static final int MAX_NUMBER_MESSAGE = 3;
    private int numberMessage = 1;
    private SubChapter subChapter;
    private String message;
    private InlineKeyboardMarkup markup;

    public synchronized void newMessage(String prevMessage) {
        if (numberMessage == 3) {
            int[] values;
            try {
                values = Arrays.stream(prevMessage.strip().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректные входные данные");
            }
            if (isValidValues(values)) {
                double result = 0;
                switch (subChapter) {
                    case ALL_MARKED -> result = UrnModel.getProbabilityAllMarked(values[0], values[1], values[2]);
                    case R_MARKED -> result = UrnModel.getProbabilityRMarked(values[0], values[1], values[2], values[3]);
                }
                createThirdMessage(result);
                numberMessage++;
            } else {
                throw new IllegalArgumentException("Некорректные входные данные");
            }
        } else {
            throw new IllegalArgumentException("Необходимо нажать на кнопку");
        }
    }

    public synchronized void newMessage(CallbackQuery callbackQuery) {
        switch (numberMessage) {
            case 1: createFirstMessage();
            case 2:
                if (callbackQuery.getData().equals("AllMarked")) {
                    createSecondMessageAllMarked();
                    subChapter = SubChapter.ALL_MARKED;
                } else if (callbackQuery.getData().equals("RMarked")) {
                    createSecondMessageRMarked();
                    subChapter = SubChapter.R_MARKED;
                }
        }
        numberMessage++;
    }

    public String getMessage() {
        return message;
    }

    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    public int getNumberMessage() {
        return numberMessage;
    }

    public void clear() {
        numberMessage = 1;
        markup = null;
        subChapter = null;
    }

    private void createFirstMessage() {
        message = "Вы выбрали расчет вероятностей с помощью урновой модели. " +
                "Вы хотите решать задачу, когда все меченные, или только нескольно?";
        markup = new InlineKeyboardMarkup();
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

        markup.setKeyboard(buttons);
    }

    private void createSecondMessageAllMarked() {
        message = "Вы выбрали режим подсчета вероятности, когда все меченные. " +
                "Напишите через пробел 3 числа: количество элементов всего, количество меченных и сколько берется";
        markup = null;
    }

    private void createSecondMessageRMarked() {
        message = "Вы выбрали режим подсчета вероятности, когда какое-то количество меченных. " +
                "Напишите через пробел 4 числа: количество элементов всего, количество меченных, сколько берется " +
                "и сколько меченных должно быть";
        markup = null;
    }

    private boolean isValidValues(int[] values) {
        if (subChapter == SubChapter.ALL_MARKED && values.length != 3) {
            return false;
        }
        if (subChapter == SubChapter.R_MARKED && values.length != 4 && values[2] < values[3]) {
            return false;
        }
        if (values[0] < values[1]) {
            return false;
        }
        for (int el : values) {
            if (el < 0) {
                return false;
            }
        }
        return true;
    }

    private void createThirdMessage(double result) {
        message = "Ответ: " + result + "\nЧтобы еще что-то решить, напишите любой текст";
        markup = null;
    }
}
