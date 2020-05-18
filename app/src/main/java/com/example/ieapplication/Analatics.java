package com.example.ieapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import com.anychart.enums.ScaleStackMode;
import com.anychart.scales.Linear;
import com.anychart.enums.Orientation;

public class Analatics extends AppCompatActivity {

    DataBaseHelper_Sleep dataBaseHelper_sleep;
    DatabaseHelper_exc databaseHelper_exc;
    DatabaseHelper_Symtoms databaseHelper_symtoms;
    DataBaseHelper_Food dataBaseHelper_food;
    private List<DataEntry> values;
    private  AnyChartView anyChartView;
    private Cartesian cartesian;
    private Stack<Integer> scores;
    private Linear scalesLinear;
    private static  final String TAG="DataBaseHelper_Sleep";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_analatics);
        //FloatingActionButton fab = findViewById(R.id.fab);
        dataBaseHelper_sleep=new DataBaseHelper_Sleep(this);
        databaseHelper_exc=new DatabaseHelper_exc(this);
        databaseHelper_symtoms=new DatabaseHelper_Symtoms(this);
        dataBaseHelper_food=new DataBaseHelper_Food(this);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        anyChartView = findViewById(R.id.chart);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        cartesian = AnyChart.cartesian();
        com.anychart.core.axes.Linear yAxis1 = cartesian.yAxis(1);
        yAxis1.orientation("right");
        yAxis1.title("Pain and Stiffness Level");
        Linear yScale = cartesian.yScale();
        yScale.minimum(0);
        yScale.maximum(500);
        yScale.ticks().interval(100);
        yScale.minorTicks().interval(100);

// setting for y axis title
        cartesian.yAxis(0).title("Lifestyle Score");
        cartesian.xAxis(0).title("Day");

// adding extra Y scale


