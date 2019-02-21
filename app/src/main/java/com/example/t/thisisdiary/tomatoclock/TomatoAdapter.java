package com.example.t.thisisdiary.tomatoclock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.bean.Tomato;

import java.util.List;

public class TomatoAdapter extends ArrayAdapter<Tomato> {

    private int resourceId;

    public TomatoAdapter(Context context, int textViewResourceId, List<Tomato> objects) {
          super(context, textViewResourceId, objects);
          resourceId = textViewResourceId;
    }

    /** 这个方法在每个子项被滚动到屏幕内的时候被调用 **/
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tomato tomato = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null)
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_task_name = (TextView)view.findViewById(R.id.tv_task_name);
            viewHolder.tv_tomato_number = (TextView)view.findViewById(R.id.tv_tomato_number);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_task_name.setText(tomato.getTaskName());
        viewHolder.tv_tomato_number.setText(String.valueOf(tomato.getTomatoNumber()));
        return view;
    }

    class ViewHolder {
        TextView tv_task_name;
        TextView tv_tomato_number;
    }
}
