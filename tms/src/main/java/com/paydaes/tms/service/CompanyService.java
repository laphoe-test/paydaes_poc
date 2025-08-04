package com.paydaes.tms.service;

import com.paydaes.entities.dto.CompanyDto;
import com.paydaes.entities.model.Company;
import com.paydaes.entities.model.Employee;
import com.paydaes.entities.repository.CompanyRepository;
import com.paydaes.utils.database.DatabaseService;
import com.paydaes.utils.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    DatabaseService databaseService;

    public CompanyDto createCompany(final CompanyDto companyDto) {
        if (companyRepository.findByName(companyDto.getName()).isPresent()) {
            throw new RuntimeException("Company already exists: " + companyDto.getName());
        }

        String dbName = "company_"+companyDto.getName();
        String url = databaseService.create(dbName,companyDto.getName(),companyDto.getPassword(), List.of(Employee.class));

        try {
            Company company = companyRepository.save(
                    Company.builder()
                            .clientId(companyDto.getClientId())
                            .name(companyDto.getName())
                            .cipherDbUrl(securityService.encrypt(url))
                            .cipherDbUser(securityService.encrypt(companyDto.getName()))
                            .cipherDbPassword(securityService.encrypt(companyDto.getPassword()))
                            .build()
            );


            return convertToDto(company);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CompanyDto getCompanyByName(final String name) {
        return companyRepository.findByName(name).map(this::convertToDto).orElseThrow();
    }
    
    private CompanyDto convertToDto(final Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .clientId(company.getClientId())
                .name(company.getName())
                .cipherDbUrl(company.getCipherDbUrl())
                .cipherDbUser(company.getCipherDbUser())
                .cipherDbPassword(company.getCipherDbPassword())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }
}