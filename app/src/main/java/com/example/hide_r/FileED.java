package com.example.hide_r;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class FileED {
    private static final int BUFFER_SIZE = 1024;
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final int ITERATION_COUNT = 65536;
    private static final int IV_LENGTH = 16;
    private static final int KEY_LENGTH = 128;
    private static final String KEY_SPEC_ALGORITHM = "AES";
    private static final int PBE_KEY_LENGTH = 128;
    private static final int SALT_LENGTH = 16;
    private static final String SECRET_KEY_TYPE = "PBKDF2WithHmacSHA1";

    public static void encrypt(File file, File file2, String str) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bArr = new byte[16];
        secureRandom.nextBytes(bArr);
        byte[] bArr2 = new byte[16];
        secureRandom.nextBytes(bArr2);
        SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance(SECRET_KEY_TYPE).generateSecret(new PBEKeySpec(str.toCharArray(), bArr, 65536, 128)).getEncoded(), "AES");
        Cipher instance = Cipher.getInstance(CIPHER_TRANSFORMATION);
        instance.init(1, secretKeySpec, new IvParameterSpec(bArr2));
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr3 = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        fileOutputStream.write(bArr);
        fileOutputStream.write(bArr2);
        while (true) {
            int read = fileInputStream.read(bArr3);
            if (read == -1) {
                break;
            }
            byte[] update = instance.update(bArr3, 0, read);
            if (update != null) {
                fileOutputStream.write(update);
            }
        }
        byte[] doFinal = instance.doFinal();
        if (doFinal != null) {
            fileOutputStream.write(doFinal);
        }
        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static void decrypt(File file, File file2, String str) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[16];
        fileInputStream.read(bArr);
        byte[] bArr2 = new byte[16];
        fileInputStream.read(bArr2);
        SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance(SECRET_KEY_TYPE).generateSecret(new PBEKeySpec(str.toCharArray(), bArr, 65536, 128)).getEncoded(), "AES");
        Cipher instance = Cipher.getInstance(CIPHER_TRANSFORMATION);
        instance.init(2, secretKeySpec, new IvParameterSpec(bArr2));
        byte[] bArr3 = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        while (true) {
            int read = fileInputStream.read(bArr3);
            if (read == -1) {
                break;
            }
            byte[] update = instance.update(bArr3, 0, read);
            if (update != null) {
                fileOutputStream.write(update);
            }
        }
        byte[] doFinal = instance.doFinal();
        if (doFinal != null) {
            fileOutputStream.write(doFinal);
        }
        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
