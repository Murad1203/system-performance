package com.itkvant.itkvant.controller;

import com.itkvant.itkvant.model.User;
import com.itkvant.itkvant.model.dto.AnswerRequest;
import com.itkvant.itkvant.model.dto.QuestionRequest;
import com.itkvant.itkvant.model.dto.QuizResultDTO;
import com.itkvant.itkvant.model.dto.TestRequest;
import com.itkvant.itkvant.model.test.Answer;
import com.itkvant.itkvant.model.test.Question;
import com.itkvant.itkvant.model.test.Test;
import com.itkvant.itkvant.service.quizService.QuestionsQuizService;
import com.itkvant.itkvant.service.quizService.TestQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class QuizController {

    @Autowired
    @Qualifier("testService")
    private TestQuizService testQuizService;

    @Autowired
    @Qualifier("questionService")
    private QuestionsQuizService questionService;

    @GetMapping("/quizs")
    public String allTestsQuiz(Model model) {
        model.addAttribute("tests", testQuizService.getAllTestQuizs());
        return "tests_main";
    }

    @GetMapping("/quiz/{id}")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<List<Question>> getTest(@PathVariable long id) {
        List<Question> questions = questionService.getAllQuestionsByTestId(id);
        if (questions != null) {
            return ResponseEntity.ok(questions);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    @GetMapping("/test/{id}")
    public String test(@PathVariable long id, Model model) {
        System.out.println(questionService.getAllQuestionsByTestId(id));
        model.addAttribute("questions", questionService.getAllQuestionsByTestId(id));
        model.addAttribute("testId", id);
        return "quizs";
    }

    @PostMapping("/quiz_save")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> saveResqultQuiz(@RequestBody QuizResultDTO quizResultDTO, Authentication authentication) {
        long userId = ((User) authentication.getPrincipal()).getId();
        System.out.println(quizResultDTO.getTestId());
        testQuizService.testScores(userId, quizResultDTO.getTestId(), quizResultDTO.getQuestionsId(), quizResultDTO.getCorrectAnswersCount());
        return new ResponseEntity<>("Responses received successful", HttpStatus.OK);
    }



    @GetMapping("/createTest")
    public String createTestPage() {
        return "createTest";
    }

    @PostMapping("/add-test")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> createTest(@RequestBody TestRequest testRequest) {
        // Создание объекта Test из данных в testCreateRequest
        Test test = new Test();
        test.setTestName(testRequest.getTestName());


        List<Question> questions = new ArrayList<>();
        for (QuestionRequest questionRequest : testRequest.getQuestions()) {
            Question question = new Question();
            question.setQuestionText(questionRequest.getName());
            question.setAward(questionRequest.getAward());
            question.setTest(test);
            List<Answer> answers = new ArrayList<>();
            for (AnswerRequest answerCreateRequest : questionRequest.getAnswers()) {
                Answer answer = new Answer();
                answer.setAnswerText(answerCreateRequest.getName());
                answer.setCorrect(answerCreateRequest.getIsCorrect());
                answer.setQuestion(question); // Установите связь с вопросом
                answers.add(answer);
            }

            question.setAnswers(answers);

            questions.add(question);
        }

        test.setQuestions(questions);

        testQuizService.createTest(test);

        return new ResponseEntity<>("Test create successful", HttpStatus.OK);
    }


}
