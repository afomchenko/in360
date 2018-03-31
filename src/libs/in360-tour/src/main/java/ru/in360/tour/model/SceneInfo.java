/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model;

import ru.in360.tour.model.adapters.ActionAsAttributeAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "scene")
public class SceneInfo extends TourElement {

    @XmlAttribute
    public String title;
    @XmlAttribute(name = "onstart")
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onstart;
    @XmlAttribute
    public String thumburl;
    @XmlAttribute
    public Double lat;
    @XmlAttribute
    public Double lng;
    @XmlAttribute
    public Double heading;
    @XmlAttribute
    public Double headingOffset;
    @XmlElement
    public ViewInfo view;
    @XmlElement
    public PreviewInfo preview;
    @XmlElement
    public ImageInfo image;

    public SceneInfo(long id) {
        this.name = "scene_" + id;
    }

    public SceneInfo() {
    }
}
