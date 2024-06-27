package com.pluralsight.yearup.data;

import com.pluralsight.yearup.models.User;

import org.springframework.context.annotation.Primary;

import java.util.List;

public interface UserDao {


    List<User> getAll();

    User getUserById(int userId);

    User getByUserName(String username);

    int getIdByUsername(String username);

    User create(User user);

    boolean exists(String username);
}