package com.newbiechen.usenightmode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
    private OnItemClickListener mListener;
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
    public void onBindViewHolder(ArticleBriefViewHolder holder, final int position) {
        //获取Article
        ArticleBrief articleBrief = getItem(position);
/*        //设置夜间模式
        if(MyApplication.isNightMode){
            holder.rlBrief.setBackground(mContext.getResources().
                    getDrawable(R.drawable.night_article_brief_bg));
        }
        else {
            holder.rlBrief.setBackground(mContext.getResources().
                    getDrawable(R.drawable.day_article_brief_bg));
        }*/

        //初始化数据
        holder.tvTitle.setText(articleBrief.getTitle());
        holder.tvAuthor.setText(articleBrief.getDate());
        holder.tvDate.setText(articleBrief.getDate());

        //设置监听器
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onItemClick(position);
                }
            }
        });
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

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    /*****************************公共方法**************************************/

    public void addItems(List<ArticleBrief> articleBriefList){
        mArticleBriefItems.addAll(articleBriefList);
        notifyDataSetChanged();
    }

    public ArticleBrief getItem(int position){
        return mArticleBriefItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}


