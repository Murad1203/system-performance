package com.itkvant.itkvant.model.test;


import com.itkvant.itkvant.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HistoryQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "test_id")
    private Test testId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @JoinColumn(name = "count_true_answers")
    private int countTrueAnswers;

//    Сколько баллов(KVANT) получил пользователь за тест
    private double await;
}
