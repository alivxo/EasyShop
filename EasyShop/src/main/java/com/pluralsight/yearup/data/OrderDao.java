package com.pluralsight.yearup.data;

import com.pluralsight.yearup.models.ShoppingCart;
import org.springframework.context.annotation.Profile;

public interface OrderDao {
   void create(int userId, Profile profile, ShoppingCart shoppingCart);
}
