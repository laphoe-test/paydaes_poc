package com.paydaes.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {
    private Long clientId;
    private Long id;
    private String name;
    private String password;
    private String cipherDbUser;
    private String cipherDbPassword;
    private String cipherDbUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}