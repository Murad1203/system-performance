package com.itkvant.itkvant.service.quizService;

import com.itkvant.itkvant.model.Transaction;
import com.itkvant.itkvant.model.Wallet;
import com.itkvant.itkvant.model.test.HistoryQuiz;
import com.itkvant.itkvant.model.test.Question;
import com.itkvant.itkvant.model.test.Test;
import com.itkvant.itkvant.repository.repoQuiz.TestQuizRepository;
import com.itkvant.itkvant.service.TransactionService;
import com.itkvant.itkvant.service.UserService;
import com.itkvant.itkvant.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service("testService")
public class TestQuizImpl implements TestQuizService {

    @Autowired
    private TestQuizRepository testQuizRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private WalletService walletService;

    @Autowired
    @Qualifier("historyService")
    private HistoryQuizService historyQuizService;

    @Autowired
    private UserService userService;

    @Override
    public List<Test> getAllTestQuizs() {
        return testQuizRepository.findAll();
    }

    @Override
    public Test getTestById(long id) {
        Test test = testQuizRepository.findById(id).orElse(null);
        System.out.println("-----------" + test);
        return test;
    }

    @Override
    public void createTest(Test test) {
        testQuizRepository.save(test);
    }

    @Override
    public void UpdateTest(long id) {
    }

    @Override
    public void deleteTestById(long id) {
        testQuizRepository.deleteById(id);
    }

    @Override
    public void testScores(long userId, long testId, List<Question> correctQuestions, Integer correctAnswersCount) {
        Double amount = 0.0;

        for (Question q: correctQuestions){
            amount = q.getAward();
        }

        Wallet wallet = walletService.findBYUserId(userId);
        walletService.addFunds(wallet.getId(), amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setDateTime(LocalDateTime.now());
        transaction.setWallet(wallet);
        transactionService.createTransaction(transaction);

        HistoryQuiz historyQuiz = new HistoryQuiz();
        historyQuiz.setTestId(testQuizRepository.findById(testId).orElse(null));
        historyQuiz.setUserId(userService.findById(userId));
        historyQuiz.setCountTrueAnswers(correctAnswersCount);
        historyQuiz.setAwait(amount);
        historyQuizService.createHistory(historyQuiz);
    }


}
