package com.fishingbooker.security;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

@Slf4j
public class Encryptor {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    private static String SECRET_KEY = "";

    public static String encrypt(String panNumber) {
        // Add the Bouncy Castle provider
        Security.addProvider(new BouncyCastleProvider());

        // Create the cipher object
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        // Generate a random initialization vector (IV)
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        if(SECRET_KEY.equals("")){
            setSecretKey();
        }

        // Create the secret key
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        // Initialize the cipher in encrypt mode
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        // Encrypt the PAN number
        byte[] encryptedPAN;
        try {
            encryptedPAN = cipher.doFinal(panNumber.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        // Concatenate the IV and the encrypted PAN and return the result
        byte[] cypherText = concatenate(iv, encryptedPAN);

        return Base64.getEncoder().encodeToString(cypherText);
    }

    private static byte[] concatenate(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;
        byte[] c = new byte[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    private static void setSecretKey(){
        log.info("Fetching secret key from AWS Secrets Manager");
        AwsClientBuilder.EndpointConfiguration  config = new AwsClientBuilder
                .EndpointConfiguration("secretsmanager.us-east-1.amazonaws.com", "us-east-1");
        AWSSecretsManagerClientBuilder clientBuilder  =  AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        AWSSecretsManager client = clientBuilder.build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId("employment-agency-secret");
        GetSecretValueResult getSecretValueResponse = null;
        try  {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        }
        catch (Exception e) {
            log.error("The requested secret 'we-marvel-firebase-config' was not found");
        }
        assert getSecretValueResponse != null;
        SECRET_KEY = getSecretValueResponse.getSecretString();
    }

    public static void main(String[] args) {
        String panNumber = "321";
        String encryptedPAN = Encryptor.encrypt(panNumber);
        System.out.println("Encrypted PAN: " + encryptedPAN);
    }
}


