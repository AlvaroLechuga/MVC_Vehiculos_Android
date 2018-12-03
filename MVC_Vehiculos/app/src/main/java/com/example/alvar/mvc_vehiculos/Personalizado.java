package com.example.alvar.mvc_vehiculos;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alvar.mvc_vehiculos.DAO.DAOVehiculo;
import com.example.alvar.mvc_vehiculos.recursos.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class Personalizado extends AppCompatActivity {

    List<String> matriculas;
    ArrayList<Vehiculo> vehiculos;
    Button btnBusquedaTexto;
    Button btnBusquedaLista;
    Button btnMostrarPersonalizado;
    Spinner txtBusquedaSpinner;
    EditText txtBusquedaCuadro;
    VehiculoHelper dbHelper;
    Context context = this;
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizado);

        vehiculos = new ArrayList<>();

        btnBusquedaLista = findViewById(R.id.btnBusquedaLista);
        btnBusquedaTexto = findViewById(R.id.btnBusquedaTexto);
        btnMostrarPersonalizado = findViewById(R.id.btnMostrarPersonalizado);

        txtBusquedaSpinner = findViewById(R.id.txtBusquedaSpinner);
        txtBusquedaCuadro = findViewById(R.id.txtBusquedaCuadro);
        dbHelper = new VehiculoHelper(this, "vehiculo", null, 1);
        matriculas = new ArrayList<String>();

        final DAOVehiculo daoVehiculo = new DAOVehiculo();
        matriculas = daoVehiculo.ObtenerMatriculas(dbHelper);

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, matriculas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtBusquedaSpinner.setAdapter(dataAdapter);


        btnBusquedaTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBusquedaCuadro.setVisibility(View.VISIBLE);
                if(txtBusquedaCuadro.getVisibility() == View.VISIBLE){
                    txtBusquedaSpinner.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnBusquedaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBusquedaSpinner.setVisibility(View.VISIBLE);
                if(txtBusquedaCuadro.getVisibility() == View.VISIBLE){
                    txtBusquedaCuadro.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnMostrarPersonalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correr = false;
                if (txtBusquedaCuadro.getVisibility() == View.VISIBLE) {

                    if (txtBusquedaCuadro.equals("")) {
                        Toast.makeText(getApplicationContext(), "No has escrito una matricula", Toast.LENGTH_LONG).show();
                        correr = false;
                    } else {
                        vehiculos = (ArrayList<Vehiculo>) daoVehiculo.ObtenerVehiculoPersonalizado(txtBusquedaCuadro.getText().toString(), dbHelper);
                        correr = true;
                    }

                } else if (txtBusquedaSpinner.getVisibility() == View.VISIBLE) {
                    if (txtBusquedaSpinner.getSelectedItemPosition() == 0) {
                        Toast.makeText(getApplicationContext(), "No has seleccionado ninguna matricula", Toast.LENGTH_LONG).show();
                        correr = false;
                    } else {
                        vehiculos = (ArrayList<Vehiculo>) daoVehiculo.ObtenerVehiculoPersonalizado(txtBusquedaSpinner.getSelectedItem().toString(), dbHelper);
                        correr = true;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No has seleccionado ninguna opcion", Toast.LENGTH_LONG).show();
                }

                if (correr) {
                    final Vehiculo vehiculo = new Vehiculo();
                    vehiculo.setMatricula(vehiculos.get(0).getMatricula());
                    vehiculo.setMarca(vehiculos.get(0).getMarca());
                    vehiculo.setModelo(vehiculos.get(0).getModelo());

                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                    View view1 = inflater.inflate(R.layout.cuadrodialogo, null);

                    final EditText txtMuestraMatricula = view1.findViewById(R.id.txtMuestraMatricula);
                    txtMuestraMatricula.setText(vehiculo.getMatricula());
                    txtMuestraMatricula.setEnabled(false);
                    final EditText txtMuestraMarca = view1.findViewById(R.id.txtMuestraMarca);
                    txtMuestraMarca.setText(vehiculo.getMarca());
                    final EditText txtMuestraModelo = view1.findViewById(R.id.txtMuestraModelo);
                    txtMuestraMatricula.setText(vehiculo.getMatricula());
                    txtMuestraModelo.setText(vehiculo.getModelo());

                    Button btnCerrarDialogo = view1.findViewById(R.id.btnCerrarDialogo);
                    Button btnModificarVehiculo = view1.findViewById(R.id.btnModificarVehiculo);
                    Button btnEliminarVehiculo = view1.findViewById(R.id.btnEliminarVehiculo);

                    final AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setView(view1)
                            .create();
                    alertDialog.show();

                    btnCerrarDialogo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });

                    btnEliminarVehiculo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(daoVehiculo.EliminarVehiculo(vehiculo, dbHelper) == 1){
                                Toast.makeText(context, "Se ha eliminado el vehiculo", Toast.LENGTH_SHORT).show();
                                txtMuestraMarca.setText("");
                                txtMuestraMatricula.setText("");
                                txtMuestraModelo.setText("");
                                vehiculos = (ArrayList<Vehiculo>) daoVehiculo.MostrarVehiculos(dbHelper);
                                matriculas.remove(txtBusquedaSpinner.getSelectedItemPosition());
                                dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, matriculas);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                txtBusquedaSpinner.setAdapter(dataAdapter);
                            }else{
                                Toast.makeText(context, "Ha ocurrido un error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    btnModificarVehiculo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String oldMatricula = vehiculo.getMatricula();
                            Vehiculo modificarVehiculo = new Vehiculo();
                            modificarVehiculo.setMatricula(txtMuestraMatricula.getText().toString());
                            modificarVehiculo.setMarca(txtMuestraMarca.getText().toString());
                            modificarVehiculo.setModelo(txtMuestraModelo.getText().toString());

                            if(oldMatricula.equals(modificarVehiculo.getMatricula())){
                                if(txtMuestraMarca.getText().equals("") || txtMuestraModelo.getText().toString().equals("")){
                                    Toast.makeText(context, "Algun campo esta vacio, no se puede moficicar el vehiculo", Toast.LENGTH_LONG).show();
                                }else{
                                    if(daoVehiculo.ModificarVehiculo(modificarVehiculo, dbHelper) == 1){
                                        Toast.makeText(context, "Se ha modificado el vehiculo", Toast.LENGTH_LONG).show();
                                        vehiculos = (ArrayList<Vehiculo>) daoVehiculo.MostrarVehiculos(dbHelper);

                                    }else{
                                        Toast.makeText(context, "Error al modificar el vehiculo", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }else{
                                Toast.makeText(context, "Has modificado la matricula, no se puede realizar la modificacion", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

            }
        });
    }
}
