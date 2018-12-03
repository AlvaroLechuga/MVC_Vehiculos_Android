package com.example.alvar.mvc_vehiculos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MostrarVehiculos extends AppCompatActivity {

    Button btnMostrarTodos;
    Button btnMostrarPersonalizada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_vehiculos);
        btnMostrarPersonalizada = findViewById(R.id.btnMostrarPersonalizado);
        btnMostrarTodos = findViewById(R.id.btnMostrarTodos);

        btnMostrarTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MostrarTodos.class);
                startActivity(intent);
            }
        });

        btnMostrarPersonalizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Personalizado.class);
                startActivity(intent);
            }
        });
    }
}
