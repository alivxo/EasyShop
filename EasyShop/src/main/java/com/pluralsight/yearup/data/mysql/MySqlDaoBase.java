package com.pluralsight.yearup.data.mysql;

import com.pluralsight.yearup.models.Profile;
import com.pluralsight.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class MySqlDaoBase
{
    private DataSource dataSource;

    public MySqlDaoBase(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public abstract int updateProfile(int userId, Profile profile);

    public abstract void create(int userId, Profile profile, ShoppingCart shoppingCart);

    public abstract void create(int userId, Profile profile, ShoppingCart shoppingCart);
}
