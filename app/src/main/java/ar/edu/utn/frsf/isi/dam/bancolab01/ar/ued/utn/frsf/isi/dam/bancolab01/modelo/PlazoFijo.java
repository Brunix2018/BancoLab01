package ar.edu.utn.frsf.isi.dam.bancolab01.ar.ued.utn.frsf.isi.dam.bancolab01.modelo;

import android.text.Editable;
import android.util.Log;

import java.util.Arrays;

public class PlazoFijo {
    private Integer dias;
    private Double monto;
    private Boolean avisarVencimiento;
    private Boolean renovarAutomaticamente;
    private Moneda moneda;
    private String[] tasas;
    private Cliente cliente;

    public Boolean getAvisarVencimiento() {
        return avisarVencimiento;
    }

    public void setAvisarVencimiento(Boolean avisarVencimiento) {
        this.avisarVencimiento = avisarVencimiento;
    }

    public PlazoFijo(String[] tasas){
        Log.d("APP_01", tasas+"");
        Log.d("APP_01", Arrays.toString(tasas));
        this.tasas = tasas;
        this.monto = 0.0;
        this.dias = 0;
        this.avisarVencimiento=false;
        this.renovarAutomaticamente=false;
        this.moneda = Moneda.PESO;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setMonto(String monto) {
        this.monto = Double.parseDouble(monto);
    }

    public Boolean getRenovarAutomaticamente() {
        return renovarAutomaticamente;
    }

    public void setRenovarAutomaticamente(Boolean renovarAutomaticamente) {
        this.renovarAutomaticamente = renovarAutomaticamente;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public String[] getTasas() {
        return tasas;
    }

    public void setTasas(String[] tasas) {
        this.tasas = tasas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "PlazoFijo{" +
                "dias=" + dias +
                ", monto=" + monto +
                ", avisarVencimiento=" + avisarVencimiento +
                ", renovarAutomaticamente=" + renovarAutomaticamente +
                ", moneda=" + moneda +
                ", tasas=" + Arrays.toString(tasas) +
                ", cliente=" + cliente +
                '}';
    }

    private Double calcularTasa(){
        if (monto <=5000) {
            if (dias < 30) return Double.valueOf(tasas[0]);
            else return Double.valueOf(tasas[1]);
        }
        if (monto <=99999) {
            if (dias < 30) return Double.valueOf(tasas[2]);
            else return Double.valueOf(tasas[3]);
        }
        else {
            if (dias < 30) return Double.valueOf(tasas[4]);
            else return Double.valueOf(tasas[5]);
        }
    }


    public Double intereses(){

        return (Math.pow(((calcularTasa()/100)+1), (getDias()/360.0))-1)*monto;
    }



}