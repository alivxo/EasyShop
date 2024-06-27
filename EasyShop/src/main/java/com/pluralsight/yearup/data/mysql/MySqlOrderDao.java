package com.pluralsight.yearup.data.mysql;

import javax.sql.DataSource;

public class MySqlOrderDao extends OrderDao {
    public MySqlOrderDao(DataSource dataSource) {
        super();
    }
}
