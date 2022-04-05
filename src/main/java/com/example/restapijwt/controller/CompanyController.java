package com.example.restapijwt.controller;


import com.example.restapijwt.dto.ApiResponse;
import com.example.restapijwt.dto.CompanyDTO;
import com.example.restapijwt.entity.Company;
import com.example.restapijwt.repository.CompanyRepository;
import com.example.restapijwt.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    final CompanyRepository companyRepository;
    final CompanyService companyService;


    @GetMapping
    public ResponseEntity getAll(){
        List<Company> all = companyRepository.findAll();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Integer id) {
        Optional<Company> byId = companyRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Company()));
    }

    @PostMapping()
    public ResponseEntity add(@RequestBody CompanyDTO dto){
        ApiResponse add = companyService.add(dto);
        return ResponseEntity.ok().body(add);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()){
            return ResponseEntity.ok().body("Company not Found");
        }
        companyRepository.delete(company.get());

        return ResponseEntity.ok().body("Deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody CompanyDTO dto) {
        ApiResponse response = companyService.edit(id, dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
