package com.itkvant.itkvant.repository.repoQuiz;

import com.itkvant.itkvant.model.User;
import com.itkvant.itkvant.model.Wallet;
import com.itkvant.itkvant.model.test.HistoryQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface HistoryQuizRepository extends JpaRepository<HistoryQuiz, Long> {
    List<HistoryQuiz> findAllByUserId(Long userId);
}
