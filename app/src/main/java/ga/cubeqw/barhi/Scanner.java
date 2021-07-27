package ga.cubeqw.barhi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.Result;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scanner extends AppCompatActivity {
    SharedPreferences sPref;
    private CodeScanner mCodeScanner;
    String json;
    Gson gson;
    List<Barcode> list = new ArrayList<>();
    Barcode barcodes [];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        json = load("history");
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        if(!json.equals("")){
        barcodes=gson.fromJson(json, Barcode[].class);
        list = Stream.of(barcodes).collect(Collectors.toCollection(ArrayList::new));
        }
        if (ContextCompat.checkSelfPermission(Scanner.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(Scanner.this, new String[]{Manifest.permission.CAMERA}, 123);
        } else {
            startScanning();
        }
    }
    private void startScanning() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date date = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy, hh:mm");
                        Barcode link = new Barcode(result.toString(), format.format(date));
                        list.add(link);
                        json = gson.toJson(list);
                        save("history", json);
                        Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                startScanning();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if (mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    void delete() {
        sPref = getSharedPreferences("sPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.clear();
        ed.commit();
    }

    void save(String key, String value) {
        sPref = getSharedPreferences("sPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(key, value);
        ed.commit();

    }

    String load(String s) {
        sPref = getSharedPreferences("sPref", MODE_PRIVATE);
        return sPref.getString(s, "");
    }
}