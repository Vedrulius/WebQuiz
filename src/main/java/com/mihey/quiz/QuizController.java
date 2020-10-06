package com.mihey.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class QuizController {

    public QuizController() {
    }

    private int id = 1;
    private List<Quiz> quizzes = new ArrayList<>();

    @PostMapping("/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {
        quizzes.add(quiz);
        quiz.setId(id);
        id++;
        return quiz;
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        if (id < 1 || quizzes.size() < id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return quizzes.get(id - 1);
    }

    @GetMapping(path = "/api/quizzes")
    public List<Quiz> getQuizzesArray() {
        return quizzes;
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Info answer(@PathVariable int id, @RequestBody Quiz answer) {
        if (id < 1 || quizzes.size() < id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (Arrays.equals(answer.getAnswer(),quizzes.get(id-1).getAnswer())) {
            return new Info(true, "Congratulations, you're right!");
        }

        return new Info(false, "Wrong answer! Please, try again.");
    }


}
