package com.eason.ripplelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RippleLayout rippleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rippleLayout = (RippleLayout) findViewById(R.id.rippleLayout);
        rippleLayout.setRippleColor(R.color.ripple_material_light);
    }
}
