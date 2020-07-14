package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.adapters.ListaLecturasAdapter;
import com.example.readtracker.entidades.Dto.DtoReading;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.webrequest.FireLectura;
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
        setContentView(R.layout.activity_lista_lecturas);
        getSupportActionBar().hide(); // hide the title bar

        showLista(usuario);
    }


    private Reading[] getReadings() {
        gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        String response = "{'estado':200, 'msg':'OK','readings':[{'id':1, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':2, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':3, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':4, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':5, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'}]}";

        Log.d("msgxd",response);
        DtoReading dtoReading = gson.fromJson(response, DtoReading.class);
        readings = dtoReading.getReadings();
         return readings;
    }

    private void showLista(String usuario) {
        //readings = getReadings();
        (new FireLectura()).listarLecturas(usuario, new CallbackInterface() {
            @Override
            public void onSuccess(Object result) {
                Log.d("msgxd", "1");
                readings = (Reading[]) result;
                ListaLecturasAdapter listaLecturasAdapter = new ListaLecturasAdapter(readings, ListaLecturasActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recyclerViewListaLecturas);
                recyclerView.setAdapter(listaLecturasAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListaLecturasActivity.this));

            }
        });

    }
}
