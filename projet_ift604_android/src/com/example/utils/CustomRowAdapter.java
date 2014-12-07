package com.example.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.entity.Article;
import com.example.projet_ift604_android.R;

public class CustomRowAdapter extends ArrayAdapter<Article> {

    private Context context; 
    private int layoutResourceId;    
    private Article articles[] = null;
    
    public CustomRowAdapter(Context context, int layoutResourceId, Article[] articles) {
        super(context, layoutResourceId, articles);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.articles = articles;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ArticleHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ArticleHolder();
            holder.dateAndUser = (TextView) row.findViewById(R.id.item_date_user);
            holder.title = (TextView) row.findViewById(R.id.item_title);
            holder.content = (TextView) row.findViewById(R.id.item_content);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ArticleHolder) row.getTag();
        }
        
        if (position % 2 == 0)
            row.setBackgroundResource(R.color.lightGreen);
        else
            row.setBackgroundResource(R.color.lightPink);
        
        Article article = articles[position];
        holder.dateAndUser.setText("Posted on " + article.getCreated() + " by " + article.getUser().getDisplayName());
        holder.title.setText(article.getTitle());
        holder.content.setText(article.getContent());
        
        return row;
    }
    
    static class ArticleHolder
    {
        TextView dateAndUser;
        TextView title;
        TextView content;
    }

}
