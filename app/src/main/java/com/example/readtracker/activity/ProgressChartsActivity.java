package com.example.readtracker.activity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.sample.R;

import java.util.ArrayList;
import java.util.List;
public class ProgressChartsActivity extends AppCompatActivity {
    
    // Instancias de WebRequests.
    FireReading fireReading;
    // Notificaciones.
    String msgToast;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_charts);
        Intent intent = getIntent(); // Get serializable intent data
        readingsRed = (Reading) intent.getSerializableExtra("readingsRed");
        
        
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
        cartesian.title("Progreso de lectura: {{etiqueta}}" );
        cartesian.yAxis(0).title("Número de páginas leídas acumuladas");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<Reading> seriesData = prepareData(readingsRed);


        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("{{etiqueta}}");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

               cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);

    }



private List<Reading> prepareData(ArrayList<Reading> readingsRed){
    int paginasleidas = 0;

    List<DataEntry> seriesData = new ArrayList<>();
    Date firstDate = readingRed.get(0).getRedDate();
    Date lastDate = readingRed.get(readingRed.size()-1).getRedDate();

    long daysBetween = Duration.between(firstDate, lastDate).toDays();
    Date nextDate = firstDate;
    String dateParse = "";
    int counter = 0;
    Reading reading;
    for (int i = 0; i <= daysBetween; i ++){
        reading = readingsRed.get(counter);
        dateParse = new SimpleDateFormat("dd/MM/yyyy").format(nextDate); 
        
        if (dateParse.equals(reading.parsedDate())){
            paginasleidas += reading.getPages();
            counter++;
        }
        
        seriesData.add( new CustomDataEntry (dateParse, paginasleidas) );
        

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