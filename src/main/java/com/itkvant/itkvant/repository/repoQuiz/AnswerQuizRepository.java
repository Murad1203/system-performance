package com.itkvant.itkvant.repository.repoQuiz;

import com.itkvant.itkvant.model.test.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerQuizRepository extends JpaRepository<Answer, Long> {
}
