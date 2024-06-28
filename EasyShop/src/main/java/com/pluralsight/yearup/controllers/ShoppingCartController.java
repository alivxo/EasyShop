package com.pluralsight.yearup.controllers;

import com.pluralsight.yearup.data.ProductDao;
import com.pluralsight.yearup.data.ShoppingCartDao;
import com.pluralsight.yearup.data.UserDao;
import com.pluralsight.yearup.models.ShoppingCart;
import com.pluralsight.yearup.models.ShoppingCartItem;
import com.pluralsight.yearup.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Map;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping ("/cart")
@CrossOrigin
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class ShoppingCartController
{
    // a shopping cart requires
    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PostMapping("products/{product_id}")
    public ShoppingCart addItemToCart(Principal principal, @PathVariable int product_id) {
        try
        {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            ShoppingCart shoppingCart = getCart(principal);

            for (Map.Entry<Integer, ShoppingCartItem> item : shoppingCart.getItems().entrySet()) {
                if (item.getValue().getProductId() == product_id) {
                    shoppingCartDao.updateItemInCart(userId, product_id);
                    return getCart(principal);
                }
            }

            shoppingCartDao.addItemToCart(userId, product_id);

        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        return getCart(principal);
    }

    @PutMapping("products/{product_id}")
    public int updateItemInCart(Principal principal, @PathVariable int product_id) {
        try
        {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return shoppingCartDao.updateItemInCart(userId, product_id);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @DeleteMapping("")
    public ShoppingCart deleteCart(Principal principal) {
        try
        {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            shoppingCartDao.deleteCart(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        return getCart(principal);
    }

}
