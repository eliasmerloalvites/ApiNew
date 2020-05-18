package cfsuman.android.chaskii.com.apinew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cfsuman.android.chaskii.com.apinew.ui.home.Home;

public class Validacion extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {

    //TODO DECLARAMOS LAS VARIABLES
    private GoogleApiClient googleApiClient; //para el inicio de session con google
    private FirebaseAuth firebaseAuth; //para el inicio de session con google
    private FirebaseAuth.AuthStateListener firebaseAuthListener; //para el inicio de session con google

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion);

        //obtenemos el modo como se IS
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        String modo = preferences.getString("inicio","");

        if (modo.equals("apinew"))
        {
            regresaLoginApinew();
            goMainScreen();

        }
        else if (modo.equals("facebook"))
        {
            regresaLoginFacebook();
            goMainScreen();
        }
        else if (modo.equals("google"))
        {
            /* TODO Configure el inicio de sesión para solicitar el ID del usuario, la dirección de correo electrónico y el
         perfil básico . El ID y el perfil básico se incluyen en DEFAULT_SIGN_IN*/
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            //Asignamos el IS con la api a googleApliClient
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            regresaLoginGoogle();
            goMainScreen();
        }

    }
    private void  goMainScreen() {
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        String modo = preferences.getString("inicio","");

        if (modo == "google")
        {
            if (firebaseAuthListener != null) {
                firebaseAuth.removeAuthStateListener(firebaseAuthListener); //CUANDO TERMINE DE ESCUCHAR
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        String modo = preferences.getString("inicio","");
        if (modo == "google") {
            firebaseAuth.addAuthStateListener(firebaseAuthListener); //PARA QUE EMPIEZE A ESCUCHAR
        }
    }
    private  void regresarLogin()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private  void regresaLoginApinew()
    {
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        boolean session = preferences.getBoolean("session",false);
        if(session)
        {
            Toast.makeText(this ,"BIENVENIDO INICIO DE SESSION CON APINEW",Toast.LENGTH_LONG).show();
        }
        else {
            regresarLogin();
        }
    }
    private  void regresaLoginFacebook()
    {

        if (AccessToken.getCurrentAccessToken() == null) {
            LoginManager.getInstance().logOut();
            regresarLogin();
        }
        else
        {
            Toast.makeText(this ,"BIENVENIDO INICIO DE SESSION CON FACEBOOK",Toast.LENGTH_LONG).show();
        }
    }

    private void regresaLoginGoogle() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() { //SE ENCARGARA DE VER SI ESTAMOS AUTENTICADOS O NO- SE EJECUTA CUANDO CAMBIA EL ES ESTADO DE AUTENTICACION
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //obtenemos al user acrtual con este metodo
                if (user != null) {  //si el resultado no es null osea si obtuvimos el IS    entra

                    Toast.makeText(getApplicationContext() ,"BIENVENIDO INICIO DE SESSION CON GOOGLE",Toast.LENGTH_LONG).show();
                }
                else {
                    regresarLogin();
                }
            }
        };
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
