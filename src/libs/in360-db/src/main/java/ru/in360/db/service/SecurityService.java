/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 19.03.18 0:30 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.service;

import ru.in360.db.model.security.User;

import java.util.Optional;

public interface SecurityService {

    Optional<User> findUser(String name);
}
