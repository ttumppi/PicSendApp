package com.example.PicSend;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.PrintWriter;

public class CameraActivation extends Activity {

    private static final int CAMERA_REQUEST = 1888;

    public static String FileName = "TemporaryPicture.JPEG";

    public static String Errors = "";

    public static String PictureDir = "images";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StartCamera();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            try{
                File file = GetFile();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("file", file.getAbsolutePath());

                setResult(MainActivity.RESULT_OK, returnIntent);

            }
            catch(Exception e){
                Errors = e.getMessage();
            }


        }
        finish();
    }
    public void StartCamera(){
        try{
            File file = GetEmptyFile();
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        catch (Exception e){
            Errors = e.getMessage();
        }

    }

    private File GetEmptyFile(){

        try{

            File file = GetFile();
            if (file.isFile()){
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
            return file;
        }

        catch(Exception e){
            Errors = e.getMessage();
        }
        return GetFile();

    }

    private File GetFile(){
        return new File(GetFileDirAsFile(), FileName);
    }

    private File GetFileDirAsFile(){
        return getExternalFilesDir(Environment.DIRECTORY_PICTURES) ;
    }


}
