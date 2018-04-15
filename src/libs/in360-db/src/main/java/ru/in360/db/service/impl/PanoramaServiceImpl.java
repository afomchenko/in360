/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.in360.db.model.Image;
import ru.in360.db.model.Panorama;
import ru.in360.db.model.Tour;
import ru.in360.db.repo.ImageLevelRepo;
import ru.in360.db.repo.ImageRepo;
import ru.in360.db.repo.PanoramaRepo;
import ru.in360.db.repo.TourRepo;
import ru.in360.db.service.PanoramaService;

import java.util.List;
import java.util.Optional;

@Service
public class PanoramaServiceImpl implements PanoramaService {

    private final ImageRepo imageRepo;
    private final PanoramaRepo panoramaRepo;
    private final ImageLevelRepo imageLevelRepo;
    private final TourRepo tourRepo;

    @Autowired
    public PanoramaServiceImpl(ImageRepo imageRepo, PanoramaRepo panoramaRepo, ImageLevelRepo imageLevelRepo, TourRepo tourRepo) {
        this.imageRepo = imageRepo;
        this.panoramaRepo = panoramaRepo;
        this.imageLevelRepo = imageLevelRepo;
        this.tourRepo = tourRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Panorama> getPanoramas(Pageable pageable) {
        return Optional.ofNullable(pageable).map(panoramaRepo::findAll)
                .orElseThrow(() -> new IllegalArgumentException("page not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Panorama> findPanoramasByTour(Tour tour) {
        return panoramaRepo.findPanoramasByTours(tour);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Panorama> findPanoramaById(long id) {
        return panoramaRepo.findPanoramaById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Panorama> findPanoramaByName(String name) {
        return panoramaRepo.findPanoramaByName(name);
    }

    @Override
    public Optional<Tour> findTourByName(String name) {
        return tourRepo.findByName(name);
    }

    @Override
    @Transactional
    public Panorama newPanorama(String sourceImagePath) {
        Panorama panorama = new Panorama();
        Image image = new Image();
        image.setSourceImagePath(sourceImagePath);
        imageRepo.save(image);
        panorama.setImage(image);
        return savePanorama(panorama);
    }

    @Override
    @Transactional
    public Panorama savePanorama(Panorama panorama) {
        return panoramaRepo.save(panorama);
    }

    @Override
    public Tour createTour(String name, List<Panorama> panoramas) {
        Tour tour = new Tour(name);
        tour.setPanoramas(panoramas);
        return tourRepo.save(tour);
    }
}
