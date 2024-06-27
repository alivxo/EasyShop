package com.pluralsight.yearup.data.mysql;

import com.pluralsight.yearup.data.OrderDao;
import com.pluralsight.yearup.models.Profile;
import com.pluralsight.yearup.models.ShoppingCart;
import com.pluralsight.yearup.models.ShoppingCartItem;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public void create(int userId, org.springframework.context.annotation.Profile profile, ShoppingCart shoppingCart) {
        String orderInsertSql = "INSERT INTO orders (user_id, date, address, city, state, zip, shipping_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String orderItemLineInsertSql = "INSERT INTO order_line_items (order_id, product_id, sales_price, quantity, discount) VALUES (?, ?, ?, ?, ?)";
        Date date = Date.valueOf(LocalDate.now());

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            int orderId = insertOrder(connection, userId, (Profile) profile, date, orderInsertSql);
            insertOrderLineItems(connection, orderId, shoppingCart, orderItemLineInsertSql);

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create order", e);
        }
    }

    private int insertOrder(Connection connection, int userId, Profile profile, Date date, String orderInsertSql) throws SQLException {
        try (PreparedStatement orderPs = connection.prepareStatement(orderInsertSql, Statement.RETURN_GENERATED_KEYS)) {
            orderPs.setInt(1, userId);
            orderPs.setDate(2, date);
            orderPs.setString(3, profile.getAddress());
            orderPs.setString(4, profile.getCity());
            orderPs.setString(5, profile.getState());
            orderPs.setString(6, profile.getZip());
            orderPs.setDouble(7, 5.00);

            orderPs.executeUpdate();

            try (ResultSet generatedKeys = orderPs.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    private void insertOrderLineItems(Connection connection, int orderId, ShoppingCart shoppingCart, String orderItemLineInsertSql) throws SQLException {
        try (PreparedStatement orderItemLinePs = connection.prepareStatement(orderItemLineInsertSql)) {
            for (Map.Entry<Integer, ShoppingCartItem> entry : shoppingCart.getItems().entrySet()) {
                ShoppingCartItem item = entry.getValue();
                orderItemLinePs.setInt(1, orderId);
                orderItemLinePs.setInt(2, item.getProductId());
                orderItemLinePs.setBigDecimal(3, item.getLineTotal());
                orderItemLinePs.setInt(4, item.getQuantity());
                orderItemLinePs.setBigDecimal(5, item.getDiscountPercent());

                orderItemLinePs.addBatch();
            }
            orderItemLinePs.executeBatch();
        }
    }
}



