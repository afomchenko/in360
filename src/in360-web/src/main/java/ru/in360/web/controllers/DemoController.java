/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.in360.db.model.Image;
import ru.in360.db.model.ImageLevel;
import ru.in360.db.model.Panorama;
import ru.in360.db.service.PanoramaService;
import ru.in360.tour.model.ImageInfo;
import ru.in360.tour.model.SceneInfo;
import ru.in360.tour.model.TourInfo;
import ru.in360.web.services.PanoramaFactory;
import ru.in360.web.services.TourFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/web/demo")
public class DemoController {

    private final PanoramaService panoramaService;
    private final PanoramaFactory panoramaFactory;

    @Autowired
    public DemoController(PanoramaService panoramaService, PanoramaFactory panoramaFactory) {
        this.panoramaService = panoramaService;
        this.panoramaFactory = panoramaFactory;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("hello in360");
    }


    @RequestMapping(path = "/tour", method = RequestMethod.GET, produces = MediaType.TEXT_XML_VALUE)
    @Transactional(readOnly = true)
    public TourInfo demoPano() {
        TourInfo outTourInfo = new TourFactory().createDefaultTourEnvironment();
        panoramaService.findPanoramasByTour(panoramaService.findTourByName("new tour").get()).forEach(e -> outTourInfo.addTourElement(panoramaFactory.asSceneInfo(e)));
        return outTourInfo;
    }

    @Transactional
    @RequestMapping(path = "/fromxml", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<TourInfo> panoFromXml(@RequestBody TourInfo tourInfo) {

        TourInfo outTourInfo = new TourFactory().createDefaultTourEnvironment();
        List<Panorama> panoramas = new ArrayList<>();
        for (Object element : tourInfo.elements) {
            if (element instanceof SceneInfo) {
                Panorama panorama = panoramaFactory.asEmptyPanorama((SceneInfo) element);
                ImageInfo imageInfo = ((SceneInfo) element).image;
                Image image = panoramaFactory.asEmptyPanoramaImage(imageInfo);
                List<ImageLevel> imageLevels = imageInfo.getLevels().stream().map(panoramaFactory::asPanoramaImageLevel).peek(e -> e.setImage(image)).collect(Collectors.toList());
                List<ImageLevel> mobileImageLevels = imageInfo.getMobileLevels()
                        .stream()
                        .map(panoramaFactory::asPanoramaImageLevel)
                        .peek(e -> e.setImageMobile(image))
                        .collect(Collectors.toList());
                panorama.setImage(image);
                image.setMobileLevels(mobileImageLevels);
                image.setMultiresLevels(imageLevels);

                panoramaService.savePanorama(panorama);
                panoramas.add(panorama);
                outTourInfo.addTourElement(panoramaFactory.asSceneInfo(panorama));
            }
        }
        panoramaService.createTour("new tour", panoramas);
        return ResponseEntity.ok(outTourInfo);
    }
}
