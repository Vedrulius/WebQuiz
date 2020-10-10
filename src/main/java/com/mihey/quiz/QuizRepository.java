package com.mihey.quiz;

import com.mihey.quiz.model_quiz.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz,Long> {

}
