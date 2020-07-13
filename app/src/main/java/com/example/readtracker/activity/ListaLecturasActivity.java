package com.example.readtracker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readtracker.CallbackInterface;
import com.example.readtracker.R;
import com.example.readtracker.adapters.ListaLecturasAdapter;
import com.example.readtracker.entidades.Dto.DtoLectura;
import com.example.readtracker.entidades.Lectura;
import com.example.readtracker.webrequest.FireLectura;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ListaLecturasActivity extends AppCompatActivity {
    Gson gson;
    Lectura[] lecturas;
    final String usuario = "juliorod";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lecturas);
        getSupportActionBar().hide(); // hide the title bar

        showLista(usuario);
    }


    private Lectura[] getLecturas() {
        gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        String response = "{'estado':200, 'msg':'OK','lecturas':[{'id':1, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':2, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':3, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':4, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'},{'id':5, 'titulo':'titulo1', 'estado':true, 'url':'www.wiki.com', 'paginas':10, 'etiqueta': 'universidad', 'fechaLeida':'18/09/2020'}]}";

        Log.d("msgxd",response);
        DtoLectura dtoLectura = gson.fromJson(response, DtoLectura.class);
        lecturas = dtoLectura.getLecturas();
         return lecturas;
    }

    private void showLista(String usuario) {
        //lecturas = getLecturas();
        (new FireLectura()).listarLecturas(usuario, new CallbackInterface() {
            @Override
            public void onSuccess(Object result) {
                Log.d("msgxd", "1");
                lecturas = (Lectura[]) result;
                ListaLecturasAdapter listaLecturasAdapter = new ListaLecturasAdapter(lecturas, ListaLecturasActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recyclerViewListaLecturas);
                recyclerView.setAdapter(listaLecturasAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListaLecturasActivity.this));

            }
        });

    }
}
