package com.mihey.quiz.repository;

import com.mihey.quiz.model_quiz.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
