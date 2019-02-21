package com.example.t.thisisdiary.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.bean.Diary;

import java.util.ArrayList;
import java.util.List;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder>
                implements Filterable {

    private Context mContext;

    private int position;

    private List<Diary> mDiaryList;

    private OnItemClickListener mOnItemClickListener = null;

    private OnItemLongClickListener mOnItemLongClickListener;

    private MyFilter filter = null;

    private FilterListener filterListener = null;

    /** 构造函数（用于把要展示的数据源传进来） **/
    public DiaryListAdapter(List<Diary> diaryList) {
        mDiaryList = diaryList;
    }

    public DiaryListAdapter(List<Diary> diaryList, Context context, FilterListener listener) {
        this.mDiaryList = diaryList;
        this.mContext = context;
        this.filterListener = listener;
    }

    /** 创建ViewHolder实例 **/
    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        setPosition(holder.getAdapterPosition());
        return holder;
    }

    /** 用于对RecyclerView的子项的数据赋值，在每个子项被滚动到屏幕内时执行**/
    @Override
    public void onBindViewHolder(@NonNull final DiaryListAdapter.ViewHolder holder, final int position) {
        Diary diary = mDiaryList.get(position);
        holder.tv_diary_title.setText(diary.getTitle());
        holder.tv_diary_content.setText(diary.getContent());
        holder.tv_diary_time.setText(diary.getTime());
        //点击事件的注册
        if (null != mOnItemClickListener) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(holder.cardView, position);
                }
            });
        }
        //长按事件的注册
        if (null != mOnItemLongClickListener) {
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemLongClickListener.onLongClick(holder.cardView, position);
                    // setPosition(holder.getLayoutPosition()); // 重要！！！！！！
                    return true;
                }
            });
        }
    }

    /**用于告诉RecyclerView一共有多少个子项**/
    @Override
    public int getItemCount() {
        return mDiaryList.size();
    }

    /** 内部类 **/
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tv_diary_title;
        TextView tv_diary_content;
        TextView tv_diary_time;
        // 私密日记


        // 构造函数传入的参数view，通常是子项的最外层布局，然后通过findViewById()获得子项实例。
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            tv_diary_title = (TextView) view.findViewById(R.id.tv_diary_title);
            tv_diary_content = (TextView) view.findViewById(R.id.tv_diary_content);
            tv_diary_time = (TextView) view.findViewById(R.id.tv_diary_time);
        }
    }



    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    /** 删除单项 **/
    public void remove (int position) {
        mDiaryList.remove(position);
        notifyItemRemoved(position);
    }

    /** 在对应位置添加单项 **/
    public void add(Diary diary, int position) {
        mDiaryList.add(position, diary);
        notifyItemInserted(position);
    }

    /** 设置点击事件 **/
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /** 设置长按事件 **/
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    /** 点击事件接口 **/
    public interface OnItemClickListener {
        void onClick(View parent, int position);
    }

    /** 长按事件接口 **/
    public interface OnItemLongClickListener {
        boolean onLongClick(View parent, int position);
    }

    /** 实现Filterable接口，重写此方法 **/
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MyFilter(mDiaryList);
        }
        return filter;
    }

    /** 创建内部类MyFilter继承Filter类，并重写相关方法，实现数据过滤 **/
    class MyFilter extends Filter { // 可以轻松地访问外部类中的私有属性。（内部类的最大好处）

        // 创建集合保存原始数据
        private List<Diary> originalDiaryList = new ArrayList<>();

        public MyFilter(List<Diary> list) {
            this.originalDiaryList = list;
        }

        /** 该方法返回搜索过滤后的数据 **/
        @Override
        protected FilterResults performFiltering(CharSequence constraint) { // constraint 约束
            FilterResults results = new FilterResults();
            // 搜索内容为空的话，给results赋值原始数据的值和大小
            // 执行了搜索的话，根据搜索的规则过滤即可，最后把过滤后的数据的值和大小赋值给results
            if (TextUtils.isEmpty(constraint)) {
                results.values = originalDiaryList;
                results.count = originalDiaryList.size();
            } else {
                // 创建集合，保存过滤后的数据
                List<Diary> filterDiaryList = new ArrayList<>();
                // 遍历原始数据集合，根据搜索的规则过滤数据
                for (Diary diary : originalDiaryList) {
                    // 过滤规则的具体实现（可以自己决定规则）
                    if (diary.getTitle().trim().contains(constraint.toString().trim())
                            || diary.getContent().trim().contains(constraint.toString().trim())
                            || diary.getTime().trim().contains(constraint.toString().trim())){
                        // 规则匹配的话，向集合中添加数据
                        filterDiaryList.add(diary);
                    }
                }
                results.values = filterDiaryList;
                results.count = filterDiaryList.size();

            }

            return results;
        }

        /** 该方法用来刷新用户界面，根据过滤后的数据重新展示列表 **/
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // 获取过滤后的数据
            mDiaryList = (List<Diary>) results.values;
            // 如果接口对象不为空，调用接口中的方法获取过滤后的数据，
            // 具体的实现在new这个接口的时候重写的方法里执行
            if (filterListener != null) {
                filterListener.getFilterData(mDiaryList);
            }
            notifyDataSetChanged();
        }
    }
}
