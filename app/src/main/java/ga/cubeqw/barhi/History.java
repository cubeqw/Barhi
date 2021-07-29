package ga.cubeqw.barhi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.budiyev.android.codescanner.CodeScanner;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class History extends Activity {
    SharedPreferences sPref;
    String json;
    Gson gson;
    List<Barcode> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    Barcode barcodes [];
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        json = load("history");
        Log.d("qqq", json);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(!json.equals("")){
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
            barcodes=gson.fromJson(json, Barcode[].class);
            list = Stream.of(barcodes).collect(Collectors.toCollection(ArrayList::new));
        }
        adapter = new HistoryAdapter(list);
        recyclerView.setAdapter(adapter);
        Log.d("qqq", adapter.getItemCount()+"");
    }
    String load(String s) {
        sPref = getSharedPreferences("sPref", MODE_PRIVATE);
        return sPref.getString(s, "");
    }
    public void onClickClean(View v){
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(History.this, R.style.SheetDialog);
        View sheetView = History.this.getLayoutInflater().inflate(R.layout.dialog, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        LinearLayout yes=sheetView.findViewById(R.id.yes);
        LinearLayout no=sheetView.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPref.edit().clear().apply();
                finish();}
        });
    }
    public void f(){
        this.finish();
    }
}