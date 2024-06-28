package com.pluralsight.yearup.data.mysql;

import com.pluralsight.yearup.data.ShoppingCartDao;
import com.pluralsight.yearup.models.ShoppingCart;

import javax.sql.DataSource;

import com.pluralsight.yearup.models.ShoppingCartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlShoppingCartDao implements ShoppingCartDao {

    private final DataSource dataSource;

    public MySqlShoppingCartDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String query = "SELECT * FROM shopping_cart WHERE user_id = ?";
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        Map<Integer, ShoppingCartItem> items = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    ShoppingCartItem item = new ShoppingCartItem(productId, quantity);
                    items.put(productId, item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions properly in a real-world application
        }
        shoppingCart.setItems(items);
        return shoppingCart;
    }

    @Override
    public ShoppingCart updateItemInCart(int userId, int productId, int quantity) {
        String query = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions properly in a real-world application
        }
        return getByUserId(userId);
    }

    @Override
    public void addItemToCart(int userId, int productId) {
        String query = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, 1); // Default quantity to 1
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions properly in a real-world application
        }
    }

    @Override
    public void deleteCart(int userId) {
        String query = "DELETE FROM shopping_cart WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions properly in a real-world application
        }
    }

    @Override
    public void clearCartByUserId(int userId) {
        deleteCart(userId);
    }
}