package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;
    private ImageButton BtnLeft;
    private ImageButton BtnRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        iv = findViewById(R.id.imageView);
        BtnLeft = findViewById(R.id.BtnLeft);
        BtnRight = findViewById(R.id.BtnRight);
    }

    public void OnBtnLeft(View view) {
        int num = Integer.parseInt(tv.getText().toString());
        if(num>1){
            tv.setText(String.valueOf(num-1));
            switch (num){
                case 2:
                    iv.setImageResource(R.drawable.cat1);
                    BtnLeft.setImageResource(R.drawable.prev_d);
                    break;
                case 3:
                    iv.setImageResource(R.drawable.cat2);
                    break;
                case 4:
                    iv.setImageResource(R.drawable.cat3);
                    break;
                case 5:
                    iv.setImageResource(R.drawable.cat4);
                    BtnRight.setImageResource(R.drawable.btn_next);
            }
        }
    }

    public void OnBtnRight(View view) {
        int num = Integer.parseInt(tv.getText().toString());
        if(num<5) {
            tv.setText(String.valueOf(num + 1));
            switch (num) {
                case 1:
                    iv.setImageResource(R.drawable.cat2);
                    BtnLeft.setImageResource(R.drawable.btn_prev);
                    break;
                case 2:
                    iv.setImageResource(R.drawable.cat3);
                    break;
                case 3:
                    iv.setImageResource(R.drawable.cat4);
                    break;
                case 4:
                    iv.setImageResource(R.drawable.cat5);
                    BtnRight.setImageResource(R.drawable.next_d);
            }
        }
    }
}