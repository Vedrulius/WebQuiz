package com.mihey.quiz.controller;

import com.mihey.quiz.repository.CompletedRepository;
import com.mihey.quiz.repository.QuizRepository;
import com.mihey.quiz.repository.UserRepository;
import com.mihey.quiz.model_completed_quizzes.CompletedQuizzes;
import com.mihey.quiz.model_quiz.Info;
import com.mihey.quiz.model_quiz.Quiz;
import com.mihey.quiz.model_user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
public class QuizController {

    public QuizController() {
    }

    @Autowired
    private CompletedRepository completedRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/register")
    public User addUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, Principal user) {

        quiz.setEmail(user.getName());
        return quizRepository.save(quiz);
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        if (id < 1 || quizRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return quizRepository.findById(id).get();
    }


    @GetMapping(path = "/api/quizzes")

    public Page<Quiz> getAllQuizzes(@RequestParam(required = false, defaultValue = "0") int page) {
        return quizRepository.findAll(PageRequest.of(page, 10));
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page<CompletedQuizzes> getCompleted(@RequestParam(required = false, defaultValue = "0") int page,
                                               Principal principal) {
        return completedRepository.findByUserEmail(principal.getName(),
                PageRequest.of(page, 10, Sort.by("completedAt").descending()));
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Info answer(@PathVariable long id, @RequestBody Quiz answer, Principal principal) {
        if (id < 1 || quizRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (Arrays.equals(answer.getAnswer(), quizRepository.findById(id).get().getAnswer())) {
            completedRepository.save(new CompletedQuizzes(id,
                    LocalDateTime.now(),
                    principal.getName()));
            return new Info(true, "Congratulations, you're right!");
        }

        return new Info(false, "Wrong answer! Please, try again.");
    }

    @DeleteMapping("/api/quizzes/{id}")
    public void deleteQuizById(@PathVariable long id, Principal user) {

        String username = user.getName();
        if (id < 1 || quizRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!quizRepository.findById(id).get().getEmail().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.deleteById(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
}
