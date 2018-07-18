package com.pawelmajda.chatapp.utils

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pawelmajda.chatapp.utils.ViewType

interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}