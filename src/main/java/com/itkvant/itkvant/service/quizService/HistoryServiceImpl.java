package com.itkvant.itkvant.service.quizService;

import com.itkvant.itkvant.model.test.HistoryQuiz;
import com.itkvant.itkvant.repository.repoQuiz.HistoryQuizRepository;
import com.itkvant.itkvant.service.quizService.HistoryQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("historyService")
public class HistoryServiceImpl implements HistoryQuizService {


    @Autowired
    private HistoryQuizRepository historyQuizRepository;


    @Override
    public List<HistoryQuiz> getHistoriesQuiz() {
        return historyQuizRepository.findAll();
    }

    @Override
    public HistoryQuiz getHistoryQuizById(long id) {
        return historyQuizRepository.findById(id).orElse(null);
    }

    @Override
    public List<HistoryQuiz> getHistoriesByUserId(long userId) {
        return historyQuizRepository.findAllByUserId(userId);
    }

    @Override
    public void createHistory(HistoryQuiz historyQuiz) {
        historyQuizRepository.save(historyQuiz);
    }

    @Override
    public void deleteHistory(long id) {
        historyQuizRepository.deleteById(id);
    }
}
