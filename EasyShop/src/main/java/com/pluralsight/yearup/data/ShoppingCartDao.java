package com.pluralsight.yearup.data;

import com.pluralsight.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
}
