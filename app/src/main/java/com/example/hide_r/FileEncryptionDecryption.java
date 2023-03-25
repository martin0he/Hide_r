package com.example.hide_r;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import javax.crypto.*;



    public class FileEncryptionDecryption {
        private static final String ALGORITHM = "AES";
        private static final int BUFFER_SIZE = 8192;
        private static String secretKey = "password";
        private static String ivSpec = "my_IV_spec";

        public static void encryptFile(File inputFile, File outputFile) throws Exception {
            byte[] keyBytes = secretKey.getBytes("UTF-8");
            byte[] ivBytes = ivSpec.getBytes("UTF-8");

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            IvParameterSpec ivSpecObj = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance(ALGORITHM + "/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpecObj);

            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }

            cipherOutputStream.close();
            outputStream.close();
            inputStream.close();
        }

        public static void decryptFile(File inputFile, File outputFile) throws Exception {
            byte[] keyBytes = secretKey.getBytes("UTF-8");
            byte[] ivBytes = ivSpec.getBytes("UTF-8");

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            IvParameterSpec ivSpecObj = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpecObj);

            FileInputStream inputStream = new FileInputStream(inputFile);
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            cipherInputStream.close();
            inputStream.close();
        }



    public static void saveToEncryptedFolder(byte[] bArr, String str, File file) throws Exception {
        byte[] keyBytes = secretKey.getBytes("UTF-8");


        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");


        Cipher cipher = Cipher.getInstance(ALGORITHM + "/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedBytes = cipher.doFinal(bArr);

        file.mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(file, str));
        fileOutputStream.write(encryptedBytes);
        fileOutputStream.close();
    }

}
