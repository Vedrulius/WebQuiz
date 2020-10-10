package com.mihey.quiz;

import com.mihey.quiz.model_user.MyUserDetails;
import com.mihey.quiz.model_user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)  {

        Optional<User> user = userRepository.findByEmail(email);
        user.orElseThrow(()->new UsernameNotFoundException("Not found" + email));
        return user.map(MyUserDetails::new).get();
    }
}
