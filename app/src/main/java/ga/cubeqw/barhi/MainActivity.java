package ga.cubeqw.barhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickScanner(View view) {
        Intent intent=new Intent(MainActivity.this, Scanner.class);
        startActivity(intent);
    }

    public void onClickHistory(View view) {
        Intent intent=new Intent(MainActivity.this, History.class);
        startActivity(intent);
    }
}