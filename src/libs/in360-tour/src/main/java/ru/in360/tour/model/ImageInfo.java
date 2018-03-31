/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model;


import ru.in360.tour.model.constants.ImageType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "image")
public class ImageInfo {
    @XmlAttribute
    public ImageType type;
    @XmlAttribute
    public String hfov;
    @XmlAttribute
    public String vfov;
    @XmlAttribute
    public Double voffset;
    @XmlAttribute
    public Boolean multires;
    @XmlAttribute
    public Double multiresthreshold;
    @XmlAttribute
    public Boolean progressive;
    @XmlAttribute
    public Long tilesize;
    @XmlAttribute
    public Long baseindex;
    @XmlAttribute
    public Long frames;
    @XmlAttribute
    public Long frame;
    @XmlAttribute
    public String prealign;
    @XmlElement(name = "level")
    List<ImageLevelInfo> levels = new ArrayList<>();
    @XmlElement(name = "mobile")
    List<ImageLevelInfo> mobileLevels = new ArrayList<>();

    public void addLevel(ImageLevelInfo level) {
        levels.add(level);
    }

    public void addMobileLevel(ImageLevelInfo mobileLevel) {
        mobileLevels.add(mobileLevel);
    }

    public List<ImageLevelInfo> getLevels() {
        return levels;
    }

    public List<ImageLevelInfo> getMobileLevels() {
        return mobileLevels;
    }
}
