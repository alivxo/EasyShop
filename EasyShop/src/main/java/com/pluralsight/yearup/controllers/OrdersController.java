package com.pluralsight.yearup.controllers;

import com.pluralsight.yearup.data.ProfileDao;
import com.pluralsight.yearup.data.ShoppingCartDao;
import com.pluralsight.yearup.data.UserDao;
import com.pluralsight.yearup.data.OrderDao;
import com.pluralsight.yearup.models.ShoppingCart;
import com.pluralsight.yearup.models.User;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("orders")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class OrdersController {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final ShoppingCartDao shoppingCartDao;

    public OrdersController(OrderDao orderDao, UserDao userDao, ProfileDao profileDao, ShoppingCartDao shoppingCartDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.shoppingCartDao = shoppingCartDao;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCart checkout(Principal principal) {
        try {
            int userId = getCurrentUserId(principal);
            Profile profile = getProfileByUserId(userId);
            ShoppingCart shoppingCart = getShoppingCartByUserId(userId);

            processOrder(userId, profile, shoppingCart);
            clearUserCart(userId);

            return getShoppingCartByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.", e);
        }
    }

    private int getCurrentUserId(Principal principal) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        return user.getId();
    }

    private Profile getProfileByUserId(int userId) {
        return profileDao.getProfileById(userId);
    }

    private ShoppingCart getShoppingCartByUserId(int userId) {
        return shoppingCartDao.getByUserId(userId);
    }

    private void processOrder(int userId, Profile profile, ShoppingCart shoppingCart) {
        orderDao.create(userId, profile, shoppingCart);
    }

    private void clearUserCart(int userId) {
        shoppingCartDao.deleteCart(userId);
    }
}
