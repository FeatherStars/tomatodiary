package com.example.t.thisisdiary.main;

import com.example.t.thisisdiary.bean.Diary;

import java.util.List;

public interface FilterListener {
    void getFilterData(List<Diary> list); // 获取过滤后的数据
}
