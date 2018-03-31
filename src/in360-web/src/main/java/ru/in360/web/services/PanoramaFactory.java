/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.web.services;

import org.springframework.stereotype.Service;
import ru.in360.db.model.Image;
import ru.in360.db.model.ImageLevel;
import ru.in360.db.model.Panorama;
import ru.in360.tour.model.CubeImageElementInfo;
import ru.in360.tour.model.ImageInfo;
import ru.in360.tour.model.ImageLevelInfo;
import ru.in360.tour.model.PreviewInfo;
import ru.in360.tour.model.SceneInfo;
import ru.in360.tour.model.ViewInfo;
import ru.in360.tour.model.constants.ImageType;
import ru.in360.tour.model.constants.LimitView;

@Service
public class PanoramaFactory {

    public PanoramaFactory() {
    }

    public SceneInfo asSceneInfo(Panorama panorama) {
        SceneInfo scene = new SceneInfo();
        scene.heading = panorama.getHeading();
        scene.lat = panorama.getLat();
        scene.lng = panorama.getLng();
        if (panorama.getPreviewUrl() != null) {
            scene.preview = new PreviewInfo();
            scene.preview.url = panorama.getPreviewUrl();
        }
        scene.thumburl = panorama.getThumbUrl();
        scene.name = panorama.getName();
        scene.title = panorama.getTitle();
        scene.image = asImageInfo(panorama.getImage());
        scene.view = new ViewInfo();
        scene.view.setFov(panorama.getFov());
        scene.view.setHlookat(panorama.getHlookat());
        scene.view.setVlookat(panorama.getVlookat());
        scene.view.setLimitview(LimitView.fromValue(panorama.getLimitview()));
        return scene;
    }

    public Panorama asEmptyPanorama(SceneInfo sceneInfo) {
        Panorama panorama = new Panorama();
        panorama.setHeading(sceneInfo.heading);
        panorama.setLat(sceneInfo.lat);
        panorama.setLng(sceneInfo.lng);
        panorama.setPreviewUrl(sceneInfo.preview.url);
        panorama.setThumbUrl(sceneInfo.thumburl);
        panorama.setName(sceneInfo.name);
        panorama.setTitle(sceneInfo.title);
        panorama.setFov(sceneInfo.view.getFov());
        panorama.setHlookat(sceneInfo.view.getHlookat());
        panorama.setVlookat(sceneInfo.view.getVlookat());
        panorama.setLimitview(sceneInfo.view.getLimitview().value());
        return panorama;
    }

    private ImageInfo asImageInfo(Image panoramaImage) {
        ImageInfo image = new ImageInfo();
        image.type = ImageType.fromValue(panoramaImage.getType());
        image.multires = panoramaImage.getMultires();
        image.tilesize = panoramaImage.getTilesize();
        image.progressive = panoramaImage.getProgressive();
        panoramaImage.getMobileLevels().forEach(imageLevel -> image.addMobileLevel(asImageLevelInfo(imageLevel)));
        panoramaImage.getMultiresLevels().forEach(imageLevel -> image.addLevel(asImageLevelInfo(imageLevel)));
        return image;
    }

    public Image asEmptyPanoramaImage(ImageInfo imageInfo) {
        Image image = new Image();
        image.setType(imageInfo.type.value());
        image.setMultires(imageInfo.multires);
        image.setTilesize(imageInfo.tilesize);
        image.setProgressive(imageInfo.progressive);
        return image;
    }

    private ImageLevelInfo asImageLevelInfo(ImageLevel panoramaImageLevel) {
        ImageLevelInfo imageLevel = new ImageLevelInfo();
        imageLevel.tiledimageheight = panoramaImageLevel.getHeight();
        imageLevel.tiledimagewidth = panoramaImageLevel.getWidth();
        imageLevel.imageElement = new CubeImageElementInfo();
        imageLevel.imageElement.url = panoramaImageLevel.getUrl();
        return imageLevel;
    }

    public ImageLevel asPanoramaImageLevel(ImageLevelInfo imageLevelInfo) {
        ImageLevel imageLevel = new ImageLevel();
        imageLevel.setHeight(imageLevelInfo.tiledimageheight);
        imageLevel.setWidth(imageLevelInfo.tiledimagewidth);
        imageLevel.setType(ImageType.CUBE.value());
        imageLevel.setUrl(imageLevelInfo.imageElement.url);
        return imageLevel;
    }
}
