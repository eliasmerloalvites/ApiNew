package cfsuman.android.chaskii.com.apinew.modelo;

public class MFamilia {
    private String Id;
    private String Nombre;
    private String Descripcion;
    private String Imagen;
    private int Tipo; //1 = Servicio, 2 = Frelancer , 3 = Alquiler

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public  MFamilia(){};

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

    public MFamilia(String id, String nombre, String descripcion, String imagen, Integer tipo) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Imagen = imagen;
        Tipo = tipo;
    }


}
