package com.example.text_2;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.LinearViewHolder> {
    @NonNull
    private Context mContext;
    private List<Word> mDatas = new ArrayList<>();

    public Adapter(List<Word> mDatas,Context context)
    {
        this.mDatas = mDatas;
        this.mContext = context;
    }

    @Override
    public Adapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;*/
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_linear_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.LinearViewHolder holder, final int position) {
        //holder.textView.setText("hello world");
        holder.textView.setText(mDatas.get(position).getEnglish());
        //删除学生信息
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除当前行
                deleteItem(position);
            }
        });

        holder.modBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改当前行学生姓名
                updateItem(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Word f = new Fragment_Word();

               // TextView word_en = view.findViewById(R.id.word_en1);
               // TextView word_ch = view.findViewById(R.id.word_ch1);
                //TextView word_detail = view.findViewById(R.id.word_detail1);
                Intent intent = new Intent(mContext,word_activity.class);
                DBHelper dbHelper=new DBHelper(mContext,"words_db",null,1);
                SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
                Cursor cursor=sqLiteDatabase.query("words",null,null,null,null,null,null);
                cursor.moveToPosition(position);
                String en = cursor.getString(cursor.getColumnIndex("English"));
                String ch=cursor.getString(cursor.getColumnIndex("Chinese"));
                String de=cursor.getString(cursor.getColumnIndex("Detail"));
                intent.putExtra("English", en);
                intent.putExtra("Chinese", ch);
                intent.putExtra("Detail", de);
                mContext.startActivity(intent);
                //word_en.setText(en);
                //word_ch.setText(ch);
                //word_detail.setText(de);
                //MainActivity.addFragment();
                //f.reflash(en,ch,de);
                //
            }
        });
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private void updateItem(final int position) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View EditView = factory.inflate(R.layout.word_eidt,
                null);
        final EditText et_en = (EditText) EditView
                .findViewById(R.id.et_en);
        final EditText et_ch = (EditText) EditView
                .findViewById(R.id.et_ch);
        final EditText et_detail = (EditText) EditView
                .findViewById(R.id.et_detail);

        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("修改");
        builder.setView(EditView);
        //   builder.setMessage("您确定要修改"+studentList.get(position).getName()+"吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(context,editText.getText().toString(),Toast.LENGTH_SHORT).show();

                //修改更新数据库
                DBHelper dbHelper=new DBHelper(mContext,"words_db",null,1);
                SQLiteDatabase database=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("English",et_en.getText().toString());
                values.put("Chinese",et_ch.getText().toString());
                values.put("Detail",et_detail.getText().toString());
                database.update("words",values,"English=?",new String[]{mDatas.get(position).getEnglish()});
                database.update("words",values,"Chinese=?",new String[]{mDatas.get(position).getChinese()});
                database.update("words",values,"Detail=?",new String[]{mDatas.get(position).getDetail()});

                //修改list内容
                mDatas.get(position).setEnglish(et_en.getText().toString());
                //mDatas.get(position).setChinese(et_ch.getText().toString());
                //mDatas.get(position).setDetail(et_detail.getText().toString());
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void deleteItem(final int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("删除提醒");
        builder.setMessage("您确定要删除吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除
                DBHelper dbHelper=new DBHelper(mContext,"words_db",null,1);
                SQLiteDatabase database=dbHelper.getWritableDatabase();
                database.delete("words","English=?",new String[]{mDatas.get(position).getEnglish()});
                database.delete("words","Chinese=?",new String[]{mDatas.get(position).getChinese()});
                database.delete("words","Detail=?",new String[]{mDatas.get(position).getDetail()});
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        Button delBtn;
        Button modBtn;
        View itemView;

        public LinearViewHolder(View itemView){
            super(itemView);
            this.itemView=itemView;
            textView = itemView.findViewById(R.id.item_en);
            delBtn = itemView.findViewById(R.id.btn_delete);
            modBtn = itemView.findViewById(R.id.btn_modify);
        }
    }
}
