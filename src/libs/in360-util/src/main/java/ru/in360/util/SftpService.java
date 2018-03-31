/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 09.03.18 14:22 Anton Fomchenko 360@in360.ru
 */

package ru.in360.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Map;
import java.util.Properties;

public class SftpService {

    private Map<String, String> properties;

    public SftpService(Map<String, String> properties) {
        this.properties = properties;
    }

    public long downloadFileContent(String pathToFile, OutputStream output) {
        Session sftpSession = null;
        try {
            sftpSession = connect();
            return download(sftpSession, pathToFile, output);
        } catch (JSchException | IOException | SftpException e) {
            throw new IllegalStateException(e);
        } finally {
            if (sftpSession != null && sftpSession.isConnected()) {
                sftpSession.disconnect();
            }
        }
    }

    private Session connect() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(properties.get(FtpProperties.SFTP_KNOWN_HOSTS));
        Session sftpSession = jsch.getSession(FtpProperties.SFTP_USER, FtpProperties.SFTP_HOST, Integer.valueOf(FtpProperties.SFTP_PORT));
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        sftpSession.setConfig(config);
        sftpSession.setPassword(FtpProperties.SFTP_PASSWORD);
        sftpSession.connect();
        return sftpSession;
    }

    private long download(Session sftpSession, String pathToFile, OutputStream output) throws JSchException, SftpException, IOException {
        ChannelSftp sftpChannel = null;
        try {
            Channel channel = sftpSession.openChannel(FtpProperties.SFTP);
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
            try (InputStream input = sftpChannel.get(pathToFile)) {
                return writeToStream(input, output);
            }
        } finally {
            if (sftpChannel != null && sftpChannel.isConnected()) {
                sftpChannel.exit();
            }
        }
    }

    private long writeToStream(InputStream input, OutputStream output) throws IOException {
        try (
                ReadableByteChannel inputChannel = Channels.newChannel(input);
                WritableByteChannel outputChannel = Channels.newChannel(output)
        ) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
            long size = 0;
            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                size += outputChannel.write(buffer);
                buffer.clear();
            }
            return size;
        }
    }
}