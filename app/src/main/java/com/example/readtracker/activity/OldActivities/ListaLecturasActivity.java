package com.example.readtracker.activity.OldActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.adapters.ListaLecturasAdapter;
import com.example.readtracker.entidades.Dto.DtoReading;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.webrequest.FireReading;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.util.Log;

public class ListaLecturasActivity extends AppCompatActivity {
    Gson gson;
    Reading[] readings;
    final String usuario = "juliorod";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar

         }


    private Reading[] getReadings() {
        gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        String response = "{'estado':200, 'msg':'OK','readings':[{'id':1, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':2, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':3, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':4, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':5, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'}]}";

        Log.d("msgxd",response);
        DtoReading dtoReading = gson.fromJson(response, DtoReading.class);
        readings = dtoReading.getReadings();
         return readings;
    }
}
