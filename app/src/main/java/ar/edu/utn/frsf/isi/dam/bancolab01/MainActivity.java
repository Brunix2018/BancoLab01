package ar.edu.utn.frsf.isi.dam.bancolab01;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

import ar.edu.utn.frsf.isi.dam.bancolab01.ar.ued.utn.frsf.isi.dam.bancolab01.modelo.Cliente;
import ar.edu.utn.frsf.isi.dam.bancolab01.ar.ued.utn.frsf.isi.dam.bancolab01.modelo.PlazoFijo;


public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;


    private EditText edtMail;
    private EditText edtCuit;
    private RadioGroup optMoneda;
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
        optMoneda = (RadioGroup) findViewById(R.id.optMoneda);
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
        chkAceptoTerminos.setChecked(false);


        //oyentes


        optMoneda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (optPesos.isChecked()) pf.setMonedaPesos();
                else pf.setMonedaDolar();
            }
        });

        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progreso, boolean fromUser) {
                tvDiasSeleccionados.setText( progreso + getResources().getString(R.string.lblCantidad));
                Editable montoText = edtMonto.getText();
                if (edtMonto.getText().length() != 0) pf.setMonto(montoText.toString());
                pf.setDias(progreso);
                Double montoConIntereses= pf.intereses()+ pf.getMonto();
                String moneda;
                if (optPesos.isChecked()) moneda= getResources().getString(R.string.lblMonedaPesos);
                else moneda= getResources().getString(R.string.lblMonedaDolar);
                tvIntereses.setText(moneda + montoConIntereses.toString());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  }
        }

        );

        chkAceptoTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                       CheckBox botoncheck = (CheckBox) compoundButton;
                Toast popUp = Toast.makeText(getApplicationContext(),
                                R.string.condiciones, Toast.LENGTH_SHORT);

                       if (botoncheck.isChecked()) btnHacerPF.setEnabled(true);
                       else {
                           btnHacerPF.setEnabled(false);
                           popUp.show();
                            }
                       }
                     }

        );

        btnHacerPF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultado="";


                if (edtMail.getText().length()==0) resultado=getString(R.string.ingreseMail);
                if (edtCuit.getText().length()==0) resultado= resultado+getString(R.string.IngreseCuit);
                if (edtMonto.getText().length()==0)  resultado= resultado+getString(R.string.ingreseMonto);
                else if (Integer.parseInt(edtMonto.getText().toString())==0) resultado= resultado+getString(R.string.ingreseMontoNoCero);
                if (seekDias.getProgress()<10)  resultado= resultado+getString(R.string.ingresePlazo);
                if (resultado!= "") {
                    edtMensajes.setTextColor(Color.RED);

                    Toast popUp = Toast.makeText(getApplicationContext(),
                            R.string.errorCrearPlazo, Toast.LENGTH_SHORT);
                } else
                    {
                        edtMensajes.setTextColor(Color.BLUE);
                        resultado= getString(R.string.plzoExito)+ pf.getDias().toString()+getString(R.string.monto)+pf.getMonto().toString()+
                                getString(R.string.veniento)+String.valueOf(swAvisarVencimiento.isChecked())+getString(R.string.renoAuto)+ String.valueOf(togAccion.isChecked())+getString(R.string.moneda)+pf.getMoneda().toString();

                    }
                edtMensajes.setText(resultado);






                    ;
            }
        });







    }
}
