/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "type")
@XmlEnum
public enum ImageType {
    CUBE,
    CUBESTRIP,
    CYLINDER,
    SPHERE,
    NUL;

    public static ImageType fromValue(String v) {
        return valueOf(v);
    }

    public String value() {
        return name();
    }
}
