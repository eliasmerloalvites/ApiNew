package cfsuman.android.chaskii.com.apinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import cfsuman.android.chaskii.com.apinew.ui.home.Inicio;

public class Presentacion extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);

                boolean session = preferences.getBoolean("session",false);
                if (AccessToken.getCurrentAccessToken() != null) {
                    Intent intent = new Intent(getApplicationContext(), Inicio.class);
                    startActivity(intent);
                    finish();
                }
                else if(session)
                {
                    Intent intent = new Intent(getApplicationContext(), Inicio.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), terminos.class);
                    startActivity(intent);
                    finish();
                }


            }
        },3000);
    }
}
