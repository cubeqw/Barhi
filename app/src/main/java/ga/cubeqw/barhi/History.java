package ga.cubeqw.barhi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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
    Barcode[] barcodes;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("История");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        json = load("history");
        Log.d("qqq", json);
        ExtendedFloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(History.this, R.style.SheetDialog);
                View sheetView = History.this.getLayoutInflater().inflate(R.layout.dialog, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
                Button yes = sheetView.findViewById(R.id.yes);
                Button no = sheetView.findViewById(R.id.no);
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
                        finish();
                    }
                });
                return false;
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0)
                {
                    fab.hide();
                }
                if(dy<0){
                    fab.show();
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (!json.equals("")) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
            barcodes = gson.fromJson(json, Barcode[].class);
            list = Stream.of(barcodes).collect(Collectors.toCollection(ArrayList::new));
        }
        adapter = new HistoryAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    String load(String s) {
        sPref = getSharedPreferences("sPref", MODE_PRIVATE);
        return sPref.getString(s, "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void f() {
        this.finish();
    }
}