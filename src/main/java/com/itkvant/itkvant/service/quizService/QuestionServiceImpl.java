package com.itkvant.itkvant.service.quizService;

import com.itkvant.itkvant.model.test.Question;
import com.itkvant.itkvant.model.test.Test;
import com.itkvant.itkvant.repository.repoQuiz.QuestionQuizRepository;
import com.itkvant.itkvant.service.quizService.QuestionsQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("questionService")
public class QuestionServiceImpl implements QuestionsQuizService {

    @Autowired
    private QuestionQuizRepository quizRepository;

    @Override
    public List<Question> getAllQuestions() {
        return quizRepository.findAll();
    }

    @Override
    public List<Question> getAllQuestionsByTestIdAndQuestId(long testId, long questId) {
        return null;
    }

    @Override
    public Test getQuestionById(long id) {
        return null;
    }

    @Override
    public void UpdateQuest(long id) {

    }

    @Override
    public void deleteQuestById(long id) {

    }

    @Override
    public List<Question> getAllQuestionsByTestId(long testId) {
        List<Question> questions = quizRepository.findAllByTestId(testId);
        return questions;
    }
}
