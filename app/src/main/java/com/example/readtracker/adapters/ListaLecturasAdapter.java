package com.example.readtracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readtracker.R;
import com.example.readtracker.entidades.Reading;

public class ListaLecturasAdapter extends RecyclerView.Adapter<ListaLecturasAdapter.LecturaViewHolder> {
    Reading[] readings;
    Context contexto;

    public ListaLecturasAdapter(Reading[] lista, Context c) {
        this.readings = lista;
        this.contexto = c;
    }

    public static class LecturaViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public LecturaViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewLectura);
        }
    }

    @NonNull
    @Override
    public LecturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(contexto).inflate(R.layout.lista_rviewer, parent, false);
        LecturaViewHolder lecturaViewHolder = new LecturaViewHolder(item);
        return lecturaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LecturaViewHolder holder, int position) {
        Reading l = readings[position];
        String texto = l.getTitle();
        holder.textView.setText(texto);
    }

    @Override
    public int getItemCount() {
        return readings.length;
    }
}
