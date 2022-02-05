package com.test.medicarehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.medicarehealth.R;
import com.test.medicarehealth.model.Article;
import com.test.medicarehealth.util.DateTimeUtil;

public class ArticleListAdapter extends ListAdapter<Article,ArticleListAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ArticleListAdapter() {
        super(diffCallback);
    }

    private static DiffUtil.ItemCallback<Article> diffCallback = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText("Title: " +getItem(position).getTitle());
        holder.newsSite.setText("News site: "+getItem(position).getNewsSite());
        holder.publishOn.setText("Publish At: "+DateTimeUtil.getDateTimeFrom(getItem(position).getPublishedAt()));
        Glide.with(context).load(getItem(position).getImageUrl()).into(holder.itemImage);
        //holder.
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,newsSite,publishOn;
        private ImageView itemImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTv);
            newsSite =itemView.findViewById(R.id.newsSiteName);
            publishOn = itemView.findViewById(R.id.publishTv);
            itemImage = itemView.findViewById(R.id.itemIV);

            itemView.setOnClickListener(view -> {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Article item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
