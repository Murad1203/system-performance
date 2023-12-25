package com.itkvant.itkvant.service.quizService;

import com.itkvant.itkvant.model.test.Question;
import com.itkvant.itkvant.model.test.Test;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface QuestionsQuizService {
    List<Question> getAllQuestions();
    List<Question> getAllQuestionsByTestIdAndQuestId(long testId, long questId);
    Test getQuestionById(long id);
    void UpdateQuest(long id);
    void deleteQuestById(long id);

    List<Question> getAllQuestionsByTestId(long testId);
}
