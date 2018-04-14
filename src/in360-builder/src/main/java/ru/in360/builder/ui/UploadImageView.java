/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 31.03.18 17:15 Anton Fomchenko 360@in360.ru
 */

package ru.in360.builder.ui;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
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
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import ru.in360.db.config.SystemParamKeys;
import ru.in360.db.service.SystemParamsService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@SpringView(name = UploadImageView.VIEW_NAME)
public class UploadImageView extends VerticalLayout implements PopupWindowView {
    public static final String VIEW_NAME = "uploadimage";
    private final SystemParamsService systemParamsService;
    private Window parentWindow;

    @Autowired
    public UploadImageView(SystemParamsService systemParamsService) {
        this.systemParamsService = systemParamsService;
    }

    @PostConstruct
    public void init() {
        VerticalLayout uploadLayout = new VerticalLayout();
        Upload upload = getUpload();
        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        uploadLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        uploadLayout.addComponent(new Label("Upload a panoramic image in equidistant projection"));
        uploadLayout.addComponent(upload);
        uploadLayout.setMargin(false);
        VerticalLayout descriptionLayout = new VerticalLayout();
        Label description = new Label("Technical Requirements:  JPEG or 8-bit TIFF format with no layers. Your panoramas must be 360º - <br />" +
                "the left side of your panorama must match with the right side of your panorama so the viewer can <br />" +
                "enjoy a fully immersive experience of looking all around without any visible seams. To upload a fully <br />" +
                "spherical panorama, make sure the aspect ratio of your panorama in pixel width and height is 2:1. <br />" +
                "Example sizes of panoramas that will be treated as spherical: 6000x3000, 10000x5000, 12288x6144.");
        description.setContentMode(ContentMode.HTML);
        description.setStyleName(ValoTheme.LABEL_SMALL);
        descriptionLayout.addComponent(description);
        descriptionLayout.setMargin(false);
        this.addComponent(uploadLayout);
        this.addComponent(descriptionLayout);
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
        Upload.Receiver receiver = new ImageUploadReceiver(systemParamsService);
        Upload upload = new Upload(null, receiver);
        upload.setImmediateMode(true);
        upload.setButtonCaption("Select and upload file");
        UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(upload, receiver);

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

    private static class ImageUploadReceiver implements Upload.Receiver {
        private final SystemParamsService systemParamsService;

        private ImageUploadReceiver(SystemParamsService systemParamsService) {
            this.systemParamsService = systemParamsService;
        }

        @Override
        public OutputStream receiveUpload(final String filename, final String MIMEType) {
            if (!systemParamsService.getParam(SystemParamKeys.IMAGE_PROCESSOR_USE_SFTP.getKey()).map(Boolean::valueOf).orElse(false)) {
                return getLocalFileOutputStream(filename);
            } else {
                return null;
            }
        }

        private OutputStream getLocalFileOutputStream(String filename) {
            return systemParamsService.getParam(SystemParamKeys.IMAGE_PROCESSOR_RAW_IMAGE_DIRECTORY.getKey())
                    .map(uploadPath -> {
                        File file = new File(uploadPath + filename);
                        try {
                            return new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            throw new IllegalStateException("upload failed");
                        }
                    })
                    .orElseThrow(() -> new IllegalStateException("upload failed"));
        }
    }

    private static class UploadInfoWindow extends Window implements
            Upload.StartedListener, Upload.ProgressListener,
            Upload.FailedListener, Upload.SucceededListener,
            Upload.FinishedListener {
        private final Label state = new Label();
        private final Label fileName = new Label();
        private final Label textualProgress = new Label();

        private final ProgressBar progressBar = new ProgressBar();
        private final Button cancelButton;
        private final Upload.Receiver receiver;

        private UploadInfoWindow(final Upload upload, final Upload.Receiver receiver) {
            super("Status");
            this.receiver = receiver;

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
            progressBar.setValue(readBytes / (float) contentLength);
            textualProgress.setValue("Processed " + readBytes + " bytes of " + contentLength);
        }

        @Override
        public void uploadSucceeded(final Upload.SucceededEvent event) {
            state.setValue("Upload finished");
        }

        @Override
        public void uploadFailed(final Upload.FailedEvent event) {
            state.setValue("Upload cancelled");
        }
    }
}