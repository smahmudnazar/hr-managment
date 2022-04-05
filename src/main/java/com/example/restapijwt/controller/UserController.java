package com.example.restapijwt.controller;

import com.example.restapijwt.aop.Check;
import com.example.restapijwt.aop.CurrentUser;
import com.example.restapijwt.dto.ApiResponse;
import com.example.restapijwt.dto.CompanyDTO;
import com.example.restapijwt.dto.UserDTO;
import com.example.restapijwt.entity.Company;
import com.example.restapijwt.entity.User;
import com.example.restapijwt.exception.ResourceNotFoundException;
import com.example.restapijwt.repository.UserRepository;
import com.example.restapijwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    final UserRepository userRepository;
    final UserService userService;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Integer id) {
        Optional<User> byId = userRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new User()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.ok().body("User not Found");
        }
        userRepository.delete(user.get());

        return ResponseEntity.ok().body("Deleted");
    }

    @PostMapping
    public ResponseEntity add(@RequestBody UserDTO dto){
        ApiResponse add = userService.add(dto);
        return ResponseEntity.ok().body(add);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody UserDTO dto) {
        ApiResponse response = userService.edit(id, dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }



    @PreAuthorize("hasAuthority('ADD_COMPANY')")
    @GetMapping("/me")
    public ResponseEntity getMe() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            throw new ResourceNotFoundException("User topilmadi!");
        }
        return ResponseEntity.ok(user);
    }

//    @Check(value = "ADD_COMPANY")
//    @PreAuthorize("hasAuthority('ADD_COMPANY')")
//    @GetMapping
//    public ResponseEntity getMe(@CurrentUser User user) {
//        return ResponseEntity.ok(user);
//    }
}
