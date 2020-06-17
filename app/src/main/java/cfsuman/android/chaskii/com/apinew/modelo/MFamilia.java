package cfsuman.android.chaskii.com.apinew.modelo;

public class MFamilia {
    private String Id;
    private String Nombre;

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

    public MFamilia(String id, String nombre) {
        Id = id;
        Nombre = nombre;
    }


}
