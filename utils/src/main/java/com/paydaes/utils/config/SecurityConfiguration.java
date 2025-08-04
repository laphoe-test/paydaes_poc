package com.paydaes.utils.config;

import com.paydaes.utils.security.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.paydaes.utils.security.SecurityService.ALGORITHM;

@Configuration
@PropertySource("classpath:key.properties")
public class SecurityConfiguration {
   @Value("${key}")
    private String key;

    @Value("${iv}")
    private String iv;

    @Bean
    SecurityService getSecurityService(){
        byte[] hexKey = hexStringToBytes(key);
        return SecurityService.builder()
                .secretKey(new SecretKeySpec(hexStringToBytes(key), 0, hexKey.length, ALGORITHM))
                .ivSpec(new IvParameterSpec(hexStringToBytes(iv)))
                .build();
    }


    byte[] hexStringToBytes(String hexString){
        int len = hexString.length();
        byte[] data = new byte[len / 2]; // The byte array will be half the length of the hex string
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
}