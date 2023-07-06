package com.example.newsupdate;

import android.net.Uri;

public class CategoryRVModal {
    private String category;
    private String categoryImageUrl;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public Uri setCategoryImageUrl() {
        this.categoryImageUrl = categoryImageUrl;
        return null;
    }

    public CategoryRVModal(String category, String categoryImageUrl) {
        this.category = category;
        this.categoryImageUrl = categoryImageUrl;
    }
}
