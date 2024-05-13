package com.example.LibrarySystem.Authentication.Service;

import com.example.LibrarySystem.Authentication.Entity.Users;
import com.example.LibrarySystem.Authentication.Helpers.AuthenticationRequest;
import com.example.LibrarySystem.Authentication.Helpers.AuthenticationResponse;
import com.example.LibrarySystem.Authentication.Helpers.RegisterRequest;
import com.example.LibrarySystem.Authentication.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request){
        Users user =new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        System.out.println(user);
        //!!! issue here
        usersRepository.save(user);
        var jwtToken =jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),request.getPassword()
        ));
        var user = usersRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken =jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
