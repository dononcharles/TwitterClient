package com.chaldrac.twitterclient.hashtag.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chaldrac.twitterclient.R;
import com.chaldrac.twitterclient.db.HashsTag;

import java.util.ArrayList;
import java.util.List;

public class HashtagsAdapter extends RecyclerView.Adapter<HashtagsAdapter.ViewHolder> {

    private List<HashsTag> dataset;
    private OnItemClickListener clickListener;

    public HashtagsAdapter(List<HashsTag> dataset, OnItemClickListener clickListener) {
        this.dataset = dataset;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public HashtagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_hashtag, parent, false);
        return new ViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagsAdapter.ViewHolder holder, int position) {
        HashsTag tweet = dataset.get(position);
        holder.setOnClickListener(tweet, clickListener);
        holder.textView.setText(tweet.tweetText);
        holder.setItems(tweet.hashtags);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(ArrayList<HashsTag> newItem){
        dataset.addAll(newItem);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        @BindView(R.id.txtTweet)
        TextView textView;
        @BindView(R.id.recyclerViewH) RecyclerView recyclerView;

        private HashtagListAdapter hashtagListAdapter;
        private ArrayList<String> items;

        ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;

            items = new ArrayList<String>();
            hashtagListAdapter = new HashtagListAdapter(items);

            CustomGridLayoutManager layoutManager = new CustomGridLayoutManager(context, 3);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(hashtagListAdapter);
        }

        void setItems(List<String> newItem){
            items.clear();
            items.addAll(newItem);
            hashtagListAdapter.notifyDataSetChanged();
        }

        void setOnClickListener(final HashsTag hashsTag, final OnItemClickListener clickListener){
            view.setOnClickListener(v -> {
                clickListener.onClickItem(hashsTag);
            });
        }

    }
}
