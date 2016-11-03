package com.newbiechen.usenightmode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newbiechen.usenightmode.entity.ArticleBrief;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;
/**
 * Created by PC on 2016/11/3.
 */

public class ArticleBriefAdapter extends Adapter<ArticleBriefAdapter.ArticleBriefViewHolder> {

    private final List<ArticleBrief> mArticleBriefItems = new ArrayList<>();

    private Context mContext;
    public ArticleBriefAdapter(Context context) {
        mContext = context;
    }


    @Override
    public ArticleBriefViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_article_brief,parent,false);
        return new ArticleBriefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleBriefViewHolder holder, int position) {
        ArticleBrief articleBrief = mArticleBriefItems.get(position);
        holder.tvTitle.setText(articleBrief.getTitle());
        holder.tvAuthor.setText(articleBrief.getDate());
        holder.tvDate.setText(articleBrief.getDate());
    }

    @Override
    public int getItemCount() {
        return mArticleBriefItems.size();
    }

    public class ArticleBriefViewHolder extends ViewHolder{
        RelativeLayout rlBrief;
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvDate;
        public ArticleBriefViewHolder(View itemView) {
            super(itemView);
            rlBrief = getViewById(itemView,R.id.article_brief_rl);
            tvTitle = getViewById(itemView,R.id.article_brief_tv_title);
            tvAuthor = getViewById(itemView,R.id.article_brief_tv_author);
            tvDate = getViewById(itemView,R.id.article_brief_tv_date);
        }

        public <T> T getViewById(View view,int id){
            return (T) view.findViewById(id);
        }
    }

    /*****************************公共方法**************************************/

    public void addItems(List<ArticleBrief> articleBriefList){
        mArticleBriefItems.addAll(articleBriefList);
        notifyDataSetChanged();
    }
}


