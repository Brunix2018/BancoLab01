package ar.edu.utn.frsf.isi.dam.bancolab01.ar.ued.utn.frsf.isi.dam.bancolab01.modelo;

public class Cliente {
    private String mail;
    private String cuil;

    @Override
    public String toString() {
        return "Cliente{" +
                "mail='" + mail + '\'' +
                ", cuil='" + cuil + '\'' +
                '}';
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }
}
