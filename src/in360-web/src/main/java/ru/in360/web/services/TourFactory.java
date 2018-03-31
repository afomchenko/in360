/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.web.services;

import ru.in360.tour.model.ActionInfo;
import ru.in360.tour.model.IncludeInfo;
import ru.in360.tour.model.SkinSettingsInfo;
import ru.in360.tour.model.TourInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class TourFactory {

    public TourFactory() {
    }

    public TourInfo createDefaultTourEnvironment() {
        try {
            return getDefaultTourInfo();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private TourInfo getDefaultTourInfo() throws URISyntaxException {
        TourInfo tour = new TourInfo();
        tour.title = "Virtual Tour";
        ActionInfo startupAction = getDefaultTourStartupAction();
        tour.onstart = startupAction;
        tour.version = "1.18";
        tour.elements = new ArrayList<>(Arrays.asList(new IncludeInfo(new URI("skin/vtourskin.xml")), startupAction, getDefaultTourProperties()));
        return tour;
    }

    private SkinSettingsInfo getDefaultTourProperties() {
        SkinSettingsInfo styleInfo = new SkinSettingsInfo();
        styleInfo.addElement("bingmaps", "true");
        styleInfo.addElement("bingmaps_key", "testkey");
        styleInfo.addElement("bingmaps_zoombuttons", "false");
        styleInfo.addElement("thumbs_width", "120");
        styleInfo.addElement("thumbs_height", "80");
        styleInfo.addElement("thumbs_padding", "10");
        styleInfo.addElement("thumbs_crop", "0|40|240|160");
        styleInfo.addElement("thumbs_opened", "false");
        styleInfo.addElement("thumbs_text", "true");
        styleInfo.addElement("thumbs_dragging", "true");
        styleInfo.addElement("thumbs_onhoverscrolling", "false");
        styleInfo.addElement("thumbs_scrollbuttons", "false");
        styleInfo.addElement("thumbs_scrollindicator", "false");
        styleInfo.addElement("thumbs_loop", "false");
        styleInfo.addElement("tooltips_thumbs", "false");
        styleInfo.addElement("tooltips_hotspots", "true");
        styleInfo.addElement("tooltips_mapspots", "false");
        styleInfo.addElement("controlbar_offset", "0");
        return styleInfo;
    }

    private ActionInfo getDefaultTourStartupAction() {
        ActionInfo action = new ActionInfo();
        action.name = "startup";
        action.addActionContent("if(startscene === null, copy(startscene,scene[0].name));");
        action.addActionContent("loadscene(get(startscene), null, MERGE);");
        return action;
    }
}
