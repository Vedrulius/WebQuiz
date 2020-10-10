package com.mihey.quiz;

import com.mihey.quiz.model_quiz.Info;
import com.mihey.quiz.model_quiz.Quiz;
import com.mihey.quiz.model_user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class QuizController {

    public QuizController() {
    }

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/register")
    public User addUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userRepository.save(user);
    }

    @PostMapping("/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        if (id < 1 || quizRepository.count() < id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return quizRepository.findById(id).get();
    }

    @GetMapping(path = "/api/quizzes")
    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Info answer(@PathVariable long id, @RequestBody Quiz answer) {
        if (id < 1 || quizRepository.count() < id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (Arrays.equals(answer.getAnswer(), quizRepository.findById(id).get().getAnswer())) {
            return new Info(true, "Congratulations, you're right!");
        }

        return new Info(false, "Wrong answer! Please, try again.");
    }
}
