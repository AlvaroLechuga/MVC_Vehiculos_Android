package com.example.alvar.mvc_vehiculos;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alvar.mvc_vehiculos.recursos.Vehiculo;

public class MainActivity extends AppCompatActivity {

    Button btnRegistrar;
    Button btnMostrar;

    VehiculoHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new VehiculoHelper(this, "vehiculo", null, 1);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnMostrar = findViewById(R.id.btnMostrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddVehiculo.class);
                startActivity(intent);
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MostrarVehiculos.class);
                startActivity(intent);
            }
        });

    }
}
