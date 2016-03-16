package com.eason.ripplelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RippleLayout rippleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rippleLayout = (RippleLayout) findViewById(R.id.rippleLayout);
        rippleLayout.setRippleColor(R.color.ripple_material_light);

        rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity","rippleLayout is clicking");
            }
        });
    }
}
