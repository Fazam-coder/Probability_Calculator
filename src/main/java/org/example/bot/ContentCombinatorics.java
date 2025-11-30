package org.example.bot;

import org.example.combinatorics.CombWithRepetitions;
import org.example.combinatorics.CombWithoutRepetitions;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContentCombinatorics {
    private int numberMessage = 1;
    private SubChapter subChapter;
    private String message;
    private InlineKeyboardMarkup markup;
    private Combinatorics combinatorics;

    public void newMessage(String prevMessage) {
        if (numberMessage == 4) {
            int[] values;
            try {
                values = Arrays.stream(prevMessage.strip().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректные входные данные");
            }
            if (isValidValues(values)) {
                long result = 0;
                if (subChapter == SubChapter.WITHOUT_REPETITIONS) {
                    switch (combinatorics) {
                        case PERMUTATIONS -> result = CombWithoutRepetitions.getPermutationsCount(values[0]);
                        case ACCOMMODATIONS -> result = CombWithoutRepetitions.getAccommodationsCount(values[0], values[1]);
                        case COMBINATIONS -> result = CombWithoutRepetitions.getCombinationsCount(values[0], values[1]);
                    }
                } else {
                    switch (combinatorics) {
                        case PERMUTATIONS -> result = CombWithRepetitions.getPermutationsCount(values);
                        case ACCOMMODATIONS -> result = CombWithRepetitions.getAccommodationsCount(values[0], values[1]);
                        case COMBINATIONS -> result = CombWithRepetitions.getCombinationsCount(values[0], values[1]);
                    }
                }
                createFourthMessage(result);
            } else {
                throw new IllegalArgumentException("Некорректные входные данные");
            }
        } else {
            throw new IllegalArgumentException("Необходимо нажать на кнопку");
        }
    }

    public void newMessage(CallbackQuery callbackQuery) {
        switch (numberMessage) {
            case 1: createFirstMessage(); break;
            case 2:
                if (callbackQuery.getData().equals("WithoutRepetitions")) {
                    subChapter = SubChapter.WITHOUT_REPETITIONS;
                } else if (callbackQuery.getData().equals("WithRepetitions")) {
                    subChapter = SubChapter.WITH_REPETITIONS;
                } else {
                    throw new IllegalArgumentException("Неправильный ввод, нажмите кнопку, которая вам нужна");
                }
                createSecondMessage();
                break;
            case 3:
                switch (callbackQuery.getData()) {
                    case "Permutations" -> {
                        createThirdMessagePermutations();
                        combinatorics = Combinatorics.PERMUTATIONS;
                    }
                    case "Accommodations" -> {
                        createThirdMessageAccommodations();
                        combinatorics = Combinatorics.ACCOMMODATIONS;
                    }
                    case "Combinations" -> {
                        createThirdMessageCombinations();
                        combinatorics = Combinatorics.COMBINATIONS;
                    }
                    default ->
                            throw new IllegalArgumentException("Неправильный ввод, нажмите кнопку, которая вам нужна");
                }
                break;
            default: throw new IllegalArgumentException("Что-то не так с сообщением");
        }
        numberMessage++;
    }

    public String getMessage() {
        return message;
    }

    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    public void clear() {
        numberMessage = 1;
        markup = null;
    }

    private void createFirstMessage() {
        message = "Вы выбрали расчет комбинаторики. Как вы хотите считать? С повторениями или без?";
        markup = new InlineKeyboardMarkup();
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

        markup.setKeyboard(buttons);
    }

    private void createSecondMessage() {
        message = "Хорошо, а теперь что вы хотите считать?";
        markup = new InlineKeyboardMarkup();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Перестановки");
        button1.setCallbackData("Permutations");
        List<InlineKeyboardButton> buttons1 = List.of(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Размещения");
        button2.setCallbackData("Accommodations");
        List<InlineKeyboardButton> buttons2 = List.of(button2);

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Сочетания");
        button3.setCallbackData("Combinations");
        List<InlineKeyboardButton> buttons3 = List.of(button3);

        List<List<InlineKeyboardButton>> buttons = List.of(buttons1, buttons2, buttons3);

        markup.setKeyboard(buttons);
    }

    private void createThirdMessagePermutations() {
        switch (subChapter) {
            case SubChapter.WITHOUT_REPETITIONS -> {
                message = "Вы выбрали посчитать количество перестановок, при этом элементы без повторений. " +
                        "Напишите количество элементов одним числом";
                markup = null;
            }
            case SubChapter.WITH_REPETITIONS -> {
                message = "Вы выбрали посчитать количество перестановок, при этом элементы с повторениями. " +
                        "Напишите количество элементов 1, 2, 3 и т.д. вида через пробел";
                markup = null;
            }
        }
    }

    private void createThirdMessageAccommodations() {
        switch (subChapter) {
            case SubChapter.WITHOUT_REPETITIONS -> {
                message = "Вы выбрали посчитать количество размещений, при этом элементы без повторений. " +
                        "Напишите количество элементов всего и количество элементов которые надо разместить через пробел";
                markup = null;
            }
            case SubChapter.WITH_REPETITIONS -> {
                message = "Вы выбрали посчитать количество размещений, при этом элементы с повторениями. " +
                        "Напишите количество элементов всего и количество элементов которые надо разместить через пробел";
                markup = null;
            }
        }
    }

    private void createThirdMessageCombinations() {
        switch (subChapter) {
            case SubChapter.WITHOUT_REPETITIONS -> {
                message = "Вы выбрали посчитать количество сочетаний, при этом элементы без повторений. " +
                        "Напишите количество элементов всего и количество элементов которые надо выбрать через пробел";
                markup = null;
            }
            case SubChapter.WITH_REPETITIONS -> {
                message = "Вы выбрали посчитать сочетания, при этом элементы с повторениями. " +
                        "Напишите количество элементов всего и количество элементов которые надо выбрать через пробел";
                markup = null;
            }
        }
    }

    private boolean isValidValues(int[] values) {
        if (combinatorics == Combinatorics.PERMUTATIONS && subChapter == SubChapter.WITHOUT_REPETITIONS && values.length != 1) {
            return false;
        }
        if (combinatorics != Combinatorics.PERMUTATIONS && values.length != 2) {
            return false;
        }
        if (combinatorics != Combinatorics.PERMUTATIONS && subChapter == SubChapter.WITHOUT_REPETITIONS && values[0] < values[1]) {
            return false;
        }
        for (int el : values) {
            if (el < 0) {
                return false;
            }
        }
        return true;
    }

    private void createFourthMessage(long result) {
        message = "Ответ: " + result;
        markup = null;
    }
}

