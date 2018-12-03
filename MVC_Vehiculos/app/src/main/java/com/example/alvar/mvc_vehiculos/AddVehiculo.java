package com.example.alvar.mvc_vehiculos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alvar.mvc_vehiculos.DAO.DAOVehiculo;
import com.example.alvar.mvc_vehiculos.recursos.Vehiculo;

public class AddVehiculo extends AppCompatActivity {

    Button btnAddVehiculo;
    EditText txtMatricula;
    EditText txtMarca;
    EditText txtModelo;
    VehiculoHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehiculo);
        btnAddVehiculo = findViewById(R.id.btnAddVehiculo);

        txtMatricula = findViewById(R.id.txtMatricula);
        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);

        dbHelper = new VehiculoHelper(this, "vehiculo", null, 1);

        btnAddVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricula = txtMatricula.getText().toString();
                String marca = txtMarca.getText().toString();
                String modelo = txtModelo.getText().toString();

                if(matricula.equals("") || marca.equals("") || modelo.equals("")){
                    Toast.makeText(getApplicationContext(), "No has completado algun campo", Toast.LENGTH_SHORT).show();
                }else{
                    Vehiculo vehiculo = new Vehiculo();
                    vehiculo.setMatricula(matricula);
                    vehiculo.setMarca(marca);
                    vehiculo.setModelo(modelo);

                    DAOVehiculo daoVehiculo = new DAOVehiculo();
                    if(daoVehiculo.InsertarVehiculo(vehiculo, dbHelper) == 1){
                        Toast.makeText(getApplicationContext(), "Se ha insertado el vehiculo", Toast.LENGTH_SHORT).show();
                        txtMatricula.setText("");
                        txtMarca.setText("");
                        txtModelo.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

    }
}
