package cfsuman.android.chaskii.com.apinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;

public class terminos extends AppCompatActivity {
    Button btnAceptoterminos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);
        btnAceptoterminos = findViewById(R.id.btnAceptoterminos);
        btnAceptoterminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);

                boolean session = preferences.getBoolean("session",false);
                if (AccessToken.getCurrentAccessToken() != null) {
                    Intent intent = new Intent(getApplicationContext(), Validacion.class);
                    startActivity(intent);
                    finish();
                }
                else if(session)
                {
                    Intent intent = new Intent(getApplicationContext(), Validacion.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
