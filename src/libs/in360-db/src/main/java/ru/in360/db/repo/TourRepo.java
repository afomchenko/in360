/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 25.02.18 0:59 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.in360.db.model.Tour;

import java.util.Optional;

@Repository
public interface TourRepo extends JpaRepository<Tour, Long> {

    Optional<Tour> findByName(String name);
}
