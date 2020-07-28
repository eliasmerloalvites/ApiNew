package cfsuman.android.chaskii.com.apinew.modelo;

public class MDetalleImagen {
    private String DIS_Id;
    private String SER_Id;
    private String DIS_Nombre;

    public String getDIS_Id() {
        return DIS_Id;
    }

    public void setDIS_Id(String DIS_Id) {
        this.DIS_Id = DIS_Id;
    }

    public String getSER_Id() {
        return SER_Id;
    }

    public void setSER_Id(String SER_Id) {
        this.SER_Id = SER_Id;
    }

    public String getDIS_Nombre() {
        return DIS_Nombre;
    }

    public void setDIS_Nombre(String DIS_Nombre) {
        this.DIS_Nombre = DIS_Nombre;
    }

    public MDetalleImagen() {
    }

    public MDetalleImagen(String DIS_Id, String SER_Id, String DIS_Nombre) {
        this.DIS_Id = DIS_Id;
        this.SER_Id = SER_Id;
        this.DIS_Nombre = DIS_Nombre;
    }
}
