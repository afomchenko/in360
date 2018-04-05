/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 18.03.18 21:41 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI
@Title("in360 tour builder")
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {

    private final SpringViewProvider viewProvider;

    private VerticalLayout springViewDisplay;

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
        navigationBar.setMargin(new MarginInfo(false, true, false, true));
        Button btnAddNew = createPopupViewButton("Add new panorama", UploadImageView.VIEW_NAME);
        Button btnSettings = createPopupViewButton("Settings", SettingsView.VIEW_NAME);
        Button btnEditor = createNavigationButton("Panorama editor", PanoramaEditorView.VIEW_NAME);
        VerticalLayout spacer = new VerticalLayout();
        spacer.setWidth(100.0f, Unit.PERCENTAGE);
        spacer.setHeightUndefined();
        navigationBar.addComponent(spacer);
        HorizontalLayout horizontalLayout = new HorizontalLayout(btnAddNew, btnSettings, btnEditor);
        navigationBar.addComponent(horizontalLayout);

        navigationBar.setWidth(100.0f, Unit.PERCENTAGE);
        navigationBar.setComponentAlignment(spacer, Alignment.MIDDLE_LEFT);
        navigationBar.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_RIGHT);
        root.addComponent(navigationBar);

        springViewDisplay = new VerticalLayout();
        springViewDisplay.setSizeFull();
        springViewDisplay.setMargin(false);
        springViewDisplay.setStyleName(ValoTheme.LAYOUT_WELL);
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    private Button createPopupViewButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.addClickListener(event -> createPopupView(caption, viewName));
        return button;
    }

    private void createPopupView(String caption, final String viewName) {
        View view = viewProvider.getView(viewName);
        final Window window = new Window(caption);
        if (view instanceof PopupWindowView) {
            ((PopupWindowView) view).isPopupWindowModal().ifPresent(window::setModal);
            ((PopupWindowView) view).isPopupWindowCentered().ifPresent(e -> window.center());
            ((PopupWindowView) view).isPopupWindowResizable().ifPresent(window::setResizable);
            ((PopupWindowView) view).setParentWindow(window);
        }
        window.setContent((Component) view);
        this.getUI().getUI().addWindow(window);
    }

    @Override
    public void showView(View view) {
        if (springViewDisplay != null) {
            springViewDisplay.removeAllComponents();
            springViewDisplay.addComponent((Component) view);
        }
    }
}