package com.itkvant.itkvant.repository.repoQuiz;

import com.itkvant.itkvant.model.test.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestQuizRepository extends JpaRepository<Test, Long> {
}
