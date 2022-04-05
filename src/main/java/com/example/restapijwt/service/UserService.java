package com.example.restapijwt.service;

import com.example.restapijwt.dto.ApiResponse;
import com.example.restapijwt.dto.UserDTO;
import com.example.restapijwt.entity.Role;
import com.example.restapijwt.entity.Turniket;
import com.example.restapijwt.entity.User;
import com.example.restapijwt.repository.RoleRepository;
import com.example.restapijwt.repository.TurniketRepository;
import com.example.restapijwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final TurniketRepository turniketRepository;
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    public ApiResponse add(UserDTO dto) {
        List<Role> roles = roleRepository.findAllById(dto.getRoleList());
        if (roles.isEmpty()) return new ApiResponse("Roles not found",false);

        User user=new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoleList(roles);


        Turniket turniket=new Turniket();
        turniket.setUser(user);

        turniketRepository.save(turniket);

        userRepository.save(user);

        return new ApiResponse("Added",true);
    }

    public ApiResponse edit(Integer id, UserDTO dto) {
        List<Role> roles = roleRepository.findAllById(dto.getRoleList());
        if (roles.isEmpty()) return new ApiResponse("Roles not found",false);

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return new ApiResponse("User not found", false);

        User user = optionalUser.get();

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoleList(roles);

        userRepository.save(user);

        return new ApiResponse("Edited",true);
    }
}