// Second additional axis

        cartesian.animation(true);

        cartesian.title("Pain and Lifestyle Analysis");

        //cartesian.yScale().stackMode(ScaleStackMode.VALUE);

        scalesLinear = Linear.instantiate();
        //scalesLinear.minimum(0d);
        //scalesLinear.maximum(100d);
        //scalesLinear.ticks("{ interval: 20 }");

        com.anychart.core.axes.Linear extraYAxis = cartesian.yAxis(1d);
        extraYAxis.orientation(Orientation.RIGHT)
                .scale(scalesLinear);
        extraYAxis.labels()
                .padding(0d, 0d, 0d, 5d)
                .format("{%Value}");

        /*cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);*/

        fetch_data();



    }
    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value,Number sleep, Number pain,Number stiff,Number veg,Number meat,Number fruit, Number diary, Number aero, Number flex, Number bal, Number strn) {
            super(x, value);
            setValue("value2", pain);
            setValue("value3", veg);
            setValue("value4", aero);
            setValue("value5", flex);
            setValue("value11",strn);
            setValue("value6", stiff);
            setValue("value7", meat);
            setValue("value8", fruit);
            setValue("value9", diary);
            setValue("value10", sleep);
            setValue("value12",bal);
        }
    }

    public void fetch_data(){
        scores=new Stack<>();
        values=new ArrayList<>();
        List<DataEntry> values_final=new ArrayList<>();
        Log.d(TAG,"Fetching Started");
        Cursor fetched_data_sleep=dataBaseHelper_sleep.getData();
        Cursor fetched_data_exc=databaseHelper_exc.getData();
        Cursor fetched_data_sym=databaseHelper_symtoms.getData();
        Cursor fetched_data_food=dataBaseHelper_food.getData();
        Log.d(TAG,"Fetching Finished" + fetched_data_sleep.getCount());
        Stack<String> pain_day=new Stack<>();
        Stack<Integer> pain_level=new Stack<>();
        Stack<Integer> stiff_level=new Stack<>();
        Stack<String> food_day=new Stack<>();
        Stack<Integer> aero_level=new Stack<>();
        Stack<Integer> stren_level=new Stack<>();
        Stack<Integer> flex_level=new Stack<>();
        Stack<Integer> bala_level=new Stack<>();
        Stack<String> exc_day=new Stack<>();
        Stack<Integer> meat_level=new Stack<>();
        Stack<Integer> veg_level=new Stack<>();
        Stack<Integer> diary_level=new Stack<>();
        Stack<Integer> fruit_level=new Stack<>();
        Stack<Integer> grain_level=new Stack<>();
        Stack<String> sleep_day=new Stack<>();
        Stack<Integer> hour_level=new Stack<>();
        fetched_data_sym.moveToFirst();
        Log.d(TAG,"Pain");
        while(!fetched_data_sym.isAfterLast()) {
            pain_day.push(fetched_data_sym.getString(fetched_data_sym.getColumnIndex("date")));
            pain_level.push(fetched_data_sym.getInt(fetched_data_sym.getColumnIndex("pain")));
            stiff_level.push(fetched_data_sym.getInt(fetched_data_sym.getColumnIndex("stiffness")));

            //Log.d(TAG, String.valueOf(value));
            //values.add(new ValueDataEntry(day,value));

            fetched_data_sym.moveToNext();
        }
        Log.d(TAG,"Pain finished "+pain_day.size());
        fetched_data_exc.moveToFirst();
        while(!fetched_data_exc.isAfterLast()) {
            exc_day.push(fetched_data_exc.getString(fetched_data_exc.getColumnIndex("date")));
            aero_level.push(fetched_data_exc.getInt(fetched_data_exc.getColumnIndex("aerobic")));
            stren_level.push(fetched_data_exc.getInt(fetched_data_exc.getColumnIndex("strength")));
            flex_level.push(fetched_data_exc.getInt(fetched_data_exc.getColumnIndex("flexibility")));
            bala_level.push(fetched_data_exc.getInt(fetched_data_exc.getColumnIndex("balance")));

            //Log.d(TAG, String.valueOf(value));
            //values.add(new ValueDataEntry(day,value));
            fetched_data_exc.moveToNext();
        }
        fetched_data_food.moveToFirst();
        while(!fetched_data_food.isAfterLast()) {
            food_day.push(fetched_data_food.getString(fetched_data_food.getColumnIndex("date")));
            meat_level.push(fetched_data_food.getInt(fetched_data_food.getColumnIndex("meat")));
            fruit_level.push(fetched_data_food.getInt(fetched_data_food.getColumnIndex("fruits")));
            diary_level.push(fetched_data_food.getInt(fetched_data_food.getColumnIndex("diary")));
            veg_level.push(fetched_data_food.getInt(fetched_data_food.getColumnIndex("veg")));
            grain_level.push(fetched_data_food.getInt(fetched_data_food.getColumnIndex("grain")));

            //Log.d(TAG, String.valueOf(value));
            //values.add(new ValueDataEntry(day,value));
            fetched_data_food.moveToNext();
        }
        fetched_data_sleep.moveToFirst();
        while(!fetched_data_sleep.isAfterLast()) {
            sleep_day.push(fetched_data_sleep.getString(fetched_data_sleep.getColumnIndex("date")));
            hour_level.push(fetched_data_sleep.getInt(fetched_data_sleep.getColumnIndex("sleep_duration")));
            //Log.d(TAG, String.valueOf(value));
            //values.add(new ValueDataEntry(day,value));
            fetched_data_sleep.moveToNext();
        }
        Log.d(TAG,"Food finished "+ food_day.size());
        //cartesian.data(values);
        //anyChartView.setChart(cartesian);
        Iterator iterator=pain_day.iterator();
        Log.d(TAG,"Iterator");
        while(iterator.hasNext()){
            int sleep_score=0;
            int exc_score=0;
            int food_score=0;
            int flex=0;
            int bal=0;
            int stren=0;
            int meat=0;
            int veg=0;
            int aero=0;
            int diary=0;
            int fruit=0;
            int x=0;
            int grain=0;
            Log.d(TAG,"Iterator Inside");
            String date= (String) iterator.next();
            int pain=pain_level.get(pain_day.indexOf(date));
            int stiff=stiff_level.get(pain_day.indexOf(date));
            if(sleep_day.contains(date)){
                x=hour_level.get(sleep_day.indexOf(date));
                Log.d(TAG,"Fetched Sleep "+ x+" "+date);

                if(x>7 && x<=9){
                    sleep_score=100;
                }
                else if(x>9){
                    sleep_score=((16/7)-(x/7))*100;
                }
                else{
                    sleep_score= (x/7)*100;
                }
            }
            if(exc_day.contains(date)){
                flex=flex_level.get(exc_day.indexOf(date));
                bal=bala_level.get(exc_day.indexOf(date));
                stren=stren_level.get(exc_day.indexOf(date));
                aero=aero_level.get(exc_day.indexOf(date));
                int total= flex+bal+stren+aero;
                if(total>30 && total<=45 ){
                    exc_score=100;
                }
                else if(total>45){
                    exc_score=((int)(2.5-(total/30)))*100;
                }
                else{
                    exc_score=(total/30)*100;
                }
            }
            if(food_day.contains(date)){
                Log.d(TAG,"Iterator Inside food");
                meat=meat_level.get(food_day.indexOf(date));
                veg=veg_level.get(food_day.indexOf(date));
                diary=diary_level.get(food_day.indexOf(date));
                fruit=fruit_level.get(food_day.indexOf(date));
                grain=grain_level.get(food_day.indexOf(date));
                Log.d(TAG,"Fetched meat " +meat);
                int total=meat+veg+diary+fruit+grain;
                if(total>11 && total<=16.5){
                    food_score=100;
                }
                else if(total>16.5){
                    food_score=((int)(2.5-(total/11)))*100;
                }
                else{
                    food_score=(total/11)*100;
                }
            }
            int total_score=food_score+exc_score+sleep_score;
            scores.push(food_score+exc_score+sleep_score);
            Log.d(TAG,"The total score is "+scores);
            values.add(new CustomDataEntry("1",total_score,x,pain,stiff,veg,meat,fruit,diary,aero,flex,bal,stren));
        }

        int i=scores.indexOf(scores.lastElement());
        int counter=0;

        while (counter<=20 && counter<=scores.size()-1){
            int value=scores.get(i);
            int pain=pain_level.get(i);
            values_final.add(values.get(i));
            i=i-1;
            String temp= String.valueOf(i);
            Log.d(TAG,"The value is "+value);
            counter=counter+1;
        }
        values.add(new CustomDataEntry("2",300,8,5,7,6,10,3,8,10,30,30,30));
        values.add(new CustomDataEntry("3",150,9,9,8,6,10,3,8,10,30,30,30));
        values.add(new CustomDataEntry("4",250,7,8,9,6,10,3,8,10,30,30,30));
        values.add(new CustomDataEntry("5",170,8,2,5,6,10,3,8,10,30,30,30));
        values.add(new CustomDataEntry("6",250,9,1,5,6,10,3,8,10,30,30,30));
        values.add(new CustomDataEntry("7",180,6,8,4,6,10,3,8,10,30,30,30));

        /*List<DataEntry> values2=new ArrayList<>();
        values.add(new ValueDataEntry("2",9));
        values.add(new ValueDataEntry("3",5));
        values.add(new ValueDataEntry("4",7));
        values.add(new ValueDataEntry("5",9));
        values.add(new ValueDataEntry("6",10));
        values2.add(new ValueDataEntry("1",100));
        values2.add(new ValueDataEntry("2",100));
        values2.add(new ValueDataEntry("3",200));
        values2.add(new ValueDataEntry("4",400));
        values2.add(new ValueDataEntry("5",500));
        values2.add(new ValueDataEntry("6",600));
        Log.d(TAG,"The total score is "+values.toString());*/

        Set set = Set.instantiate();
        set.data(values);
        Mapping lineData = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping lineData2 = set.mapAs("{ x: 'x', value: 'value6' }");
        // = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping column1Data = set.mapAs("{ x: 'x', value: 'value' }");
        //Mapping column1Data = set.mapAs("{ x: 'x', value: 'value2' }");
        //cartesian.data(values);
        Column column=cartesian.column(column1Data);
        column.name("Lifestyle");
        //cartesian.crosshair(true);



        Line line = cartesian.line(lineData);
        cartesian.legend(true);
        line.name("Pain");
        line.hovered().markers().enabled(true);
        Line line2=cartesian.line(lineData2);
        line2.name("Stiffness");
        column.tooltip().format("Day: {%x}<br/>Sleep: {%value10} Hours<br/>Pain: {%value2}<br/>Veg: {%value3} Cups<br/>Fruits: {%value8} Cups<br/>Meat: {%value7} Cups<br/>Diary: {%value9} Cups<br/>Aerobics: {%value4} Minutes<br/>Strength: {%value11} Minutes<br/>Flexibility: {%value5} Minutes<br/>Balance: {%value12} Minutes");
        column.tooltip().useHtml(true);
        line.yScale(scalesLinear);
        line2.yScale(scalesLinear);
        anyChartView.setChart(cartesian);


    }



}
