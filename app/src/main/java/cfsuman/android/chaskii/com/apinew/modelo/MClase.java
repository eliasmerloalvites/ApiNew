package cfsuman.android.chaskii.com.apinew.modelo;

public class MClase {
    private String Id;
    private String Nombre;
    private String Descripcion;

    public MClase(){};

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public MClase(String id, String nombre, String descripcion) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
    }


}
