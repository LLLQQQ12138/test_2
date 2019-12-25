package com.example.text_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class word_activity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity);
        TextView word_en = findViewById(R.id.word_en);
        TextView word_ch = findViewById(R.id.word_ch);
        TextView word_detail = findViewById(R.id.word_detail);
        Intent intent = getIntent();
        String en = intent.getStringExtra("English");
        String ch = intent.getStringExtra("Chinese");
        String de = intent.getStringExtra("Detail");
        word_en.setText(en);
        word_ch.setText(ch);
        word_detail.setText(de);

    }
}
