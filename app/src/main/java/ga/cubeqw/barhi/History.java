package ga.cubeqw.barhi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.budiyev.android.codescanner.CodeScanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class History extends AppCompatActivity {
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
}