package com.example.ieapplication;

import android.icu.util.IndianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Fitness extends AppCompatActivity {
    IndicatorSeekBar indicatorSeekBar_fruit;
    IndicatorSeekBar indicatorSeekBar_vegetable;
    IndicatorSeekBar indicatorSeekBar_meat;
    IndicatorSeekBar indicatorSeekBar_diary;
    IndicatorSeekBar indicatorbar_grain;
    ImageButton save_button;
    TextView date;
    private static  final String TAG="DataBaseHelper_FIT";
    DataBaseHelper_Food dataBaseHelperFood;
    int indicator_fruit,indicator_vegetable,indicator_diary,indicator_meat,indicator_grain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indicator_diary=0;
        indicator_fruit=0;
        indicator_grain=0;
        indicator_meat=0;
        indicator_vegetable=0;


        dataBaseHelperFood= new DataBaseHelper_Food(this);
        setContentView(R.layout.activity_fitness);
        date=findViewById(R.id.date_fit);
        Calendar calendar = Calendar.getInstance();
        String current_date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(current_date);
       // String current_date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        indicatorbar_grain=findViewById(R.id.seekBar_grain);
        indicatorbar_grain.setMin(0);
        indicatorbar_grain.setMax(7);
        indicatorSeekBar_fruit= findViewById(R.id.seekBar2_fit);
        indicatorSeekBar_fruit.setMax(7);
        indicatorSeekBar_fruit.setMin(0);
        indicatorSeekBar_vegetable=findViewById(R.id.seekBar3_fit);
        indicatorSeekBar_vegetable.setMax(7);
        indicatorSeekBar_vegetable.setMin(0);
        indicatorSeekBar_meat=findViewById(R.id.seekBar4_fit);
        indicatorSeekBar_meat.setMax(7);
        indicatorSeekBar_meat.setMin(0);
        indicatorSeekBar_diary=findViewById(R.id.seekBar4_food);
        indicatorSeekBar_diary.setMax(7);
        indicatorSeekBar_diary.setMin(0);

        save_button=findViewById(R.id.savebutton_food);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"The values "+indicator_meat);
                Toast.makeText(getApplicationContext(), "Food Intake Saved!",
                        Toast.LENGTH_LONG).show();
                dataBaseHelperFood.insert_food(indicator_fruit,indicator_vegetable,indicator_diary,indicator_meat,indicator_grain);
            }
        });
        indicatorbar_grain.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                indicator_grain=seekBar.getProgress();

            }

            @Override
            public void onDragging(float progress) {

            }
        });
        indicatorSeekBar_fruit.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                indicator_fruit=seekBar.getProgress();

            }

            @Override
            public void onDragging(float progress) {

            }
        });
        indicatorSeekBar_vegetable.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                indicator_vegetable=seekBar.getProgress();

            }

            @Override
            public void onDragging(float progress) {

            }
        });
        indicatorSeekBar_diary.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                indicator_diary=seekBar.getProgress();

            }

            @Override
            public void onDragging(float progress) {

            }
        });
        indicatorSeekBar_meat.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                indicator_meat=seekBar.getProgress();

            }

            @Override
            public void onDragging(float progress) {

            }
        });



    }

}
