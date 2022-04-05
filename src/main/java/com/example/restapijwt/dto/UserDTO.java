package com.example.restapijwt.dto;

import com.example.restapijwt.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String username, password, email;

    private List<Integer> roleList;

}
