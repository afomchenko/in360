
/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "style")
public class StyleInfo extends TourElement {

    @XmlAnyAttribute
    public Map<QName, String> contents = new HashMap<>();

    public StyleInfo() {
    }

    public StyleInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addElement(String name, String element) {
        contents.put(QName.valueOf(name), element);
    }

    public void removeElement(String name) {
        contents.remove(QName.valueOf(name));
    }

}
