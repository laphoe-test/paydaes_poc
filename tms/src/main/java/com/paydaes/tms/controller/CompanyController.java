package com.paydaes.tms.controller;

import com.paydaes.entities.dto.ClientDto;
import com.paydaes.entities.dto.CompanyDto;
import com.paydaes.tms.service.ClientService;
import com.paydaes.tms.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tms/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(final @RequestBody CompanyDto companyDto) {
        try {
            return new ResponseEntity<>(companyService.createCompany(companyDto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<CompanyDto> searchCompany(final @RequestParam String name) {
        return new ResponseEntity<>(companyService.getCompanyByName(name), HttpStatus.OK);
    }

}