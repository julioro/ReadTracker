package com.example.readtracker.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readtracker.R;
import com.example.readtracker.activity.ListReadingsActivity;
import com.example.readtracker.entidades.Reading;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListReadingsAdapter extends RecyclerView.Adapter<ListReadingsAdapter.ReadingViewHolder> {
    ArrayList<Reading> listReadings;
    Context context;
    private AdapterView.OnItemClickListener mListener;

    public ListReadingsAdapter(ArrayList<Reading> listReadings, Context c) {
        this.listReadings = listReadings;
        this.context = c;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = (AdapterView.OnItemClickListener) listener;
    }


    public static class ReadingViewHolder extends RecyclerView.ViewHolder {

        TextView rv_list_readings_label;
        ToggleButton rv_list_readings_read_status;
        TextView rv_list_readings_title;
        TextView rv_list_readings_other_info;
        Button rv_list_readings_more_details;
        Button rv_list_readings_delete_reading;


        public ReadingViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ubicar todos los items a llenar.
            rv_list_readings_label = itemView.findViewById(R.id.rv_list_readings_label);
            rv_list_readings_read_status = itemView.findViewById(R.id.rv_list_readings_read_status);
            rv_list_readings_title = itemView.findViewById(R.id.rv_list_readings_title);
            rv_list_readings_other_info = itemView.findViewById(R.id.rv_list_readings_other_info);
            rv_list_readings_more_details = itemView.findViewById(R.id.rv_list_readings_more_details);
            rv_list_readings_delete_reading = itemView.findViewById(R.id.rv_list_readings_delete_reading);

        }
    }

    @NonNull
    @Override
    public ReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.recycler_list_readings, parent, false);
        ReadingViewHolder readingViewHolder = new ReadingViewHolder(item);
        return readingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReadingViewHolder holder, int position) {
        Reading readingItem = listReadings.get(position);

        int pages = readingItem.getPages();
        boolean status = readingItem.isStatus();

        String label = readingItem.getLabel();
        String title = readingItem.getTitle();
        String author = readingItem.getAuthor();


        String otherInfo = String.valueOf(pages) + "pg. " + author;
        if (readingItem.getReadDate() != null) {
            String fechaParseada = new SimpleDateFormat("dd/MM/yyyy").format(readingItem.getReadDate());
            otherInfo += ". Red on" + fechaParseada;
        }


        holder.rv_list_readings_label.setText(label);
        holder.rv_list_readings_read_status.setChecked(status);
        holder.rv_list_readings_title.setText(title);
        holder.rv_list_readings_other_info.setText(otherInfo);
    }

    @Override
    public int getItemCount() {
        return listReadings.size();
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
