package com.example.restapijwt.service;

import com.example.restapijwt.dto.ApiResponse;
import com.example.restapijwt.dto.CompanyDTO;
import com.example.restapijwt.entity.Company;
import com.example.restapijwt.entity.User;
import com.example.restapijwt.repository.CompanyRepository;
import com.example.restapijwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    final CompanyRepository companyRepository;
    final UserRepository userRepository;

    public ApiResponse add(CompanyDTO dto) {
        Optional<User> user = userRepository.findById(dto.getDirector_id());
        if (user.isEmpty()) return new ApiResponse("User not found",false);

        Company company=new Company();
        company.setName(dto.getName());
        company.setDirector(user.get());

        companyRepository.save(company);

        return new ApiResponse("Added",true);

    }

    public ApiResponse edit(Integer id, CompanyDTO dto) {
        Optional<User> user = userRepository.findById(dto.getDirector_id());
        if (user.isEmpty()) return new ApiResponse("User not found",false);

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) return new ApiResponse("Company not found",false);

        Company company = optionalCompany.get();
        company.setDirector(user.get());
        company.setName(dto.getName());

        companyRepository.save(company);

        return new ApiResponse("Edited",true);

    }
}
