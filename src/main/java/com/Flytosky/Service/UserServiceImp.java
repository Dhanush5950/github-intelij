package com.Flytosky.Service;

import com.Flytosky.config.JwtProvider;
import com.Flytosky.model.User;
import com.Flytosky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findByEmail(email);

        return user;
    }

    @Override
    public User findByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user==null)
        {
            throw new Exception("User not found");
        }

        return user;
    }
}
