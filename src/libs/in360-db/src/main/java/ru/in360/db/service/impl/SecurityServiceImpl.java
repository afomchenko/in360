/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 19.03.18 0:31 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.in360.db.model.security.User;
import ru.in360.db.repo.UserRepo;
import ru.in360.db.service.SecurityService;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepo userRepo;

    @Autowired
    public SecurityServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<User> findUser(String name) {
        return userRepo.findUserByUserName(name);
    }
}
