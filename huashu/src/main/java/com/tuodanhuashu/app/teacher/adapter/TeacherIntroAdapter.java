package com.tuodanhuashu.app.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tuodanhuashu.app.R;

import java.util.List;

public class TeacherIntroAdapter extends BaseAdapter {

    private Context context;

    private String teacher[];

    public TeacherIntroAdapter(Context context, String teacher[]) {
        this.context = context;
        this.teacher = teacher;
    }

    @Override
    public int getCount() {
        return teacher.length;
    }

    @Override
    public Object getItem(int position) {
        return teacher[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.teacher_pic_layout,null);
        ImageView teacherIv = v.findViewById(R.id.teacher_pic_iv);

        Glide.with(context).load(teacher[position]).into(teacherIv);
        return v;
    }
}
