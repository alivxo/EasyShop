package com.pluralsight.yearup.data.mysql;

import com.pluralsight.yearup.data.ShoppingCartDao;
import com.pluralsight.yearup.models.ShoppingCart;

import javax.sql.DataSource;

public class MySqlShoppingCartDao implements ShoppingCartDao {
    public MySqlShoppingCartDao(DataSource dataSource) {
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        return null;
    }

    @Override
    public ShoppingCart updateItemInCart(int userId, int productId, int quantity) {
        return null;
    }

    @Override
    public void addItemToCart(int userId, int productId) {

    }

    @Override
    public void deleteCart(int userId) {

    }

    @Override
    public void clearCartByUserId(int userId) {

    }
}
