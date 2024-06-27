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

// Convert this class to a REST controller
@RestController
// Only logged in users should have access to these actions
@RequestMapping("cart")
@CrossOrigin
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class ShoppingCartController {

    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // Each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    public ShoppingCart getCart(Principal principal) {
        try {
            // Get the currently logged in username
            String userName = principal.getName();
            // Find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // Use the shoppingCartDao to get all items in the cart and return the cart
            return shoppingCartDao.getByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // Add a POST method to add a product to the cart - the URL should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added)
    @PostMapping("products/{product_id}")
    public ShoppingCart addItemToCart(Principal principal, @PathVariable int product_id, ShoppingCartItem shoppingCartItem) {
        try {
            // Get the currently logged-in username
            String userName = principal.getName();
            // Find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            ShoppingCart shoppingCart = getCart(principal);

            // Check if the item already exists in the cart and update if it does
            for (Map.Entry<Integer, ShoppingCartItem> item : shoppingCart.getItems().entrySet()) {
                if (item.getValue().getProductId() == product_id) {
                    shoppingCartDao.updateItemInCart(userId, product_id, shoppingCartItem.getQuantity());
                    return getCart(principal);
                }
            }

            // Add the item to the cart
            shoppingCartDao.addItemToCart(userId, product_id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        return getCart(principal);
    }

    // Add a PUT method to update an existing product in the cart - the URL should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // The BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("products/{product_id}")
    public ShoppingCart updateItemInCart(Principal principal, @PathVariable int product_id, @RequestBody ShoppingCartItem shoppingCartItem) {
        try {
            // Get the currently logged-in username
            String userName = principal.getName();
            // Find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // Update the quantity of the item in the cart
            shoppingCartDao.updateItemInCart(userId, product_id, shoppingCartItem.getQuantity());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        return getCart(principal);
    }

    // Add a DELETE method to clear all products from the current user's cart
    // https://localhost:8080/cart
    @DeleteMapping("")
    public void clearCart(Principal principal) {
        try {
            // Get the currently logged-in username
            String userName = principal.getName();
            // Find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // Clear the cart for the current user
            shoppingCartDao.clearCartByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
