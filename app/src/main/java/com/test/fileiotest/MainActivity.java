package com.test.fileiotest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STROAGE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSaveAuth();
    }

    private void setSaveAuth() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STROAGE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void saveTest() throws JSONException {
        Log.d(TAG, "saveTest!!!");
        String dirPath = getFilesDir().getAbsolutePath() + "/TempData";
        File file = new File(dirPath);

        // 일치하는 폴더가 없으면 생성
        if (!file.exists()) {
            file.mkdirs();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }

        // txt 파일 생성
        String testStr = "ABCDEFGHIJK...";
        JSONObject obj = new JSONObject();
        obj.put("testStr", testStr);
        File savefile = new File(dirPath + "/test.json");
        try {
            FileOutputStream fos = new FileOutputStream(savefile);
            fos.write(obj.toString().getBytes());
            fos.close();
            Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }
    }


    private void readFilesTest() {
        Log.d(TAG, "readTest!!!");
        try {
            String dirPath = getFilesDir().getAbsolutePath() + "/TempData";

            List<String> fileList = getFlleList();
            for (int i = 0; i < fileList.size(); i++) {
                Log.d(TAG, "readFilesTest: " + fileList.get(i));
                FileReader reader = new FileReader(dirPath + "/" + fileList.get(i));
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Log.d(TAG, "readTest: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            Log.d(TAG, "readFilesTest: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "readFilesTest: " + e.getMessage());
        }
    }

    private void readTest() {
        Log.d(TAG, "readTest!!!");
        try {
            String dirPath = getFilesDir().getAbsolutePath() + "/TempData";
            File saveFile = new File(dirPath);
            FileReader reader = new FileReader(saveFile + "/test.json");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String line;
            //ufferedReader.readLine()은 Stream에서 한줄을 읽어 반환한다.
            while ((line = bufferedReader.readLine()) != null) {
                Log.d(TAG, "readTest: " + line);
//                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTest() {
        Log.d(TAG, "saveTest!!!");
        String dirPath = getFilesDir().getAbsolutePath() + "/TempData";
        // txt 파일 생성
        String testStr = "update data...";
        JSONObject obj = new JSONObject();
        try {
            obj.put("testStr", testStr);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        File savefile = new File(dirPath + "/test1.json");
        try {
            FileOutputStream fos = new FileOutputStream(savefile);
            fos.write(obj.toString().getBytes());
            fos.close();
            Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }

    }


    private void deleteTest() {
        Log.d(TAG, "deleteTest!!!");
        String dirPath = getFilesDir().getAbsolutePath() + "/TempData";
        File file = new File(dirPath, "test.json");
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STROAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 허가되어 있습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "아직 승인받지 않았습니다.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public List<String> getFlleList() {
        String dirPath = getFilesDir().getAbsolutePath() + "/TempData";
        File directory = new File(dirPath);
        File[] files = directory.listFiles();

        List<String> fileNameList = new ArrayList<String>();

        for (int i = 0; i < files.length; i++) {
            fileNameList.add(files[i].getName());
        }

        return fileNameList;
    }
    public void createTest(View view) {
        try {
            saveTest();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void readTest(View view) {
        readTest();
    }

    public void readFileTest(View view) {
        readFilesTest();
    }

    public void updateFileTest(View view) {
        updateTest();
    }

    public void deleteFileTest(View view) {
        deleteTest();
    }
}