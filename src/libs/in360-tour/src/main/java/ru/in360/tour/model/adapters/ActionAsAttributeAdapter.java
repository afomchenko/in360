/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour.model.adapters;

import ru.in360.tour.model.ActionInfo;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;


public class ActionAsAttributeAdapter extends XmlAdapter<String, ActionInfo> {

    @Override
    public ActionInfo unmarshal(String v) throws Exception {
        ActionInfo action = new ActionInfo();
        Arrays.stream(v.split(" ")).forEach(action::addActionContent);
        return action;
    }

    @Override
    public String marshal(ActionInfo v) throws Exception {
        if (v == null) {
            return null;
        }
        if (v.content.size() < 1) {
            return "";
        } else {
            return String.join(" ", v.content);
        }
    }
}
