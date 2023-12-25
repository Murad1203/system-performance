package com.itkvant.itkvant.repository.repoQuiz;

import com.itkvant.itkvant.model.test.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionQuizRepository extends JpaRepository<Question, Long> {


    List<Question> findAllByTestId(long id);
}
