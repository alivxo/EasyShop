package com.pluralsight.yearup.data;

import com.pluralsight.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    ShoppingCart updateItemInCart(int userId, int productId, int quantity);

    void addItemToCart(int userId, int product_id);
    int updateItemInCart(int userId, int product_id);
    void deleteCart(int userId);
}
