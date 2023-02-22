package com.example.hide_r;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class FileEncryptionDecryption {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    // Method for encrypting a file
    public static void encryptFile(File file, File encryptedFile, String secretKey) throws Exception {
        Key key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        FileInputStream inputStream = new FileInputStream(file);
        byte[] inputBytes = new byte[(int) file.length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(encryptedFile);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }

    // Method for decrypting a file
    public static void decryptFile(File encryptedFile, File decryptedFile, String secretKey) throws Exception {
        Key key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);

        FileInputStream inputStream = new FileInputStream(encryptedFile);
        byte[] inputBytes = new byte[(int) encryptedFile.length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(decryptedFile);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }
}
