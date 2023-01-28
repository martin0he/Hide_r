package com.example.hide_r;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptorDecryptor {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static void encrypt(String str, FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), ALGORITHM);
        Cipher instance = Cipher.getInstance(TRANSFORMATION);
        instance.init(1, secretKeySpec);
        byte[] bArr = new byte[((int) fileInputStream.getChannel().size())];
        fileInputStream.read(bArr);
        fileOutputStream.write(instance.doFinal(bArr));
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static void decrypt(String str, FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), ALGORITHM);
        Cipher instance = Cipher.getInstance(TRANSFORMATION);
        instance.init(2, secretKeySpec);
        byte[] bArr = new byte[((int) fileInputStream.getChannel().size())];
        fileInputStream.read(bArr);
        fileOutputStream.write(instance.doFinal(bArr));
        fileInputStream.close();
        fileOutputStream.close();
    }
}
