/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 31.03.18 17:15 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = UploadImageView.VIEW_NAME)
public class UploadImageView extends VerticalLayout implements View, PopupWindow {
    public static final String VIEW_NAME = "uploadimage";

    Button uploadButton = new Button("Upload", this::uploadButtonClick);

    @PostConstruct
    public void init() {
        setSizeFull();

        HorizontalLayout fields = new HorizontalLayout(uploadButton);
        fields.setCaption("Upload panoramic image in equidistant projection");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();
        this.addComponent(fields);
        this.setWidth(300, Unit.POINTS);
        this.setHeight(300, Unit.POINTS);
        this.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
    }

    public void uploadButtonClick(Button.ClickEvent e) {
        // do nothing
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}