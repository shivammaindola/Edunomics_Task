package com.example.edunomics_task;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText editheight;
    TextView tvProgressLabel;
    double t;
    double h;
    double gravity = 10.0;
    double t2;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editheight=(EditText)findViewById(R.id.editheight);
        // set a change listener on the SeekBar
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(4);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);


        int progress = seekBar.getProgress();
        tvProgressLabel = findViewById(R.id.textView);
        tvProgressLabel.setText("0.25");
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            t=0.0;
            h=0.0;
            t2=0.0;
            float progressD=(float) progress/4;
            tvProgressLabel.setText(String.valueOf(progressD));
            String height= editheight.getText().toString();
            h= Double.parseDouble(height);
            String cor= String.valueOf(progressD);
            double e=0.0;
            e=Double.parseDouble(cor);

            //make a graph

            GraphView graph = (GraphView) findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {});

            //set y axis first - HEIGHT
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(h);
            //series.appendData(new DataPoint(0,h),true,1);
            //calculate first time
            t = Math.sqrt((2*h)/gravity);
            //set x axis - TIME
            graph.getViewport().setMinX(0);
            //graph.getViewport().setMaxX(t*5);
            int datapoints=2;
            series.appendData(new DataPoint(0,h),true,datapoints);
            series.appendData(new DataPoint(t,0),true,datapoints);


            double h1=h*(e*e);
            double t1=t*e;
            t2=t;
            while(h1>0)
            {
                datapoints++;
                 t2+=t1;
                series.appendData(new DataPoint(t2,h1),true,datapoints);
                t2+=t1;
                datapoints++;
                series.appendData(new DataPoint(t2,0),true,datapoints);
                h1=h1*(e*e);
                t1=t1*e;
            }
           Toast.makeText(MainActivity.this, "Total number of bounces are:  "+ datapoints, Toast.LENGTH_SHORT).show();
            graph.addSeries(series);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setXAxisBoundsManual(true);


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };

}