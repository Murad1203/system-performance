package com.itkvant.itkvant.service.bot;

import com.itkvant.itkvant.model.dto.AssignmentUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TelegramBot extends  TelegramLongPollingBot {

    private static final String BOT_USERNAME = "@KvantumItBot";
    private static final String BOT_TOKEN = "6297954924:AAG5eh5f0hyRI_UFfVCZsMIXexTdfJbUqfE";


    private final AssignmentUser assignmentUser;

    private static final String BUTTON_CORRECT = "Правильно";
    private static final String BUTTON_INCORRECT = "Неправильно";



    List<String> questions = Arrays.asList(
            "Сколько будет 2+2?",
            "Как называется столица Франции?",
            "Кто написал роман \"Война и мир\"?"
    );

    List<Boolean> answers = Arrays.asList(
            true,
            false,
            true
    );

    public TelegramBot(AssignmentUser assignmentUser) {
        this.assignmentUser = assignmentUser;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Получаем id чата и текст сообщения от пользователя
            long chatId = update.getMessage().getChatId();
            String text = assignmentUser.toString();
            String messageText = update.getMessage().getText();

            // Если пользователь отправил "/start", отправляем первый вопрос
            if (messageText.equals("/start")) {
                // Отправляем первый вопрос с двумя кнопками
                InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
                List<InlineKeyboardButton> row = new ArrayList<>();
                row.add(new InlineKeyboardButton().setText("Правильно").setCallbackData("true"));
                row.add(new InlineKeyboardButton().setText("Неправильно").setCallbackData("false"));
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);

                try {
                    execute(new SendMessage()
                            .setChatId(chatId)
                            .setText(text)
                            .setReplyMarkup(keyboardMarkup));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    // метод для отправки сообщения с кнопками
    private void sendMessageWithButtons(String text) {

        long chatId = 938882970;

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(text);

        // создание кнопок
        KeyboardButton buttonCorrect = new KeyboardButton()
                .setText(BUTTON_CORRECT);
        KeyboardButton buttonIncorrect = new KeyboardButton()
                .setText(BUTTON_INCORRECT);

        // создание строки кнопок
        KeyboardRow row = new KeyboardRow();
        row.add(buttonCorrect);
        row.add(buttonIncorrect);

        // создание клавиатуры с кнопками
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup()
                .setOneTimeKeyboard(true)
                .setKeyboard(List.of(row));

        // добавление клавиатуры к сообщению
        message.setReplyMarkup(keyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void onCallbackQueryReceived(CallbackQuery callbackQuery) {
        // Получаем данные из callback запроса
        String data = callbackQuery.getData();

        if (data.equals("true") || data.equals("false")) {
            // Получаем id чата
            long chatId = callbackQuery.getMessage().getChatId();

            // Получаем номер вопроса
            int questionNumber = Integer.parseInt(callbackQuery.getMessage().getText().split("\\.")[0]);

            // Проверяем ответ пользователя
            boolean userAnswer = Boolean.parseBoolean(data);
            boolean correctAnswer = answers.get(questionNumber - 1);

            // Отправляем сообщение с результатом
            try {
                execute(new SendMessage()
                        .setChatId(chatId)
                        .setText(userAnswer == correctAnswer ? "Правильно!" : "Неправильно!")
                );
            } catch (TelegramApiException e) {
                e.printStackTrace();

            }
        }
    }

    public AssignmentUser getAssignmentUser() {
        return assignmentUser;
    }
}
