package com.paydaes.utils.security;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

@Builder
@AllArgsConstructor
public class SecurityService {
    public static final String ALGORITHM = "AES";
    public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private SecretKey secretKey;
    private IvParameterSpec ivSpec;

    public String encrypt(String plaintext) throws Exception{

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] originalBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(originalBytes);
    }
}