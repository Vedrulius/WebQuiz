package com.mihey.quiz;

import com.mihey.quiz.model_quiz.Info;
import com.mihey.quiz.model_quiz.Quiz;
import com.mihey.quiz.model_user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
public class QuizController {

    public QuizController() {
    }

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
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
//        String username = authentication.getName();
//        quiz.setEmail(username);
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
    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Info answer(@PathVariable long id, @RequestBody Quiz answer) {
        if (id < 1 || quizRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (Arrays.equals(answer.getAnswer(), quizRepository.findById(id).get().getAnswer())) {
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
