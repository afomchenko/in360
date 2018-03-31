/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 09.03.18 15:40 Anton Fomchenko 360@in360.ru
 */

package ru.in360.web.controllers;

import com.jcraft.jsch.JSchException;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.in360.util.SftpService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/web/resources")
public class FileResourcesController {

    private final SftpService sftpService;

    public FileResourcesController(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    @RequestMapping(value = "/jpg/{path}", method = RequestMethod.GET)
    public void getImageFile(HttpServletResponse response, @PathVariable("path") String path) throws IOException, JSchException {
        if (!StringUtils.isEmpty(path)) {
            try (OutputStream output = response.getOutputStream()) {
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                response.setContentLengthLong(sftpService.downloadFileContent(path, output));
            }
        } else {
            throw new IllegalArgumentException();
        }
    }


    @RequestMapping(value = "/file/{path}", method = RequestMethod.GET)
    public void getFile(HttpServletResponse response, @PathVariable("path") String path) throws IOException, JSchException {
        if (!StringUtils.isEmpty(path)) {
            try (OutputStream output = response.getOutputStream()) {
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                response.setContentLengthLong(sftpService.downloadFileContent(path, output));
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
