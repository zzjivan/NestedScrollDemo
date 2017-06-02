package com.snick.zzj.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button demo_coordinary_appbar;
    private List<File> list = new ArrayList<File>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demo_coordinary_appbar = (Button) findViewById(R.id.demo_coordinary_appbar);
        demo_coordinary_appbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.demo_coordinary_appbar:
                startActivity(new Intent(this, CoordinaryAppBarActivity.class));
                break;
            default:break;
        }
    }
}
