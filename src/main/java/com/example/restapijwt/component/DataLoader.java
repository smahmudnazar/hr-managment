package com.example.restapijwt.component;


import com.example.restapijwt.entity.Company;
import com.example.restapijwt.entity.Role;
import com.example.restapijwt.entity.User;
import com.example.restapijwt.entity.enums.PermissionEnum;
import com.example.restapijwt.entity.enums.RoleEnum;
import com.example.restapijwt.repository.CompanyRepository;
import com.example.restapijwt.repository.RoleRepository;
import com.example.restapijwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    //field metod
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    @Value("${spring.sql.init.mode}")
    String mode;


    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            PermissionEnum[] values = PermissionEnum.values();

            List<Role> roleList = new ArrayList<>();
            Role userRole = roleRepository.save(new Role(RoleEnum.ROLE_USER, new ArrayList<>(Arrays.asList(PermissionEnum.READ_ALL_COMPANY, PermissionEnum.READ_ONE_COMPANY))));
            Role adminRole = roleRepository.save(new Role(RoleEnum.ROlE_ADMIN, Arrays.asList(values)));
            Role moderRole = roleRepository.save(new Role(RoleEnum.ROLE_MODERATOR, Arrays.asList(PermissionEnum.READ_ALL_COMPANY, PermissionEnum.EDIT_COMPANY, PermissionEnum.READ_ONE_COMPANY)));
            roleList.add(userRole);
            roleList.add(adminRole);
            roleList.add(moderRole);

            User jafar = userRepository.save(new User("Jafar", passwordEncoder.encode("123"), roleList));
            companyRepository.save(new Company(1, "PDP", jafar));
        }
    }
}
