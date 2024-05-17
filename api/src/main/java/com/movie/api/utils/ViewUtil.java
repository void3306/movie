package com.movie.api.utils;

import com.movie.api.model.entity.User;

public class ViewUtil {
    public static User setUserToView(User user){
        user.setId(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setBirthday(null);
        user.setBirthday(null);
        user.setGender(null);
        user.setCreateAt(null);
        user.setUpdateAt(null);
        return user;
    }
}
