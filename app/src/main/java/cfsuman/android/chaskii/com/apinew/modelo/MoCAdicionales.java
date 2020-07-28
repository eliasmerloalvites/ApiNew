package cfsuman.android.chaskii.com.apinew.modelo;

public class MoCAdicionales {
    private String Id;
    private String Nombre;
    private String Descripcion;
    private String Imagen;
    private String Precio;
    private String IdCategoria;
    private Boolean Favorito;

    public Boolean getFavorito() {
        return Favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.Favorito = favorito;
    }

    public MoCAdicionales(){};

    public String getIdCategoria() { return IdCategoria; }

    public void setIdCategoria(String idCategoria) {IdCategoria = idCategoria; }

    public String getPrecio() { return Precio; }

    public void setPrecio(String precio) { Precio = precio; }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }



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

    public MoCAdicionales(String id, String nombre, String descripcion, String imagen, String precio, String idCategoria, Boolean favorito) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Imagen = imagen;
        Precio = precio;
        IdCategoria = idCategoria;
        Favorito = favorito;
    }


}
