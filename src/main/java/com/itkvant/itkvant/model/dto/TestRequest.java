package com.itkvant.itkvant.model.dto;

import com.itkvant.itkvant.model.test.Question;
import lombok.Data;

import java.util.List;


@Data
public class TestRequest {
    private String testName;
    private List<QuestionRequest> questions;
}
