/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.util.stream.Stream;

@XmlType(name = "limitView")
@XmlEnum
public enum LimitView {
    @XmlEnumValue("off") OFF("off"),
    @XmlEnumValue("auto") AUTO("auto"),
    @XmlEnumValue("lookat") LOOKAT("lookat"),
    @XmlEnumValue("range") RANGE("range"),
    @XmlEnumValue("fullrange") FULLRANGE("fullrange"),
    @XmlEnumValue("offrange") OFFRANGE("offrange");

    private String label;

    LimitView(String label) {
        this.label = label;
    }

    public static LimitView fromValue(String v) {
        return Stream.of(LimitView.values()).filter(e -> e.label.equals(v)).findFirst().orElse(OFF);
    }

    public String value() {
        return label;
    }
}