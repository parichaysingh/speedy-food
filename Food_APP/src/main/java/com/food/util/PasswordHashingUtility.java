package com.food.util;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component 
public class PasswordHashingUtility {
	
	public static final Logger LOG = LoggerFactory.getLogger(PasswordHashingUtility.class);
	
	@Value("${password.hash.min.iterations}")
	public int minIterations;

	@Value("${password.hash.max.iterations}")
	public int maxIterations;
	
	@Value("${password.hash.algorithm}")
	public String algorithm;

	@Value("${key.length}")
	public int keyLength;
	
	@Value("${salt.length}")
	public int saltLength;
	
	SecureRandom random = new SecureRandom();
	
    public PasswordHashingUtility() {}

    public String getPasswordHash(String password) {
        return encode(password, generateRandomSalt() , getIterationCount());
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and HASH
        // parts are joined with : character
        String[] parts = hashedPassword.split(":");
        if (parts.length != 4) {
            // Wrong hash format
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = encode(password, salt, iterations);
        return hash.equals(hashedPassword);
    }
    
    private String encode(String password, String salt, int iterations) {
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s:%d:%s:%s", algorithm, iterations, salt, hash);
    }
    
    private String getEncodedHash(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
        	LOG.error("PasswordHashingUtility : getEncodedHash : Could NOT retrieve ", algorithm ," algorithm : ",e);
            return null;
        }
        KeySpec keySpec = new PBEKeySpec((password+salt).toCharArray(), salt.getBytes(Charset.forName("UTF-8")) , iterations, keyLength);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
        	LOG.error("PasswordHashingUtility : getEncodedHash : Could NOT generate secret key : ",e);
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    private String generateRandomSalt(){
        byte bytes[] = new byte[saltLength];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    private int getIterationCount(){
    	return ThreadLocalRandom.current().nextInt(minIterations,maxIterations);
    }
}