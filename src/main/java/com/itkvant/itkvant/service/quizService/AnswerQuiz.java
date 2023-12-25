package com.itkvant.itkvant.service.quizService;

import com.itkvant.itkvant.model.test.Question;
import com.itkvant.itkvant.model.test.Test;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerQuiz {
    List<Question> getAllAnswers();
    Test getAnswerById(long id);
    void UpdateAnswer(long id);
    void deleteAnswerById(long id);
}
