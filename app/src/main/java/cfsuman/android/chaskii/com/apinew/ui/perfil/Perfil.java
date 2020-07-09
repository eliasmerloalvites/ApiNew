package cfsuman.android.chaskii.com.apinew.ui.perfil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;

import cfsuman.android.chaskii.com.apinew.MainActivity;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.ui.home.Inicio;

public class Perfil extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navView;
    private String modo;
    private TextView edtNombre,edtEmail;
    private ImageView imgFoto;
    private Button btnCerrarSession; //declarando variable buttom
    private LinearLayout prueb1; //declarando variable LinearLayout
    private FirebaseAuth firebaseAuth; //para el inicio de session con google
    private FirebaseAuth.AuthStateListener firebaseAuthListener; //para el inicio de session con google

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        navView = findViewById(R.id.nav_viewB); //Instanciamos BotonBar del formulario con nuestra Variable navView
        navView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this); //asignamos una funcion a cumplir si se selecciona
        navView.getMenu().getItem(3).setChecked(true);

        edtNombre = findViewById(R.id.txtNombreUsuario);
        edtEmail = findViewById(R.id.txtCorreoUsuario);
        imgFoto = findViewById(R.id.imageView);
        try {
            recuperarPreferenciaUsuario();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        btnCerrarSession= findViewById(R.id.btnCerrarSession); //asignamos a las variables Button su respectivo Button que le pertenece
        prueb1= findViewById(R.id.LinPerfil); //asignamos a las variables LinearLayout su respectivo Button que le pertenece

        btnCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
                modo = preferences.getString("inicio","");

                if (modo.equals("apinew"))
                {
                    Toast.makeText(getApplicationContext(), "APINEW"+ modo, Toast.LENGTH_SHORT).show();
                    cerrarSessionApinew();
                }
                else if (modo.equals("facebook"))
                {
                    Toast.makeText(getApplicationContext(),"FACEBOOK"+ modo, Toast.LENGTH_SHORT).show();
                    cerrarSessionFacebook();
                }
                else if (modo.equals("google"))
                {
                    Toast.makeText(getApplicationContext(), "MODO "+ modo, Toast.LENGTH_SHORT).show();

                    firebaseAuth = FirebaseAuth.getInstance();
                    Toast.makeText(getApplicationContext(), "GOOGLE"+ modo, Toast.LENGTH_SHORT).show();
                    cerrarSessionGoogle();
                }
            }
        });
        prueb1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Fragment fragment;
                FragmentTransaction ft;
                switch (view.getId()){

                    case R.id.LinPerfil :
                        //   Toast.makeText(getContext(),"HOLA MUNDO",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void Compartir(View view)
    {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String aux = "Descarga la app\n";
            //aux = aux + "https://play.google.com/store/apps/de..."+getBaseContext().getPackageName();
            aux = aux + "https://play.google.com/store/apps/details?id=com.fastbuyapp.omar.fastbuy";
            i.putExtra(Intent.EXTRA_TEXT, aux);
            startActivity(i);
        } catch (Exception e) {
        }

        /*Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
        compartir.setType("text/plain");
        String mensaje = "Te recomiendo esta App para encontrar trabajo y obtener descuentos.";
        compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Empleos Baja App");
        compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
        startActivity(Intent.createChooser(compartir, "Compartir vía"));*/
    }

    private  void recuperarPreferenciaUsuario() throws UnsupportedEncodingException {

        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        System.out.println(preferences.getString("foto",""));
        edtNombre.setText(preferences.getString("name","null"));
        edtEmail.setText(preferences.getString("email","null@gmail.com"));

        Picasso.get()
                .load(Uri.parse(preferences.getString("foto","")))
                .resize(150, 150)
                .centerCrop()
                .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                .into(imgFoto);
       /* dni : 33577960
                fe :12-07-2012
                        fn : 20-07-1940*/
    }

    public void cerrarSessionGoogle() {
        firebaseAuth.signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();

        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("session",false);
        editor.commit();

        regresarLogin();
    }


    /*public void revoke(View view) {
         firebaseAuth.signOut()
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_revoke, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    private void regresarLogin() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void cerrarSessionFacebook() {
        LoginManager.getInstance().logOut();
        regresarLogin();
    }
    public void cerrarSessionApinew(){
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("session",false);
        editor.commit();
        regresarLogin();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigation_Inicio) {
            Intent intent = new Intent(this, Inicio.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if (id == R.id.navigation_Mensaje) {

        }

        else if (id == R.id.navigation_notificaciones) {


        }
        else if (id == R.id.navigation_perfil) {
            Intent intent = new Intent(this, Perfil.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return true;
    }

}
