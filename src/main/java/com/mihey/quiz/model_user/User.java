package com.mihey.quiz.model_user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mihey.quiz.model_completed_quizzes.CompletedQuizzes;
import com.mihey.quiz.model_quiz.Quiz;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @Email
    @Pattern(regexp=".+@.+\\..+")
    private String email;
    @Size(min = 5)
    private String password;

    @OneToMany
    @JoinColumn(name = "userEmail")
    @JsonIgnore
    private List<CompletedQuizzes> quizzes = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CompletedQuizzes> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<CompletedQuizzes> quizzes) {
        this.quizzes = quizzes;
    }
}
