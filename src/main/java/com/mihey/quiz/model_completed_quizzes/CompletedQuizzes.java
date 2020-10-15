package com.mihey.quiz.model_completed_quizzes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class CompletedQuizzes {

    private long id;
    @Id
    private LocalDateTime completedAt;
    @JsonIgnore
    private String userEmail;

    public CompletedQuizzes() {
    }

    public CompletedQuizzes(long id, LocalDateTime completedAt, String userEmail) {
        this.completedAt = completedAt;
        this.id = id;
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

}
