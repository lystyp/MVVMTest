package com.example.mvvmtest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepoSearchResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("items")
    private List<Repo> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }
}
