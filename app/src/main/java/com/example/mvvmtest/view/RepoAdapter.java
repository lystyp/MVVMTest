package com.example.mvvmtest.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvvmtest.databinding.RepoItemBinding;
import com.example.mvvmtest.model.Repo;

import java.util.List;
import java.util.Objects;

public class RepoAdapter  extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private List<Repo> items;
    public TextView t;

    RepoAdapter(List<Repo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RepoAdapter.RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RepoItemBinding binding = RepoItemBinding.inflate(layoutInflater, parent, false);
        return new RepoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        Repo repo = items.get(position);
        Glide.with(holder.itemView.getContext())
                .load(repo.owner.avatarUrl)
                .into(holder.binding.ownerAvatar);
        holder.binding.desc.setText(repo.description);
        holder.binding.stars.setText(""+repo.stars);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    void clearItems() {
        int size = this.items.size();
        this.items.clear();
        notifyItemRangeRemoved(0, size);
    }

    void swapItems(List<Repo> newItems) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new RepoDiffCallback(this.items, newItems));
        this.items.clear();
        this.items.addAll(newItems);
        result.dispatchUpdatesTo(this);
    }

    private class RepoDiffCallback extends DiffUtil.Callback {

        private List<Repo> mOldList;
        private List<Repo> mNewList;

        RepoDiffCallback(List<Repo> oldList, List<Repo> newList) {
            this.mOldList = oldList;
            this.mNewList = newList;
        }

        @Override
        public int getOldListSize() {
            return mOldList != null ? mOldList.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return mNewList != null ? mNewList.size() : 0;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            int oldId = mOldList.get(oldItemPosition).id;
            int newId = mNewList.get(newItemPosition).id;
            return Objects.equals(oldId, newId);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Repo oldRepo = mOldList.get(oldItemPosition);
            Repo newRepo = mNewList.get(newItemPosition);
            return Objects.equals(oldRepo, newRepo);
        }
    }

    class RepoViewHolder extends RecyclerView.ViewHolder{

        private RepoItemBinding binding;

        RepoViewHolder(RepoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.name.setText("XXX");
        }
    }
}
