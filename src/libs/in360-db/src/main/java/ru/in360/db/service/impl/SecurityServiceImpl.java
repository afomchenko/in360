/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 19.03.18 0:31 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.in360.db.model.security.Role;
import ru.in360.db.model.security.User;
import ru.in360.db.repo.RoleRepo;
import ru.in360.db.repo.UserRepo;
import ru.in360.db.service.SecurityService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findUser(String name) {
        return userRepo.findUserByUserName(name);
    }

    @Override
    public Optional<Role> findRole(String authority) {
        return roleRepo.findRoleByAuthority(authority);
    }

    @Override
    public User newUser(String name, CharSequence password, Role... roles) {
        User user = new User();
        user.setUserName(name);
        user.setPassword(password, passwordEncoder);
        user.setAuthorities(new HashSet<>(Arrays.asList(roles)));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return userRepo.save(user);
    }

    @Override
    public Role newRole(String authority) {
        Role role = new Role();
        role.setAuthority(authority);
        roleRepo.save(role);
        roleRepo.flush();
        return role;
    }
}
