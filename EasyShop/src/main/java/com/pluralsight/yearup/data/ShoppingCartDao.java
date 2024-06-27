package com.pluralsight.yearup.data;

import com.pluralsight.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    ShoppingCart updateItemInCart(int userId, int productId, int quantity);

    void addItemToCart(int userId, int productId);

    void deleteCart(int userId);

    void clearCartByUserId(int userId);
    // add additional method signatures here
}
