package com.example.myapplication.controller;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperListener {
    public void onSwiped(RecyclerView.ViewHolder viewHolder);
}
