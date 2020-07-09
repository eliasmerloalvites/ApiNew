package cfsuman.android.chaskii.com.apinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import cfsuman.android.chaskii.com.apinew.ui.home.Inicio;

public class ValidarMensaje extends AppCompatActivity implements View.OnKeyListener {
    Button BtnSiguiente;
    EditText txt1,txt2,txt3,txt4,txt5,txt6;
    TextView textView,textViewMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_mensaje);

        BtnSiguiente = findViewById(R.id.idsiguiente);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txt1.setOnKeyListener(this);
        txt2.setOnKeyListener(this);
        txt3.setOnKeyListener(this);
        txt4.setOnKeyListener(this);
        txt5.setOnKeyListener(this);
        txt6.setOnKeyListener(this);

        BtnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Inicio.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        textView = (TextView)findViewById(R.id.idTiempoSeg);
        textViewMin = (TextView)findViewById(R.id.idTiempoMin);

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView.setText(String.format(Locale.getDefault(), "%d sec.", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                textViewMin.setText("0:");
                segundo();
            }
        }.start();

    }

    private void segundo()
    {
        CountDownTimer countDownTimer1 = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView.setText(String.format(Locale.getDefault(), "%d sec.", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                textView.setText("0");
            }
        }.start();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.txt1:

                if (keyCode == KeyEvent.KEYCODE_DEL) {

                }else if (txt1.length()>0)
                    {
                        txt2.requestFocus();
                }
                break;
            case R.id.txt2:
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txt1.requestFocus();
                }else if (txt2.length()>0)
                {
                    txt3.requestFocus();
                }
                break;
            case R.id.txt3:
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txt2.requestFocus();
                }else if (txt3.length()>0)
                {
                    txt4.requestFocus();
                }
                break;
            case R.id.txt4:
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txt3.requestFocus();
                }else if (txt4.length()>0)
                {
                    txt5.requestFocus();
                }
                break;
            case R.id.txt5:
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txt4.requestFocus();

                }else if (txt5.length()>0)
                {
                    txt6.requestFocus();
                }
                break;
            case R.id.txt6:
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    BtnSiguiente.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.formato_bottonplomo));
                    txt5.requestFocus();
                }else if (txt6.length()>0)
                {
                    BtnSiguiente.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.formato_botton));
                }
                break;
            default:
                break;
        }

        return false;
    }
}


