/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.in360.db.model.Panorama;
import ru.in360.db.model.Tour;

import java.util.List;
import java.util.Optional;

@Repository
public interface PanoramaRepo extends JpaRepository<Panorama, Long> {
    Page<Panorama> findAll(Pageable pageable);

    List<Panorama> findPanoramasByTours(Tour tour);

    Optional<Panorama> findPanoramaById(long id);

    Optional<Panorama> findPanoramaByName(String name);
}
