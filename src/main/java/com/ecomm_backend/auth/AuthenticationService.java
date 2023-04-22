package com.ecomm_backend.auth;

import com.ecomm_backend.config.JwtService;
import com.ecomm_backend.entities.Role;
import com.ecomm_backend.entities.User;
import com.ecomm_backend.repositoreis.RoleRepository;
import com.ecomm_backend.repositoreis.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user=addNewUser(request);
        if (roleRepository.findByRoleName(request.getRoleName()).isEmpty()){
            addNewRole(request.getRoleName());
        }

        addRoleToUser(request.getEmail(), request.getRoleName());

        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User addNewUser(RegisterRequest request) {
        var user= User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        return user;
    }

    public void addNewRole(String roleName) {
        var role= Role.builder()
                .roleName(roleName)
                .build();
        roleRepository.save(role);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow();
    }

    public void addRoleToUser(String email, String roleName) {
        User user=findUserByEmail(email);
        Role role=findRoleByRoleName(roleName);

        if(user.getRoles()!=null){
            user.getRoles().add(role);
        } else {
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
        }

        if(role.getUsers()!=null){
            role.getUsers().add(user);
        } else {
            List<User> users = new ArrayList<>();
            users.add(user);
            role.setUsers(users);
        }
        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        List<String> roles=new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getRoleName()));
        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .roles(roles)
                .token(jwtToken)
                .build();
    }
}