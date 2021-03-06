/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "level")
public class ImageLevelInfo {
    @XmlAttribute
    public Long tiledimagewidth;
    @XmlAttribute
    public Long tiledimageheight;
    @XmlElementRef(name = "cube", type = CubeImageElementInfo.class)
    public ImageElementInfo imageElement;
}
