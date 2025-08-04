package com.paydaes.corehr.controller;

import com.paydaes.corehr.client.CompanyClient;
import com.paydaes.corehr.config.MultiTenantManager;
import com.paydaes.entities.dto.CompanyDto;
import com.paydaes.utils.security.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class TenantFilter implements Filter {

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private MultiTenantManager multiTenantManager;

    @Autowired
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String tenantName = httpRequest.getHeader("Tenant-name");

        try {
            CompanyDto dto = companyClient.searchCompany(tenantName);
            String url = securityService.decrypt(dto.getCipherDbUrl());
            String user = securityService.decrypt(dto.getCipherDbUser());
            String password = securityService.decrypt(dto.getCipherDbPassword());

            multiTenantManager.addTenant(dto.getName(), url, user, password);
            multiTenantManager.setCurrentTenant(tenantName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        chain.doFilter(request, response); // Continue the filter chain
        multiTenantManager.removeCurrentTenant();
    }
}
