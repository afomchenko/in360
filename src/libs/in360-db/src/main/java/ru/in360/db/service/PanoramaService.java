/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.in360.db.model.Panorama;
import ru.in360.db.model.Tour;

import java.util.List;
import java.util.Optional;

public interface PanoramaService {

    Page<Panorama> getPanoramas(Pageable pageable);

    List<Panorama> findPanoramasByTour(Tour tour);

    Optional<Panorama> findPanoramaById(long id);

    Optional<Panorama> findPanoramaByName(String name);

    Optional<Tour> findTourByName(String name);

    Panorama newPanorama(String sourceImagePath);

    Panorama savePanorama(Panorama panorama);

    Tour createTour(String name, List<Panorama> panoramas);
}
