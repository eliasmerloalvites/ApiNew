package cfsuman.android.chaskii.com.apinew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import cfsuman.android.chaskii.com.apinew.ui.home.Inicio;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ValidandoNumero extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText edtPhoneNumber;
    Button btnrecibircodigo;
    Boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validando_numero);
        btnrecibircodigo = findViewById(R.id.idrecibircodigo);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
       // ccp.setCountryForNameCode("US");
        edtPhoneNumber = findViewById(R.id.numerotelefono);
        if(ActivityCompat.checkSelfPermission(
                ValidandoNumero.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(
                ValidandoNumero.this,Manifest
                        .permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ValidandoNumero.this,new String[]
                    { Manifest.permission.SEND_SMS,},1000);
        }else{
        };

        edtPhoneNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(11)});
        PhoneNumberUtils.formatNumber(edtPhoneNumber.getText().toString());

        ccp.registerCarrierNumberEditText (edtPhoneNumber);
        //ccp.setCountryPreference("PE,AR,CU,CL"); // aparescan primero como preferidos
        ccp.setCustomMasterCountries("AR,BR,CO,CU,CL,US,PE"); // para que solo aparezcan esos paises de lo contrario poner null y aparecen todos
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Toast.makeText(getApplicationContext(), "Cambio al Pais " + ccp.getSelectedCountryName()+ " y numero"+ccp.getFullNumberWithPlus() , Toast.LENGTH_LONG).show();
            }
        });

        btnrecibircodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences1 = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE); //asignamos un nombre a la preferencia
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("celular",ccp.getFullNumberWithPlus());
                editor.commit();
                try {

                    ValidarNumero();
                    Toast.makeText(getApplicationContext(),ccp.getFullNumberWithPlus(),Toast.LENGTH_LONG).show();

                    enviarMensaje(ccp.getFullNumberWithPlus(),"Hola Soy ELIAS te estoy enviando un Mensaje");



                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        edtPhoneNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                String text = edtPhoneNumber.getText().toString();

                int  textLength = edtPhoneNumber.getText().length();
                if (textLength == 1) {
                    if (!text.contains("9"))
                    {
                        edtPhoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, "(").toString());
                        edtPhoneNumber.setSelection(edtPhoneNumber.getText().length());
                        error = true;
                        edtPhoneNumber.setError( "Ingrese el numero valido :  9XX XXX XXX" );
                    }
                    else error = false;
                }
                else if (textLength==11)
                {
                    if (error)
                    {
                        edtPhoneNumber.setError( "Ingrese el numero valido :  9XX XXX XXX" );
                    }
                    else
                    {
                        //btnrecibircodigo.setEnabled(false);
                        btnrecibircodigo.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.formato_botton));
                    }
                }
                else btnrecibircodigo.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.formato_bottonplomo));
            }
        });

    }

    private void ValidarNumero()  throws IOException {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        System.out.println(preferences.getString("celular",""));
        RequestBody formBody = new FormBody.Builder() //mandamos los parametros para validar el login
                .add("celular",preferences.getString("celular","") )
                .add("id",preferences.getString("id","") )
                .build();
        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/registerNumero")  //url
                .post(formBody)         //parametros
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String myresponse = response.body().string();       //asignamos la respuesta en esa variable
                System.out.println(myresponse);                     //mostramos el resultado para visualizarlo
                try {
                    JSONObject json = new JSONObject(myresponse);
                    //asignamos en una variable json  el resultado
                    if (json.getBoolean("error")) {
                        System.out.println(json.getString("info"));
                        //si la respuesta da error, muestra un mensaje del resultado
                        Toast.makeText(getApplicationContext(), json.getString("hubo un error al registrar"), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ValidarMensaje.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) { //no hubo conexion con respecto al url solicitado
                e.printStackTrace();
                Log.i ("ERROR", e.getMessage()); //mostramos el mensaje del error de la conexion
            }
        });
    }
    private void enviarMensaje (String numero, String mensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero,null,mensaje,null,null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrecto", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
