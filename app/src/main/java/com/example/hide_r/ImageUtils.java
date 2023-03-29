package com.example.hide_r;

import android.content.Context;
import android.util.Base64;
import java.io.File;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class ImageUtils {

    public static void saveImageToEncryptedDirectory(File directory, String imageName, byte[] imageData, String encryptionKey) throws Exception {
        // Encode the image data to Base64
        String encodedImageData = Base64.encodeToString(imageData, Base64.DEFAULT);
        // Create the file in the directory
        File file = new File(directory, imageName);
        // Create the encryption key
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        // Create the encryption cipher
        Cipher encryptionCipher = Cipher.getInstance("AES");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // Write the encrypted image data to the file
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, encryptionCipher);
        cipherOutputStream.write(encodedImageData.getBytes());
        cipherOutputStream.close();
    }
}
