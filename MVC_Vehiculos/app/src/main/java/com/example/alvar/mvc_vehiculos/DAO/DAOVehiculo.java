package com.example.alvar.mvc_vehiculos.DAO;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alvar.mvc_vehiculos.VehiculoHelper;
import com.example.alvar.mvc_vehiculos.recursos.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class DAOVehiculo {

    ArrayList<Vehiculo> vehiculos;
    ArrayList<String> listaMatriculas;

    public DAOVehiculo() {
        vehiculos = new ArrayList<>();
    }

    public int InsertarVehiculo(Vehiculo vehiculo, VehiculoHelper dbHelper) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String matricula = vehiculo.getMatricula();
        String marca = vehiculo.getMarca();
        String modelo = vehiculo.getModelo();

        try{
            String consulta ="INSERT INTO `vehiculo` (`Matricula`, `Marca`, `Modelo`) VALUES ('" + matricula + "', '" + marca + "', '" + modelo + "')";
            db.execSQL(consulta);
            return 1;
        }catch (SQLException sqle){
            return 0;
        }catch (Exception e){
            return 0;
        }
    }

    public List<Vehiculo> MostrarVehiculos(VehiculoHelper dbHelper){
        vehiculos = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Vehiculo vehiculo = null;

        String consulta = "SELECT * FROM vehiculo";
        Cursor cursor = db.rawQuery(consulta, null);

        while (cursor.moveToNext()){
            vehiculo = new Vehiculo();
            vehiculo.setMatricula(cursor.getString(0));
            vehiculo.setMarca(cursor.getString(1));
            vehiculo.setModelo(cursor.getString(2));
            vehiculos.add(vehiculo);
        }

        return vehiculos;
    }

    public int EliminarVehiculo(Vehiculo vehiculo, VehiculoHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try{
            db.delete("vehiculo", "Matricula="+vehiculo.getMatricula(), null);
            return 1;
        }catch (SQLException sqle){
            return 0;
        }catch (Exception e){
            return 0;
        }

    }

    public int ModificarVehiculo(Vehiculo vehiculo, VehiculoHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String marca = vehiculo.getMarca();
        String modelo = vehiculo.getModelo();
        String matricula = vehiculo.getMatricula();
        try {
            String consulta = "UPDATE vehiculo SET Marca = '"+marca+"', Modelo = '"+modelo+"' WHERE Matricula = '"+matricula+"'";
            db.execSQL(consulta);
            return 1;
        }catch (SQLException sqle){
            return 0;
        }catch (Exception e){
            return 0;
        }
    }

    public List<String> ObtenerMatriculas(VehiculoHelper dbHelper){

        listaMatriculas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String consulta = "SELECT Matricula FROM vehiculo";
        Cursor cursor = db.rawQuery(consulta, null);

        listaMatriculas.add("------");

        while (cursor.moveToNext()){
            listaMatriculas.add(cursor.getString(0));
        }

        return  listaMatriculas;
    }

    public List<Vehiculo> ObtenerVehiculoPersonalizado(String matricula, VehiculoHelper dbHelper){

        vehiculos = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Vehiculo vehiculo = null;

        String consulta = "SELECT * FROM vehiculo WHERE Matricula = '"+matricula+"'";
        Cursor cursor = db.rawQuery(consulta, null);

        while (cursor.moveToNext()){
            vehiculo = new Vehiculo();
            vehiculo.setMatricula(cursor.getString(0));
            vehiculo.setMarca(cursor.getString(1));
            vehiculo.setModelo(cursor.getString(2));
            vehiculos.add(vehiculo);
        }

        return vehiculos;
    }

}
