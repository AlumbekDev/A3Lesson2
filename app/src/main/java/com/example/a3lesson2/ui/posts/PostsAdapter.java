package com.example.a3lesson2.ui.posts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3lesson2.data.models.Post;
import com.example.a3lesson2.databinding.ItemPostsBinding;
import com.example.a3lesson2.utils.ItemOnClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {
    private HashMap<Integer, String> name = new HashMap<>();
    private List<Post> posts = new ArrayList<>();
    private ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPost(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostsBinding binding = ItemPostsBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public Post getPost(int position){
        return posts.get(position);
    }

    public void deleteItem(int position){
        posts.remove(position);
        notifyItemRemoved(position);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostsBinding binding;

        public PostViewHolder(@NonNull ItemPostsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOnClick.onClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemOnClick.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void onBind(Post post) {
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
        }
    }
}