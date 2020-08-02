package com.example.readtracker.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.readtracker.R;
import com.example.readtracker.entidades.Reading;
import com.example.readtracker.webrequest.FireReading;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProgressChartsActivity extends AppCompatActivity {

    // Instancias de WebRequests.
    FireReading fireReading;
    // Notificaciones.
    String msgToast;
    private FirebaseAuth mAuth;
    ArrayList<Reading> readingsRed;
    String labelFilter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_charts);
        Intent intent = getIntent(); // Get serializable intent data

        labelFilter = (String) intent.getSerializableExtra("labelFilter");
        readingsRed = (ArrayList<Reading>) intent.getSerializableExtra("listReadings");

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));


        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Progreso por etiqueta: " + labelFilter);
        cartesian.yAxis(0).title("# pg. acumuladas");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = prepareData(readingsRed);
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name(labelFilter);
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        anyChartView.setChart(cartesian);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<DataEntry> prepareData(ArrayList<Reading> readingsRed) {

        List<DataEntry> seriesData = new ArrayList<>();
        if (readingsRed.size() == 0) {
            return seriesData;
        }
        int paginasleidas = 0;

        Date firstDate = readingsRed.get(0).getReadDate();
        Date lastDate = readingsRed.get(readingsRed.size() - 1).getReadDate();
        long flagDaysBetween = 0;
        int extra = 2;
        long daysBetween = 5;
        if (firstDate != lastDate) {
            extra = Integer.valueOf((int) (daysBetween / 10));
            flagDaysBetween = lastDate.getTime() - firstDate.getTime();
            daysBetween = TimeUnit.DAYS.convert(flagDaysBetween, TimeUnit.MILLISECONDS);
        }
        Date nextDate = firstDate;
        String dateParse = "";
        int counter = 0;
        int sizeTotal = readingsRed.size();
        Reading reading = readingsRed.get(0);
        for (int i = -extra; i < daysBetween + extra + 5; i++) {
            if (counter < sizeTotal) {
                while (dateParse.equals(reading.parsedDate())) {
                    paginasleidas += reading.getPages();
                    counter++;
                    if (counter < sizeTotal) {
                        reading = readingsRed.get(counter);
                    } else {
                        break;
                    }
                }
            }
            dateParse = new SimpleDateFormat("dd/MM/yyyy").format(nextDate);
            seriesData.add(new CustomDataEntry(dateParse, paginasleidas));
            Calendar c = Calendar.getInstance();
            c.setTime(nextDate);
            c.add(Calendar.DATE, 1);
            nextDate = c.getTime();

        }


        return seriesData;
    }


    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value) {
            super(x, value);
        }
    }
}