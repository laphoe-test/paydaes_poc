package com.paydaes.corehr.client;

import com.paydaes.entities.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="company", url = "http://localhost:8081/api/tms/company")
public interface CompanyClient{

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    CompanyDto searchCompany(final @RequestParam String name);
}
