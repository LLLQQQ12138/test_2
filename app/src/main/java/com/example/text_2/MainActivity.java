package com.example.text_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Word> mDatas=new ArrayList<>();
    private Adapter adapter;
    private RecyclerView rv_home;
    private Fragment fragment_word;
    //private Fragment Translate;
    //private Fragment News;
    //private int fragmentId = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu. main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id. flash_item:
                queryAll();
                break;
            case R.id. help_item:
                Toast.makeText(MainActivity.this, "帮助",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.query_internet_item:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,
                        query_internet.class);
                startActivity(intent);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return true;
    }

    @Nullable

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

        //int orientation = getResources().getConfiguration().orientation;

        Button add = findViewById(R.id.add);
        //TextView tlst_text = findViewById(R.id.tslt_text);
        //TextView view1 = findViewById(R.id.home);
        //TextView view2 = findViewById(R.id.translate);
        //TextView view3 = findViewById(R.id.news);
        rv_home= findViewById(R.id.rv_home);
       // RadioGroup radiogroup1 = findViewById(R.id.radiogroup1);

        //view1.setOnClickListener(l);
        //view2.setOnClickListener(l);
        //view3.setOnClickListener(l);

        rv_home.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv_home.addItemDecoration(new MyDecoration());
        rv_home.setAdapter(new Adapter(mDatas,this));
        queryAll();

        EditText input = findViewById(R.id.edit_input);
        final String str_input = input.getText().toString();
        input.addTextChangedListener(new TextWatcher() {

                                         @Override
                                         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                         }

                                         @Override
                                         public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                                             if(str_input==null)

                                             {queryAll();}
                                             else
                                                 //
                                             {queryPart();}
                                             //这个应该是在改变的时候会做的动作吧，详细还没用到过。
                                         }

                                         @Override
                                         public void afterTextChanged(Editable editable) {

                                         }
                                     });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItem();
            }
        });

    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        //通过onSaveInstanceState方法保存当前显示的fragment
        outState.putInt("fragment_Id",fragmentId);
        super.onSaveInstanceState(outState);
    }*/

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //通过FragmentManager获取保存在FragmentTransaction中的Fragment实例
        Home = mFragmentManager.findFragmentByTag("clothes_fragment");
        Translate = mFragmentManager.findFragmentByTag("food_fragment");
        News = mFragmentManager.findFragmentByTag("hotel_fragment");
        //恢复销毁前显示的Fragment
        setFragment(savedInstanceState.getInt("fragment_id"));
    }*/

    /*private void setFragment(int index){
        //获取Fragment管理器
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        //隐藏所有Fragment
        hideFragments(mTransaction);
        switch (index){
            default:
                break;
            case 0:
                //设置菜单栏为选中状态（修改文字和图片颜色）
                //mTextClothes.setCompoundDrawablesWithIntrinsicBounds(0,
                // R.drawable.ic_clothes_pressed,0,0);

                //显示对应Fragment
                if(Home == null){
                    Home = new Fragment_Home();
                    mTransaction.add(R.id.fragment, Home,
                            "Home");
                }else {
                    mTransaction.show(Home);
                }
                break;
            case 1:
                /*mTextFood.setTextColor(getResources()
                        .getColor(R.color.colorTextPressed));
                mTextFood.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.ic_food_pressed,0,0);

                if(Translate == null){
                    Translate = new Fragment_Translate();
                    mTransaction.add(R.id.fragment, Translate,
                            "Translate");
                }else {
                    mTransaction.show(Translate);
                }
                break;
            case 2:
                /*mTextHotel.setTextColor(getResources()
                        .getColor(R.color.colorTextPressed));
                mTextHotel.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.ic_hotel_pressed,0,0);
                if(News == null){
                    News = new Fragment_News();
                    mTransaction.add(R.id.fragment,News,
                            "News");
                }else {
                    mTransaction.show(News);
                }
                break;
        }
        //提交事务
        mTransaction.commit();
    }*/

    /*private void hideFragments(FragmentTransaction transaction){
        if(Home != null){
            //隐藏Fragment
            transaction.hide(Home);
            //将对应菜单栏设置为默认状态
            /*Home.setTextColor(getResources()
                    .getColor(R.color.colorText));
            mTextClothes.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.ic_clothes,0,0);
        }
        if(Translate != null){
            transaction.hide(Translate);
            /*mTextFood.setTextColor(getResources()
                    .getColor(R.color.colorText));
            mTextFood.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.ic_food,0,0);
        }
        if(News != null){
            transaction.hide(News);
            /*mTextHotel.setTextColor(getResources()
                    .getColor(R.color.colorText));
            mTextHotel.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.drawable.ic_hotel,0,0);
        }
    }*/

    private void queryAll() {
        mDatas.clear();
        DBHelper dbHelper=new DBHelper(this,"words_db",null,1);
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query("words",null,null,null,null,null,null);
        cursor.moveToFirst();
        do{

            String en = cursor.getString(cursor.getColumnIndex("English"));
            //String ch=cursor.getString(cursor.getColumnIndex("Chinese"));
            //String detail=cursor.getString(cursor.getColumnIndex("Detail"));
            Word w = new Word(en);
            mDatas.add(w);//取出en，填充到列表

        }while (cursor.moveToNext());

        adapter=new Adapter(mDatas,MainActivity.this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rv_home.setLayoutManager(linearLayoutManager);
        rv_home.setAdapter(adapter);
    }

    private void queryPart() {
            EditText input = findViewById(R.id.edit_input);
            String str_input = input.getText().toString();
            mDatas.clear();
            DBHelper dbHelper = new DBHelper(this, "words_db", null, 1);
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query("words", null, "English" + "  LIKE ? ",
                    new String[]{"%" + str_input + "%"}, null, null, null);
            //Cursor cursor=sqLiteDatabase.query("words",null,null,null,null,null,null);
            if (cursor.getCount() == 0) {
                Toast.makeText(MainActivity.this, "无查询结果",
                        Toast.LENGTH_SHORT).show();
            } else {
                cursor.moveToFirst();
                do {

                    String en = cursor.getString(cursor.getColumnIndex("English"));
                    //String ch=cursor.getString(cursor.getColumnIndex("Chinese"));
                    //String detail=cursor.getString(cursor.getColumnIndex("Detail"));
                    Word w = new Word(en);
                    mDatas.add(w);//取出所有学生姓名，填充到列表

                } while (cursor.moveToNext());

                adapter = new Adapter(mDatas, MainActivity.this);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                rv_home.setLayoutManager(linearLayoutManager);
                rv_home.setAdapter(adapter);
            }
        }



    public void AddItem() {

        // LayoutInflater是用来找layout文件夹下的xml布局文件，并且实例化
        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        // 把activity_login中的控件定义在View中
        final View textEditView = factory.inflate(R.layout.word_eidt,
                null);

        // 将LoginActivity中的控件显示在对话框中
        new AlertDialog.Builder(MainActivity.this)
                // 对话框的标题
                .setTitle("新增词条")
                .setView(textEditView)
                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Fragment_Word f = new Fragment_Word();
                        EditText et_en = (EditText) textEditView
                                .findViewById(R.id.et_en);
                        EditText et_ch = (EditText) textEditView
                                .findViewById(R.id.et_ch);
                        EditText et_detail = (EditText) textEditView
                                .findViewById(R.id.et_detail);
                            String English = et_en.getText().toString();
                            String Chinese = et_ch.getText().toString();
                            String Detail = et_detail.getText().toString();

                            DBHelper dbHelper=new DBHelper(MainActivity.this,"words_db",null,1);
                            SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put("English",English);
                            values.put("Chinese",Chinese);
                            values.put("Detail",Detail);
                            sqLiteDatabase.insert("words",null,values);

                            queryAll();

                    }
                })
                // 对话框的“退出”单击事件
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                // 设置dialog是否为模态，false表示模态，true表示非模态
                .setCancelable(false)
                // 对话框的创建、显示
                .create().show();
    }

    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
            super.getItemOffsets(outRect, itemPosition, parent);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }
    /*View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = null;
            switch (view.getId()) {
                case R.id.home:
                    /*if (f == null) {
                        f = new Fragment_Home();
                        ft.add(R.id.fragment, f);
                    }else {
                        ft.show(f);
                    }
                    f = new Fragment_Home();
                    //setFragment(0);
                    break;
                case R.id.translate:
                    /*if (f == null) {
                        f = new Fragment_Translate();
                        ft.add(R.id.fragment, f);
                    }else {
                        ft.show(f);
                    }
                    //f = new Fragment_Translate();
                    //setFragment(1);
                    break;
                case R.id.news:
                    /*if (f == null) {
                        f = new Fragment_News();
                        ft.add(R.id.fragment, f);
                    }else {
                        ft.show(f);
                    }
                    break;
                    //f = new Fragment_News();
                    //setFragment(2);
                default:
                    break;

            }

        }
    };*/



}
