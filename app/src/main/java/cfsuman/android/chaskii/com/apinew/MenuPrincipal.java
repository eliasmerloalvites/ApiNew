package cfsuman.android.chaskii.com.apinew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {

    //TODO DECLARAMOS LAS VARIABLES
    SearchView searchView=null;
    private String provider,provider_id,foto,name,email;
    private BottomNavigationView navView;
    private GoogleApiClient googleApiClient; //para el inicio de session con google
    private FirebaseAuth firebaseAuth; //para el inicio de session con google
    private FirebaseAuth.AuthStateListener firebaseAuthListener; //para el inicio de session con google


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        //obtenemos el modo como se IS
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        String modo = preferences.getString("inicio","");

        if (modo == "apinew")
        {
                regresaLoginApinew();
                goMainScreen();

        }
        else if (modo == "facebook")
        {
            regresaLoginFacebook();
            goMainScreen();
        }
        else if (modo == "google")
        {
            /* TODO Configure el inicio de sesión para solicitar el ID del usuario, la dirección de correo electrónico y el
         perfil básico . El ID y el perfil básico se incluyen en DEFAULT_SIGN_IN*/
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            //Asignamos el IS con la api a googleApliClient
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this,this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            regresaLoginGoogle();
            goMainScreen();
        }

    }
    private void  goMainScreen() {
        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
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

/*    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
 /*    @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;

        if (id == R.id.navigation_Inicio) {
            Intent intent = new Intent(this, Clases.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if (id == R.id.navigation_Mensaje) {

        }
        else if (id == R.id.navigation_busqueda) {


        }
        else if (id == R.id.navigation_notificaciones) {


        }
        else if (id == R.id.navigation_perfil) {
            fragment = new PerfilFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null);
            ft.commit();
            //logout();
            //cerrarSesion();

        }
        return true;
    }*/

    //TODO - SIS HAY ERROR
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
