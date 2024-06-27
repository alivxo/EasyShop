package com.pluralsight.yearup.controllers;


import com.pluralsight.yearup.data.ProfileDao;
import com.pluralsight.yearup.data.UserDao;
import com.pluralsight.yearup.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class UserProfileController {

    private final ProfileDao profileDao;
    private final UserDao userDao;

    @Autowired
    public UserProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @GetMapping("")
    public com.pluralsight.yearup.models.Profile getProfile(Principal principal) {
        try {
            // Get the currently logged-in username
            String userName = principal.getName();
            // Find database user by username
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return profileDao.getProfileById(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping("")
    public int updateProfile(Principal principal, @RequestBody Profile profile) {
        try {
            // Get the currently logged-in username
            String userName = principal.getName();
            // Find database user by username
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return profileDao.updateProfile(userId, profile);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

}
