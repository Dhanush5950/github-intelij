package com.Flytosky.controller;

import java.util.Collection;
import com.Flytosky.Service.CustomerUserDetailsService;
import com.Flytosky.config.JwtProvider;
import com.Flytosky.model.Cart;
import com.Flytosky.model.USER_ROLE;
import com.Flytosky.model.User;
import com.Flytosky.repository.CartRepository;
import com.Flytosky.repository.UserRepository;
import com.Flytosky.request.LoginRequest;
import com.Flytosky.request.SignupRequest;
import com.Flytosky.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> createUserHandler(@Valid @RequestBody SignupRequest signupRequest) {

        if (signupRequest.getPassword() == null || signupRequest.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        User user = new User();
        user.setFullName(signupRequest.getFullName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(USER_ROLE.ROLE_CUSTOMER);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }



    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {

        String username = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(username, password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if(userDetails==null)
        {
            throw new BadCredentialsException("Invalid username...........");

        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {

            throw new BadCredentialsException("invalid password..........");

        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

}





