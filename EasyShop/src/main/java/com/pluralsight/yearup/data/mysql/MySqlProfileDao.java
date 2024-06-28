package com.pluralsight.yearup.data.mysql;

import com.pluralsight.yearup.data.ProfileDao;
import com.pluralsight.yearup.models.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

import static com.pluralsight.yearup.data.mysql.MySqlProductDao.mapRow;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            ps.executeUpdate();

            return profile;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile getProfileById(int userId) {
        String query = "SELECT * FROM profiles WHERE user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
