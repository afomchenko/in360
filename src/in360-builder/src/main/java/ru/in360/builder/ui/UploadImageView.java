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
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import javax.annotation.PostConstruct;
import java.io.OutputStream;

@SpringView(name = UploadImageView.VIEW_NAME)
public class UploadImageView extends VerticalLayout implements PopupWindowView {
    public static final String VIEW_NAME = "uploadimage";

    private Window parentWindow;

    @PostConstruct
    public void init() {
        setSizeFull();
        Upload upload = getUpload();
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.setWidth(300, Unit.POINTS);
        this.setHeight(100, Unit.POINTS);
        this.addComponent(new Label("Upload a panoramic image in equidistant projection"));
        this.addComponent(upload);
    }

    public void uploadButtonClick(Button.ClickEvent e) {
        // do nothing
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    @Override
    public void setParentWindow(Window window) {
        this.parentWindow = window;
    }

    private Upload getUpload() {
        ImageUploadReciever reciever = new ImageUploadReciever();
        reciever.setSlow(true);
        Upload upload = new Upload(null, reciever);
        upload.setImmediateMode(true);
        upload.setButtonCaption("Select and upload file");
        UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(upload, reciever);

        upload.addStartedListener(event -> {
            if (uploadInfoWindow.getParent() == null) {
                UI.getCurrent().addWindow(uploadInfoWindow);
            }
            parentWindow.close();
            uploadInfoWindow.setClosable(false);
        });
        upload.addFinishedListener(event -> uploadInfoWindow.setClosable(true));
        return upload;
    }

    private static class ImageUploadReciever implements Upload.Receiver {
        private int counter;
        private int total;
        private boolean sleep;

        @Override
        public OutputStream receiveUpload(final String filename, final String MIMEType) {
            counter = 0;
            total = 0;
            return new OutputStream() {
                private static final int searchedByte = '\n';

                @Override
                public void write(final int b) {
                    total++;
                    if (b == searchedByte) {
                        counter++;
                    }
                    if (sleep && total % 1000 == 0) {
                        try {
                            Thread.sleep(100);
                        } catch (final InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }

        private int getLineBreakCount() {
            return counter;
        }

        private void setSlow(boolean value) {
            sleep = value;
        }
    }

    private static class UploadInfoWindow extends Window implements
            Upload.StartedListener, Upload.ProgressListener,
            Upload.FailedListener, Upload.SucceededListener,
            Upload.FinishedListener {
        private final Label state = new Label();
        private final Label result = new Label();
        private final Label fileName = new Label();
        private final Label textualProgress = new Label();

        private final ProgressBar progressBar = new ProgressBar();
        private final Button cancelButton;
        private final ImageUploadReciever reciever;

        private UploadInfoWindow(final Upload upload, final ImageUploadReciever reciever) {
            super("Status");
            this.reciever = reciever;

            setResizable(false);
            setDraggable(false);
            setWidth(400.0f, Unit.POINTS);
            center();
            setModal(true);

            final FormLayout uploadInfoLayout = new FormLayout();
            setContent(uploadInfoLayout);
            uploadInfoLayout.setMargin(true);

            final HorizontalLayout stateLayout = new HorizontalLayout();
            stateLayout.setSpacing(true);
            stateLayout.addComponent(state);

            cancelButton = new Button("Cancel");
            cancelButton.addClickListener(event -> upload.interruptUpload());
            cancelButton.setVisible(false);
            cancelButton.setStyleName("small");
            stateLayout.addComponent(cancelButton);

            stateLayout.setCaption("Current state");
            state.setValue("Idle");
            uploadInfoLayout.addComponent(stateLayout);

            fileName.setCaption("File name");
            uploadInfoLayout.addComponent(fileName);

            result.setCaption("Line breaks counted");
            uploadInfoLayout.addComponent(result);

            progressBar.setCaption("Progress");
            progressBar.setVisible(false);
            uploadInfoLayout.addComponent(progressBar);

            textualProgress.setVisible(false);
            uploadInfoLayout.addComponent(textualProgress);

            upload.addStartedListener(this);
            upload.addProgressListener(this);
            upload.addFailedListener(this);
            upload.addSucceededListener(this);
            upload.addFinishedListener(this);

        }

        @Override
        public void uploadFinished(final Upload.FinishedEvent event) {
            progressBar.setVisible(false);
            textualProgress.setVisible(false);
            cancelButton.setVisible(false);
        }

        @Override
        public void uploadStarted(final Upload.StartedEvent event) {
            progressBar.setValue(0f);
            progressBar.setVisible(true);
            UI.getCurrent().setPollInterval(500);
            textualProgress.setVisible(true);
            state.setValue("Uploading");
            fileName.setValue(event.getFilename());
            cancelButton.setVisible(true);
        }

        @Override
        public void updateProgress(final long readBytes, final long contentLength) {
            // this method gets called several times during the update
            progressBar.setValue(readBytes / (float) contentLength);
            textualProgress.setValue("Processed " + readBytes + " bytes of " + contentLength);
            result.setValue(reciever.getLineBreakCount() + " (counting...)");
        }

        @Override
        public void uploadSucceeded(final Upload.SucceededEvent event) {
            state.setValue("Upload finished");
            result.setValue(reciever.getLineBreakCount() + " (total)");
        }

        @Override
        public void uploadFailed(final Upload.FailedEvent event) {
            state.setValue("Upload cancelled");
            result.setValue(reciever.getLineBreakCount()
                    + " (counting interrupted at "
                    + Math.round(100 * progressBar.getValue()) + "%)");
        }
    }
}