package com.itkvant.itkvant.model.dto;

import com.itkvant.itkvant.model.test.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuizResultDTO {
    private long testId;
    private int correctAnswersCount;
    private List<Question> questionsId;
}
