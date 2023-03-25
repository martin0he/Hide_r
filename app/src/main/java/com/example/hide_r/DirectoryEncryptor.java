package com.example.hide_r;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class DirectoryEncryptor {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final int KEY_LENGTH = 256;

    private byte[] keyBytes;

    public DirectoryEncryptor(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] key = md.digest(password.getBytes("UTF-8"));
            keyBytes = Arrays.copyOf(key, KEY_LENGTH / 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encryptDirectory(File directory, File outputDirectory) {
        outputDirectory.mkdirs();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                encryptDirectory(file, new File(outputDirectory, file.getName()));
            } else {
                encryptFile(file, new File(outputDirectory, file.getName()));
            }
        }
    }

    public void decryptDirectory(File directory, File outputDirectory) {
        outputDirectory.mkdirs();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                decryptDirectory(file, new File(outputDirectory, file.getName()));
            } else {
                decryptFile(file, new File(outputDirectory, file.getName()));
            }
        }
    }

    private void encryptFile(File inputFile, File outputFile) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(Base64.getEncoder().encode(outputBytes));
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decryptFile(File inputFile, File outputFile) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(Base64.getDecoder().decode(inputBytes));
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
