package com.itkvant.itkvant.service.quizService;

import com.itkvant.itkvant.model.test.Question;
import com.itkvant.itkvant.model.test.Test;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestQuizService {
    List<Test> getAllTestQuizs();
    Test getTestById(long id);
    void createTest(Test test);
    void UpdateTest(long id);
    void deleteTestById(long id);
    void testScores(long userId, long testId, List<Question> correctQuestions, Integer correctAnswersCount);
}

