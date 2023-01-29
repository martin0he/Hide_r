package com.example.hide_r;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class Gallery extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // on below line we are creating variables for
    // our array list, recycler view and adapter class.
    private static final int PERMISSION_REQUEST_CODE = 200;
    private ArrayList<String> imagePaths;
    private RecyclerView imagesRV;
    private RecyclerViewAdapter imageRVAdapter;
    private Button returnButton;
    String[] sorters = {"Ascending", "Descending"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        returnButton = (Button) findViewById(R.id.goBackToCam);
        assert returnButton != null;
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivities();
            }
        });



        requestPermission();            // we are calling a method to request the permissions to read external storage.
        imagePaths = new ArrayList<>();                     // creating a new array list and initializing our recycler view.
        imagesRV = findViewById(R.id.idRVImages);



        Spinner sort = findViewById(R.id.sortBtn);


        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sorters);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setOnItemSelectedListener(this);
        sort.setAdapter(ad);


        prepareRecyclerView();          // calling a method to prepare our recycler view.
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedOption = (String) adapterView.getItemAtPosition(i);
        sortAction(selectedOption);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    private void sortAction(String selectedOption) {
        switch (selectedOption) {
            case "Ascending":
                // start action for option 1
                Collections.sort(imagePaths);
                break;
            case "Descending":
                // start action for option 2
                Collections.reverse(imagePaths);
                break;
            default:
                // do nothing
                break;
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE); // in this method we are checking if the permissions are granted or not and returning the result.
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (checkPermission()) {
            Toast.makeText(this, "Permissions granted..", Toast.LENGTH_SHORT).show();               // if the permissions are already granted we are calling a method to get all images from our external storage.
            getImagePath();
        } else {
            requestPermission();                // if the permissions are not granted we are calling a method to request permissions.
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);          //on below line we are requesting the rea external storage permissions.
    }

    private void prepareRecyclerView() {
        imageRVAdapter = new RecyclerViewAdapter(Gallery.this, imagePaths); // in this method we are preparing our recycler view. on below line we are initializing our adapter class.
        GridLayoutManager manager = new GridLayoutManager(Gallery.this, 4);         // on below line we are creating a new grid layout manager.
        imagesRV.setLayoutManager(manager);         // on below line we are setting layout manager and adapter to our recycler view.
        imagesRV.setAdapter(imageRVAdapter);
    }

    private void getImagePath() {
        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);            // in this method we are adding all our image paths in our arraylist which we have created. on below line we are checking if the device is having an sd card or not.

        if (isSDPresent) {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};   // if the sd card is present we are creating a new list in which we are getting our images data with their ids.
            final String orderBy = MediaStore.Images.Media._ID;             // on below line we are creating a new string to order our images by string.
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%Hide_r Pics%"}, orderBy);              // this method will stores all the images from the gallery in Cursor
            int count = cursor.getCount();                      //below line is to get total number of images
            for (int i = 0; i < count; i++) {                           // on below line we are running a loop to add the image file path in our array list.
                cursor.moveToPosition(i);                                               // on below line we are moving our cursor position
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);      // on below line we are getting image file path
                imagePaths.add(cursor.getString(dataColumnIndex));                      // after that we are getting the image file path and adding that path in our array list.
            }
            imageRVAdapter.notifyDataSetChanged();
            cursor.close();                                                 // after adding the data to our array list we are closing our cursor.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);           // this method is called after permissions has been granted.
        switch (requestCode) {
            // we are checking the permission code.
            case PERMISSION_REQUEST_CODE:
                // in this case we are checking if the permissions are accepted or not.
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        // if the permissions are accepted we are displaying a toast message
                        // and calling a method to get image path.
                        Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show();
                        getImagePath();
                    } else {
                        // if permissions are denied we are closing the app and displaying the toast message.
                        Toast.makeText(this, "Permissions denied, Permissions are required to use the app..", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


}