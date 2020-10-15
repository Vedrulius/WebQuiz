package com.mihey.quiz.repository;

import com.mihey.quiz.model_completed_quizzes.CompletedQuizzes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CompletedRepository extends JpaRepository<CompletedQuizzes, LocalDateTime> {
    Page<CompletedQuizzes> findByUserEmail(String userEmail, Pageable pageable);
}
