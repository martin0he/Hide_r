package com.example.hide_r;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class SortFolder {

    public static void sortFolderBySize(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // Sort the files by size
        assert files != null;
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return Long.compare(file1.length(), file2.length());
            }
        });
    }

    public static void sortByDate(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        Arrays.sort(listOfFiles, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }
        });
    }


    public static void reverseSortFiles(File folder){

        File[] files = folder.listFiles();
        Arrays.sort(files, Comparator.reverseOrder());

        for (File file : files) {
            if (file.isDirectory()) {
                reverseSortFiles(file);
            } else {
                // do something
            }
        }
    }



}
