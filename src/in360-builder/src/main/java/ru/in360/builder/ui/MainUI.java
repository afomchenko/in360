/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 18.03.18 21:41 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {


    private final SpringViewProvider viewProvider;

    private Panel springViewDisplay;

    @Autowired
    public MainUI(SpringViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }


    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(new MarginInfo(true, false, false, false));
        setContent(root);

        final HorizontalLayout navigationBar = new HorizontalLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        Button btnAddNew = createNavigationButton("Add new panorama", UploadImageView.VIEW_NAME);
        Button btnSettings = createNavigationButton("Settings", SettingsView.VIEW_NAME);
        Button btnEditor = createNavigationButton("Panorama editor", PanoramaEditorView.VIEW_NAME);
        navigationBar.addComponent(btnAddNew);
        navigationBar.addComponent(btnSettings);
        navigationBar.addComponent(btnEditor);

//        navigationBar.setWidth("100%");
//        navigationBar.setComponentAlignment(btnAddNew, Alignment.MIDDLE_RIGHT);
//        navigationBar.setComponentAlignment(btnSettings, Alignment.MIDDLE_RIGHT);
//        navigationBar.setComponentAlignment(btnEditor, Alignment.MIDDLE_RIGHT);
        root.addComponent(navigationBar);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        if (springViewDisplay != null) {
            springViewDisplay.setContent((Component) view);
        }
    }
}