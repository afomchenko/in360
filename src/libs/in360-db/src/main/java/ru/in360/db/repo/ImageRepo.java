/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.in360.db.model.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
}