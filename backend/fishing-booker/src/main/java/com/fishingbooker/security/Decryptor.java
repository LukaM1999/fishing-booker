package com.fishingbooker.security;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class Decryptor {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    public static String decrypt(String encryptedBase64PAN) throws Exception {
        // Add the Bouncy Castle provider
        Security.addProvider(new BouncyCastleProvider());

        // Create the cipher object
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        byte[] encryptedPAN = Base64.getDecoder().decode(encryptedBase64PAN);

        // Extract the IV from the encrypted data
        byte[] iv = Arrays.copyOfRange(encryptedPAN, 0, GCM_IV_LENGTH);
        byte[] cipherText = Arrays.copyOfRange(encryptedPAN, GCM_IV_LENGTH, encryptedPAN.length);

        // Create the secret key
        SecretKeySpec secretKey = new SecretKeySpec(getSecretKey(), "AES");

        // Initialize the cipher in decrypt mode
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv));

        // Decrypt the PAN number
        byte[] decryptedPAN = cipher.doFinal(cipherText);

        // Return the decrypted PAN as a string
        return new String(decryptedPAN);
    }

    private static byte[] getSecretKey(){
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
        String secret = getSecretValueResponse.getSecretString();
        return secret.getBytes();
    }

    public static void main(String[] args) throws Exception {
        String panNumber = "7774567891234567";
        String encryptedPAN = Encryptor.encrypt(panNumber);
        System.out.println("Encrypted PAN: " + encryptedPAN);
        String decryptedPAN = decrypt(encryptedPAN);
        System.out.println(decryptedPAN);
    }
}

