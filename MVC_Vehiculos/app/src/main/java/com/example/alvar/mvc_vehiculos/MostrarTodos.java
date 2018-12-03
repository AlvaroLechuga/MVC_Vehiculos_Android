package com.example.alvar.mvc_vehiculos;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alvar.mvc_vehiculos.DAO.DAOVehiculo;
import com.example.alvar.mvc_vehiculos.recursos.Vehiculo;

import java.util.ArrayList;

public class MostrarTodos extends AppCompatActivity {

    ListView listViewVehiculos;
    ArrayList<Vehiculo> listaVehiculos;
    VehiculoHelper dbHelper;
    Context context = this;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_todos);

        listViewVehiculos = findViewById(R.id.listViewVehiculos);
        dbHelper = new VehiculoHelper(this, "vehiculo", null, 1);
        final DAOVehiculo daoVehiculo = new DAOVehiculo();
        listaVehiculos = new ArrayList<>();
        listaVehiculos = (ArrayList<Vehiculo>) daoVehiculo.MostrarVehiculos(dbHelper);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaVehiculos);

        listViewVehiculos.setAdapter(adapter);

        listViewVehiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Vehiculo vehiculo = new Vehiculo();
                vehiculo.setMatricula(listaVehiculos.get(position).getMatricula());
                vehiculo.setMarca(listaVehiculos.get(position).getMarca());
                vehiculo.setModelo(listaVehiculos.get(position).getModelo());

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View view1 = inflater.inflate(R.layout.cuadrodialogo, null);

                final EditText txtMuestraMatricula = view1.findViewById(R.id.txtMuestraMatricula);
                txtMuestraMatricula.setText(vehiculo.getMatricula());
                txtMuestraMatricula.getEditableText();
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
                            listaVehiculos = (ArrayList<Vehiculo>) daoVehiculo.MostrarVehiculos(dbHelper);
                            adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, listaVehiculos);
                            listViewVehiculos.setAdapter(adapter);
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
                                    listaVehiculos = (ArrayList<Vehiculo>) daoVehiculo.MostrarVehiculos(dbHelper);
                                    adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, listaVehiculos);
                                    listViewVehiculos.setAdapter(adapter);
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
        });
    }
}
