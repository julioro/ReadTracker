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

import java.util.ArrayList;

public class ListReadingsAdapter extends RecyclerView.Adapter<ListReadingsAdapter.ReadingViewHolder> {
    ArrayList<Reading> listReadings;
    Context context;

    public ListReadingsAdapter(ArrayList<Reading> listReadings, Context c) {
        this.listReadings = listReadings;
        this.context = c;
    }

    public static class ReadingViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ReadingViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ubicar todos los items a llenar.
            //textView = itemView.findViewById(R.id.textViewLectura);
        }
    }

    @NonNull
    @Override
    public ReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.lista_rviewer, parent, false);
        ReadingViewHolder readingViewHolder = new ReadingViewHolder(item);
        return readingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReadingViewHolder holder, int position) {
        Reading readingItem = listReadings.get(position);
        
        int pages = readingItem.getPages();
        String title = readingItem.getTitle();
        boolean status = readingItem.getReadDate() != null;
        //holder.textView.setText(texto);
    }

    @Override
    public int getItemCount() {
        return listReadings.size();
    }
}
