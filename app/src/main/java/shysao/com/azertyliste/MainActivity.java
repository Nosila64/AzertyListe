package shysao.com.azertyliste;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import shysao.com.azertyliste.WebService.HttpClientUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSign,btnWatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btnSign = findViewById(R.id.btnSign);
        btnWatch = findViewById(R.id.btnWatch);

        btnSign.setOnClickListener(this);
        btnWatch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSign) {
            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
        }
        else if(v == btnWatch){
            Intent intent = new Intent(this, WatchTestActivity.class);
            startActivity(intent);
        }

    }


}
