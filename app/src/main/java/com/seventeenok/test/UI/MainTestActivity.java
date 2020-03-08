package com.seventeenok.test.UI;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.seventeenok.test.UI.paint.gradient.RadarGradientView;

public class MainTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_test);
        setContentView(new RadarGradientView(this));
//        setContentView(new ZoomImageView(this));
//        setContentView(new MyGradientView(this));
    }   
}
