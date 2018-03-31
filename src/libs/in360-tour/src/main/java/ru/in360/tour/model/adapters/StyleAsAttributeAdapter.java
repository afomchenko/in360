/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model.adapters;


import ru.in360.tour.model.StyleInfo;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StyleAsAttributeAdapter extends XmlAdapter<String, StyleInfo> {

    @Override
    public StyleInfo unmarshal(String v) throws Exception {
        StyleInfo style = new StyleInfo();
        style.setName(v);
        return style;
    }

    @Override
    public String marshal(StyleInfo v) throws Exception {
        return v != null ? v.getName() : null;
    }
}
