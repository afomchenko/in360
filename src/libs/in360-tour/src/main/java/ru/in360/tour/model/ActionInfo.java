/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model;

import ru.in360.tour.model.constants.Autorun;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "action")
public class ActionInfo extends TourElement {
    @XmlAttribute
    public Autorun autorun;
    @XmlAttribute
    public Boolean secure;

    @XmlMixed
    public List<String> content = new ArrayList<>();

    int count = 0;

    public ActionInfo(String name, Autorun autorun, boolean secure) {
        this.name = name;
        this.autorun = autorun;
        this.secure = secure;
    }

    public ActionInfo() {
    }

    public void addActionContent(String actionContent) {
        content.add(actionContent);
    }
}
