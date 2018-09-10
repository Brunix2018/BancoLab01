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
        chkAceptoTerminos.setChecked(false);


        //oyentes

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
                                "Es obligatorio Aceptar las Condiciones", Toast.LENGTH_SHORT);

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


                if (edtMail.getText().length()==0) resultado="Ingrese un Mail.";
                if (edtCuit.getText().length()==0) resultado= resultado+"Ingrese un Cuit.";
                if (edtMonto.getText().length()==0)  resultado= resultado+"Ingrese un Monto.";
                else if (Integer.parseInt(edtMonto.getText().toString())==0) resultado= resultado+"Ingrese un Monto mayor a Cero.";
                if (seekDias.getProgress()<10)  resultado= resultado+"Ingrese plazo mayor a 10 Días.";
                if (resultado!= "") {
                    edtMensajes.setTextColor(Color.RED);

                    Toast popUp = Toast.makeText(getApplicationContext(),
                            "No se pudo crear el Plazo Fijo. Verifique los datos ingresados", Toast.LENGTH_SHORT);
                } else
                    {
                        edtMensajes.setTextColor(Color.BLUE);
                        resultado= "El plazo fijo se realizó exitosamente. Plazo fijo: Días ="+ pf.getDias().toString()+", monto="+pf.getMonto().toString()+
                                ", avisarVencimiento="+String.valueOf(swAvisarVencimiento.isChecked())+", renovarAutomáticamente="+ String.valueOf(togAccion.isChecked())+"; moneda="+pf.getMoneda().toString();

                    }
                edtMensajes.setText(resultado);






                    ;
            }
        });







    }
}
