package com.pluralsight.yearup.data;

import com.pluralsight.yearup.models.Category;

public interface CategoryDao
{
    Category getAllCategories();
    Category getById(int categoryId);
    Category create(Category category);
    void update(int categoryId, Category category);
    void delete(int categoryId);
}
