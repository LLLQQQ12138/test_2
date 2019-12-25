package com.example.text_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Word extends Fragment {
    private View view;
    private TextView word_en;
    private TextView word_ch;
    private TextView word_detail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_word,container,false);
        word_en = view.findViewById(R.id.word_en1);
        word_ch = view.findViewById(R.id.word_ch1);
        word_detail = view.findViewById(R.id.word_detail1);
        //word_en.setText("12");
        //word_ch.setText("45");
        //word_detail.setText("67");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*TextView word_en = view.findViewById(R.id.word_en1);
        TextView word_ch = view.findViewById(R.id.word_ch1);
        TextView word_detail = getView().findViewById(R.id.word_detail1);
        word_en.setText("英文");
        word_ch.setText("中文");
        word_detail.setText("例句");*/
    }

    /*public static Fragment_Word putData(String English, String Chinese, String Detail) {


        Bundle args = new Bundle();
        args.putString("English",English);
        args.putString("Chinese",Chinese);
        args.putString("Detail",Detail);

        Fragment_Word fragment = new Fragment_Word();
        fragment.setArguments(args);

        return fragment;
    }*/


    public void reflash(String en,String ch,String de)
    {
        TextView word_en1 = view.findViewById(R.id.word_en1);
        TextView word_ch1 = view.findViewById(R.id.word_ch1);
        TextView word_detail1 = view.findViewById(R.id.word_detail1);
        word_en1.setText(en);
        word_ch1.setText(ch);
        word_detail1.setText(de);
    }

    /*public void setTextAll(String)
    {
        word_en.setText("123");
        word_ch.setText("456");
        word_detail.setText("789");
    }*/
}
