package com.itkvant.itkvant.service.bot;

import com.itkvant.itkvant.model.Transaction;
import com.itkvant.itkvant.model.Wallet;
import com.itkvant.itkvant.model.dto.AssignmentUser;
import com.itkvant.itkvant.service.TransactionService;
import com.itkvant.itkvant.service.WalletService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TelegramBot extends  TelegramLongPollingBot {

    private static final String BOT_USERNAME = "@KvantumItBot";
    private static final String BOT_TOKEN = "6297954924:AAG5eh5f0hyRI_UFfVCZsMIXexTdfJbUqfE";


    private final WalletService walletService;


    private final TransactionService transactionService;

    private final AssignmentUser assignmentUser;
    private final Long userId;

    public TelegramBot(WalletService walletService, TransactionService transactionService, AssignmentUser assignmentUser, Long userId) {
        this.walletService = walletService;
        this.transactionService = transactionService;
        this.assignmentUser = assignmentUser;
        this.userId = userId;
    }


    public void sendMessageWithButtons(Chat chat) {

    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Chat chat = update.getMessage().getChat();


            String text = assignmentUser.toString();

            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(chat.getId());
            message.setText(text);

            InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
            List<InlineKeyboardButton> rowInLine = new ArrayList<>();

            var trueAnswerButton = new InlineKeyboardButton();
            trueAnswerButton.setText("Правильно");
            trueAnswerButton.setCallbackData("TRUE_ANSWER");

            var falseAnswerButton = new InlineKeyboardButton();
            falseAnswerButton.setText("Неправильно");
            falseAnswerButton.setCallbackData("FALSE_ANSWER");

            rowInLine.add(trueAnswerButton);
            rowInLine.add(falseAnswerButton);

            rowsInLine.add(rowInLine);

            markupInLine.setKeyboard(rowsInLine);
            message.setReplyMarkup(markupInLine);


            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        System.out.println(update.hasCallbackQuery());
        if (update.hasCallbackQuery()) {
            System.out.println("IF callback");
            String callBackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callBackData.equals("TRUE_ANSWER")) {
                System.out.println("true");
                afterSendCallbackQuery(chatId);
                addBonusesForCompletingTasks();
            } else if (callBackData.equals("FALSE_ANSWER")) {
                afterSendCallbackQuery(chatId);
            }
        }


    }

    public void addBonusesForCompletingTasks() {
        double award = assignmentUser.getAssignmtAward();
        Wallet wallet = walletService.findBYUserId(userId);
        System.out.println(wallet);
        walletService.addFunds(wallet.getId(), award);

        System.out.println("======= + " + wallet);
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(award));
        transaction.setDateTime(LocalDateTime.now());
        transaction.setWallet(wallet);
        System.out.println("===" + transaction);

        transactionService.createTransaction(transaction);
    }

    public void afterSendCallbackQuery(long chatId) {
        SendMessage trueMessage = new SendMessage();
        trueMessage.setText("Сообщение отправлено");
        trueMessage.setChatId(chatId);
        try {
            execute(trueMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
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


}
