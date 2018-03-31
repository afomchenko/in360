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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "krpano")
public class TourInfo {
    @XmlAttribute
    public String title;
    @XmlAttribute
    public String version;
    @XmlAttribute(name = "onstart")
    @XmlJavaTypeAdapter(ActionAsAttributeAdapter.class)
    public ActionInfo onstart;


    @XmlElementRefs({
            @XmlElementRef(name = "include", type = IncludeInfo.class),
            @XmlElementRef(name = "skin_settings", type = SkinSettingsInfo.class),
            @XmlElementRef(name = "action", type = ActionInfo.class),
            @XmlElementRef(name = "layer", type = LayerInfo.class),
            @XmlElementRef(name = "scene", type = SceneInfo.class)
    })
    @XmlMixed
    public List<TourElement> elements = new ArrayList<>();

    public void addTourElement(TourElement element) {
        elements.add(element);
    }
}
