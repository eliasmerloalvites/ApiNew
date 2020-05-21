package cfsuman.android.chaskii.com.apinew.modelo;

public class MUsuario {
    private String id;
    private String nombre;
    private String email;
    private String token;

    public MUsuario() {
    }

    public MUsuario(String id, String nombre, String email, String token) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
