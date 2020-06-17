package cfsuman.android.chaskii.com.apinew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cfsuman.android.chaskii.com.apinew.SQLite.SQLusuario;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
//TODO declaraciones de las variables

    SQLusuario sqlUsuario;//Sql del usuario
    String usu;  //parametros del usuario  Cadena
    String contr;    //parametros del usuario  Cadena
    private EditText edtUsuario,edtContraseña; //parametros del usuario TextView
    private CallbackManager callbackManager; //para el inicio de session con facebook , devoluciones de llamada o retrollamada
    private LoginButton loginButton ;       //para el inicio de session con facebook
    private SignInButton signInButton;          //para el inicio de session con google
    private GoogleApiClient googleApiClient;    //para el inicio de session con google
    private Button Button,Buttonface,ButtonGoo ;          //botones para el inicio de session con AppNew,facebook y google


    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    public static final int SIGN_IN_CODE = 777;  //NUMERO DE ACTIVIDAD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Instanciamos las variables.
        sqlUsuario = new SQLusuario(this);  //asigno a mi variable objeto la clase SQLUsuario, que nos permetira usar el almacenamiento del dispositivo movil como BD

        callbackManager = CallbackManager.Factory.create(); //inicializo tu instancia
        loginButton = (LoginButton) findViewById(R.id.login_button); //Instanciamos login_button del formulario con nuestra Variable loginButton
        signInButton = (SignInButton) findViewById(R.id.signInButton); //Instanciamos signInButton del formulario con nuestra Variable signInButton
        Button = (Button) findViewById(R.id.button);    //Instanciamos Button del formulario con nuestra Variable Button
        Buttonface = (Button) findViewById(R.id.button2); //Instanciamos Buttonface del formulario con nuestra Variable button2
        ButtonGoo = (Button) findViewById(R.id.buttongoogle); //Instanciamos ButtonGoo del formulario con nuestra Variable buttongoogle

        edtUsuario = findViewById(R.id.idusuario);  //Instanciamos edtUsuario del formulario con nuestra Variable idusuario
        edtContraseña = findViewById(R.id.idcontraseña);//Instanciamos edtContraseña del formulario con nuestra Variable idcontraseña
        loginButton.setReadPermissions("email");
        recuperarPreferencia(); //recuperamos preferencia para ver si han iniciado session

        //TODO dandole estilo las variables
        signInButton.setSize(SignInButton.SIZE_WIDE); //button de Google Toma un tamaño mayor al de por defecto
        signInButton.setColorScheme(SignInButton.COLOR_DARK); //button de Google le damos un fondo oscuro

        //TODO ASIGNANDO VARIABLES PARA EL INICIO SESSION CON GOOGLE;

        /* Configure el inicio de sesión para solicitar el ID del usuario, la dirección de correo electrónico y el
         perfil básico . El ID y el perfil básico se incluyen en DEFAULT_SIGN_IN*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) //ADEMAS OBTENGAMOS UN TOKEN
                .requestEmail()
                .build();
        //Asignamos el IS con la api a googleApliClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //TODO asignamos funciones a nuestra variable  psdta(IS = Iniciar Session)
        //Este es el button de IS de Api new
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    VerificaUsuario();//llama a ala funcion que verifica si existe el usuario
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        //Este es el button de IS de faceebok camuflando al botton de IS por defecto
        Buttonface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick(); //llama a presionar el setOnclick a ese boton de facebook por defecto
            }
        });
        //Este es el button de IS de faceebok por defecto
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                modificamosModoPreferenciaIS("facebook");
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        // Obtener datos
                        Profile profile = Profile.getCurrentProfile();
                        if (profile != null) {
                            displayProfileInfo(profile);
                        } else {
                            Profile.fetchProfileForCurrentAccessToken();
                        }

                        try {

                                modificamosModoPreferenciaIS("facebook");
                                RegistroUsuario();//llama a ala funcion que Registra el usuario
                                goMainScreen();//pasa al siguiente activity
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();


                   //si se a IS correctamente llama a la funcion goMainScreen, que llama a la siguiente actividad
            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show(); //mensaje a mostrar si cancelamos el ingreso
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show(); //mensaje a mostrar si tenemos un error al ingreso
            }
        });
        //Este es el button de IS de google camuflando al botton de IS por defecto
        ButtonGoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);  //llama a presionar el setOnclick a ese boton de facebook por defecto
            }
        });
        //Este es el button de IS de google por defecto
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() { //SE ENCARGARA DE VER SI ESTAMOS AUTENTICADOS O NO- SE EJECUTA CUANDO CAMBIA EL ES ESTADO DE AUTENTICACION
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //obtenemos al user acrtual con este metodo
                if (user != null) {  //si el resultado no es null osea si obtuvimos el IS    entra
                    modificamosModoPreferenciaIS("google");
                    SharedPreferences preferences1 = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE); //asignamos un nombre a la preferencia
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("provider_id",user.getUid());
                    editor.putString("provider","google");
                    editor.putString("foto", user.getPhotoUrl().toString());
                    editor.putString("email",user.getEmail());
                    editor.putString("name",user.getDisplayName());
                    editor.commit();

                    try {

                            modificamosModoPreferenciaIS("google");
                            RegistroUsuario();//llama a ala funcion que Registra el usuario
                            goMainScreen();//pasa al siguiente activity

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    goMainScreen();
                }
            }
        };


    }


    //TODO para IS de api new guardamos una referencia de los datos ingresado en login
    private void guardarPreferencia(){
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE); //asignamos un nombre a la preferencia
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario",usu);
        editor.putString("contraseña",contr);
        editor.putString("inicio","apinew");
        editor.putBoolean("session",true);
        editor.commit();
    }
    //TODO para IS de api new recuperamos datos de la referencia
    private  void recuperarPreferencia(){
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        edtUsuario.setText(preferences.getString("usuario","elias@gmail.com"));
        edtContraseña.setText(preferences.getString("contraseña","123456"));
    }
    //TODO para IS de api new guardamos una referencia de los datos que recibimos una vez logueado
    private void guardarPreferenciaUsuario(String ptoken,String pprovider,String pfoto,String pemial,String pname,String pid){
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE); //asignamos un nombre a la preferencia
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",ptoken);
        editor.putString("provider",pprovider);
        editor.putString("foto",pfoto);
        editor.putString("email",pemial);
        editor.putString("name",pname);
        editor.putString("id",pid);
        editor.commit();
    }
    private void guardarPreferenciaUsuarioEmail(String pemail){
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE); //asignamos un nombre a la preferencia
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",pemail);
        editor.commit();
    }

    //TODO para IS de api new recuperamos modo de IS de la referencia
    private  void modificamosModoPreferenciaIS(String inicio){
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("inicio",inicio);
        editor.putBoolean("session",true);
        editor.commit();
    }
    //TODO ESTA FUNCION ES PARA LLAMAR AL SIGUIENTE ACTIVITY
    private void  goMainScreen() {
        Intent intent = new Intent(this, Validacion.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    //TODO ESTA FUNCION ES PARA VERIFICAR EL IS DE APINEW
    public void VerificaUsuario()
            throws IOException {

        EditText usernameEditText = findViewById(R.id.idusuario);   //asignamos a las variables editext su respectivo caja de texto que le pertenece
        EditText passwordEditText = findViewById(R.id.idcontraseña);

        usu = usernameEditText.getText().toString();  // asignamos el valor que tiene la caja de texto a esa variable
        contr = passwordEditText.getText().toString();
        RequestBody formBody = new FormBody.Builder() //mandamos los parametros para validar el login
                .add("email", usu)
                .add("password",contr )
                .build();
        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/login")  //url
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
                        System.out.println("error");
                        //si la respuesta da error, muestra un mensaje del resultado
                        Toast.makeText(getApplicationContext(), json.getString("error"), Toast.LENGTH_SHORT).show();
                    } else {                                //si el resultado es perfecto
                        guardarPreferencia();

                        Intent intent = new Intent(getApplicationContext(), Validacion.class);
                        JSONArray array = json.getJSONArray("success");
                        //asignamos al array el array que de respuesta success
                        for(int i = 0; i < array.length(); i++) {
                            JSONObject objectUsuario = array.getJSONObject(i);
                            guardarPreferenciaUsuario(
                                    objectUsuario.getString("token"),
                                    objectUsuario.getString("provider"),
                                    objectUsuario.getString("foto"),
                                    objectUsuario.getString("email"),
                                    objectUsuario.getString("name"),
                                    objectUsuario.getString("id")
                            );
                            //sqlUsuario.agregarUsuario(objectUsuario.getString("id"),objectUsuario.getString("name"), objectUsuario.getString("email"), objectUsuario.getString("token")); //agregamos a la BD el usuario logeado
                        }
                        startActivity(intent);
                        finish();
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

    //TODO obtener datos de facebook
    private void displayProfileInfo(Profile profile) {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE); //asignamos un nombre a la preferencia
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("provider_id",profile.getId());
        editor.putString("provider","facebook");
        editor.putString("name",profile.getName());
        editor.putString("foto",profile.getProfilePictureUri(100, 100).toString());
        editor.commit();
        System.out.println(profile.getProfilePictureUri(100, 100).toString());
    }

    //TODO ESTA FUNCION ES PARA REGISTRAR AL USUARIO Y CLIENTE
    public void RegistroUsuario()
            throws IOException {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        System.out.println(preferences.getString("foto",""));
        RequestBody formBody = new FormBody.Builder() //mandamos los parametros para validar el login
                .add("provider", preferences.getString("provider",""))
                .add("provider_id",preferences.getString("provider_id","") )
                .add("photo_extension",preferences.getString("foto","") )
                .add("name",preferences.getString("name","") )
                .add("email",preferences.getString("email","") )
                .build();
        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/registroRedes")  //url
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
                        JSONArray array = json.getJSONArray("success");

                        for(int i = 0; i < array.length(); i++) {
                            JSONObject objectUsuario = array.getJSONObject(i);
                            guardarPreferenciaUsuario(
                                    objectUsuario.getString("token"),
                                    objectUsuario.getString("provider"),
                                    objectUsuario.getString("foto"),
                                    objectUsuario.getString("email"),
                                    objectUsuario.getString("name"),
                                    objectUsuario.getString("id")
                            );
                        }
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

    // todo PARA IS GOOGLE ONSTAR

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener); //PARA QUE EMPIEZE A ESCUCHAR
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener); //CUANDO TERMINE DE ESCUCHAR
        }
    }

    //TODO - SIS HAY ERROR GOOGLE
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //TODO - SE ACTIVA AL LLAMAR EL IS DE GOOGLE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE) { //SI SE EJECUTO LA ACTIVIDAD DECLARADA ENTRA
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data); //SE EJECUTA EL IS Y EL RESULTADO LO ASIGNAMOS EN LA VARIABLE result
            handleSignInResult(result);  //el resultado mandamos a la funcion handleSignInResult
        }
    }
    //TODO - VERIFICA EL RESULTADO DEL INICIO DE SESSION
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) { //SI EL IS FUE CORRECTO ENTRA
            firebaseAuthWithGoogle(result.getSignInAccount()); //ya no llamamos de frente a goMainScreem si llamamos a un metodo que se encargara de la autenticacion en firebase
        } else {
            Toast.makeText(this, R.string.not_log_in, Toast.LENGTH_SHORT).show(); //EN CASO DE NO A VER IS MANDA UN MENSAJE
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        //...
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null); //en esa credencial le damos el token que obtenemos del objeto cuenta
        firebaseAuth.signInWithCredential(credential) //creamos otro oyente
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //cuanto termine en otra palabras el resultado el stop
                        //...
                        if (!task.isSuccessful()) { //si no fue exitosa muestra ese mensaje
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //Obteneniedo Datos de facebook
    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email")) {
                bundle.putString("email", object.getString("email"));
                guardarPreferenciaUsuarioEmail(object.getString("email"));
            }
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        }
        catch(JSONException e) {
            System.out.println("error"+e);
        }
        return null;
    }
}
