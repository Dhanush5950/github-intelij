package com.Flytosky.Service;

import com.Flytosky.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;


    public User findByEmail(String email) throws Exception;
}
