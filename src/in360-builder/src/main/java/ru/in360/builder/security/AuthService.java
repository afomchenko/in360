/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 19.03.18 0:12 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.in360.db.model.security.User;
import ru.in360.db.service.SecurityService;

@Service("authService")
public class AuthService implements UserDetailsService {

    @Autowired
    SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = securityService.findUser(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        return user;
    }
}