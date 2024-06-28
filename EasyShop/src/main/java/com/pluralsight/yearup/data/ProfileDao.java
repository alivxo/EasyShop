package com.pluralsight.yearup.data;


import com.pluralsight.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    Profile getProfileById(int userId);

}
