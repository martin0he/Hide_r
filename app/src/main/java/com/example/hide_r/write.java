package com.example.hide_r;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class write {

    public static void saveByteArrayToFile(byte[] byteArray, File directory, String fileName) {
        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Create a new file with the given directory and file name
        File file = new File(directory, fileName);

        try {
            // Create a new FileOutputStream for the file
            FileOutputStream outputStream = new FileOutputStream(file);

            // Write the byte array to the file
            outputStream.write(byteArray);

            // Close the FileOutputStream
            outputStream.close();


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
