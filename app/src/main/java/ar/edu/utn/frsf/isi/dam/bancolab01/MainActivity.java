package ar.edu.utn.frsf.isi.dam.bancolab01;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import ar.edu.utn.frsf.isi.dam.bancolab01.ar.ued.utn.frsf.isi.dam.bancolab01.modelo.Cliente;
import ar.edu.utn.frsf.isi.dam.bancolab01.ar.ued.utn.frsf.isi.dam.bancolab01.modelo.PlazoFijo;


public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;


    private EditText edtMail;
    private EditText edtCuit;
    private RadioButton optDolar;
    private RadioButton optPesos;
    private EditText edtMonto;
    private SeekBar seekDias;
    private TextView tvDiasSeleccionados;
    private TextView tvIntereses;
    private Switch swAvisarVencimiento;
    private ToggleButton togAccion;
    private CheckBox chkAceptoTerminos;
    private Button btnHacerPF;
    private TextView edtMensajes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pf = new PlazoFijo(getResources().getStringArray(R.array.tasas));
        cliente = new Cliente();

        edtMail= (EditText) findViewById(R.id.edtMail);
        edtCuit= (EditText) findViewById(R.id.edtCuit);
        optDolar= (RadioButton) findViewById(R.id.optDolar);
        optPesos= (RadioButton) findViewById(R.id.optPesos);
        edtMonto= (EditText) findViewById(R.id.edtMonto);
        seekDias= (SeekBar) findViewById(R.id.seekDias);
        tvDiasSeleccionados= (TextView) findViewById(R.id.tvDiasSeleccionados);
        tvIntereses= (TextView) findViewById(R.id.tvIntereses);
        swAvisarVencimiento= (Switch) findViewById(R.id.swAvisarVencimiento);
        togAccion= (ToggleButton) findViewById(R.id.togAccion);
        chkAceptoTerminos= (CheckBox) findViewById(R.id.chkAceptoTerminos);
        btnHacerPF = (Button) findViewById(R.id.btnHacerPF);
        edtMensajes= (TextView) findViewById(R.id.edtMensajes);

        //inicializamos
        optDolar.setChecked(false);
        optPesos.setChecked(true);
        swAvisarVencimiento.setChecked(true);
        togAccion.setChecked(false);
        btnHacerPF.setEnabled(false);
        chkAceptoTerminos.setChecked(true);


        //oyentes

        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progreso, boolean fromUser) {
                tvDiasSeleccionados.setText( progreso + getResources().getString(R.string.lblCantidad));
                Editable montoText = edtMonto.getText();
                pf.setMonto(montoText.toString());
                pf.setDias(progreso);
                Double montoConIntereses= pf.intereses()+ pf.getMonto();
                String moneda;
                if (optPesos.isChecked()) moneda= getResources().getString(R.string.lblMonedaPesos);
                else moneda= getResources().getString(R.string.lblMonedaDolar);
                tvIntereses.setText(moneda + montoConIntereses.toString());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





    }
}
