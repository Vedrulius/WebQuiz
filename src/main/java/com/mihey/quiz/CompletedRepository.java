package com.mihey.quiz;

import com.mihey.quiz.model_completed_quizzes.CompletedQuizzes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CompletedRepository extends JpaRepository<CompletedQuizzes, LocalDateTime> {
}
