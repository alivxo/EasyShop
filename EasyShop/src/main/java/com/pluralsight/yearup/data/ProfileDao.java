package com.pluralsight.yearup.data;


import com.pluralsight.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    org.springframework.context.annotation.Profile getProfileById(int userId);

    int updateProfile(int userId, org.springframework.context.annotation.Profile profile);
}
