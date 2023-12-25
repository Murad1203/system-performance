package com.itkvant.itkvant.service.quizService;

import com.itkvant.itkvant.model.test.HistoryQuiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryQuizService {
    List<HistoryQuiz> getHistoriesQuiz();
    HistoryQuiz getHistoryQuizById(long id);
    List<HistoryQuiz> getHistoriesByUserId(long userId);
    void createHistory(HistoryQuiz historyQuiz);
    void deleteHistory(long id);
}
