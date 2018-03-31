/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.tour;


import ru.in360.tour.model.ActionInfo;
import ru.in360.tour.model.IncludeInfo;
import ru.in360.tour.model.PreviewInfo;
import ru.in360.tour.model.SceneInfo;
import ru.in360.tour.model.SkinSettingsInfo;
import ru.in360.tour.model.TourInfo;
import ru.in360.tour.model.ViewInfo;
import ru.in360.tour.model.constants.FOVType;
import ru.in360.tour.model.constants.LimitView;

import javax.xml.bind.JAXBContext;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;

public class TourTest {

    protected JAXBContext jaxbContext;

    @Before
    public void setUp() throws Exception {
        jaxbContext = JAXBContext.newInstance(TourInfo.class, SceneInfo.class, ActionInfo.class, ViewInfo.class);
    }

    protected ActionInfo getStartupAction() {
        ActionInfo action = new ActionInfo();
        action.name = "startup";
        action.addActionContent("if(startscene === null, copy(startscene,scene[0].name));");
        action.addActionContent("loadscene(get(startscene), null, MERGE);");
        return action;
    }

    protected ActionInfo getAction() {
        ActionInfo action = new ActionInfo();
        action.addActionContent("doSomething();");
        action.addActionContent("doSomethingElse();");
        return action;
    }


    protected SceneInfo getScene(long id) {
        SceneInfo scene = new SceneInfo(id);
        scene.title = "Test Scene";
        scene.lat = 123.12345;
        scene.lng = 132.54321;
        scene.heading = 180D;
        scene.headingOffset = 90D;
        scene.onstart = getAction();
        scene.view = getView();
        scene.preview = new PreviewInfo("panos/panorama.tiles/preview.jpg");
        return scene;
    }

    protected ViewInfo getView() {
        ViewInfo view = new ViewInfo();
        view.setFov(120.0);
        view.setFovmax(140.0);
        view.setFovmin(90.0);
        view.setMaxpixelzoom(1.0);
        view.setHlookat(5.0);
        view.setVlookat(90.0);
        view.setLimitview(LimitView.AUTO);
        view.setFovtype(FOVType.MFOV);
        return view;
    }

    protected SkinSettingsInfo getProperties() {
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


    protected TourInfo tourInfo() throws Exception {
        TourInfo tour = new TourInfo();
        tour.title = "Virtual Tour";
        tour.onstart = getAction();
        tour.version = "1.18";
        tour.elements = new ArrayList<>(Arrays.asList(new IncludeInfo(new URI("skin/vtourskin.xml")), getStartupAction(), getProperties(), getScene(1), getScene(2)));
        return tour;
    }
}
