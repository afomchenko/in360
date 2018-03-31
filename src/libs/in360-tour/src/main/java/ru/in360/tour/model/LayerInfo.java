/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model;

import ru.in360.tour.model.adapters.ActionAsAttributeAdapter;
import ru.in360.tour.model.adapters.StyleAsAttributeAdapter;
import ru.in360.tour.model.constants.Align;
import ru.in360.tour.model.constants.LayerType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "layer")
public class LayerInfo extends TourElement {
    @XmlAttribute
    public Boolean keep;
    @XmlAttribute
    public LayerType type = LayerType.IMAGE;
    @XmlAttribute
    public Align align;
    @XmlAttribute
    public String width;
    @XmlAttribute
    public String height;
    @XmlAttribute
    public Integer x;
    @XmlAttribute
    public Integer y;
    @XmlAttribute
    public Integer y_opened;
    @XmlAttribute
    public Integer y_closed;
    @XmlAttribute
    public Integer x_opened;
    @XmlAttribute
    public Integer x_closed;
    @XmlAttribute
    public Boolean maskchildren;
    @XmlAttribute
    @XmlJavaTypeAdapter(StyleAsAttributeAdapter.class)
    public StyleInfo style;
    @XmlAttribute
    public String layer;
    @XmlAttribute
    public Boolean visible;
    @XmlAttribute
    public Double alpha;
    @XmlAttribute
    public String backgroundalpha;
    @XmlAttribute
    public Boolean background;
    @XmlAttribute
    public String backgroundcolor;
    @XmlAttribute
    public String url;
    @XmlAttribute
    public Double scale;

    @XmlAttribute
    public String alturl;
    @XmlAttribute
    public String devices;
    @XmlAttribute
    public String parent;
    @XmlAttribute
    public Boolean enabled;
    @XmlAttribute
    public Boolean handcursor;
    @XmlAttribute
    public Boolean scalechildren;
    @XmlAttribute
    public Integer zorder;
    @XmlAttribute
    public Align edge;
    @XmlAttribute
    public Double rotate;

    @XmlAttribute
    public String crop;
    @XmlAttribute
    public String onovercrop;
    @XmlAttribute
    public String ondowncrop;

    @XmlAttribute
    public String bgcolor;
    @XmlAttribute
    public Double bgalpha;

    @XmlAttribute
    public String bgborder;
    @XmlAttribute
    public String bgroundedge;
    @XmlAttribute
    public String bgshadow;
    @XmlAttribute
    public Boolean bgcapture;

    @XmlAttribute
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onover;
    @XmlAttribute
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onhover;
    @XmlAttribute
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onout;
    @XmlAttribute
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onclick;
    @XmlAttribute
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo ondown;
    @XmlAttribute
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onup;
    @XmlAttribute
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onloaded;
}
