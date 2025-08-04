package com.paydaes.utils.config;

import com.paydaes.utils.database.DatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

    @Bean
    DatabaseService getDatabaseService(){
        return DatabaseService.builder().build();
    }

}
