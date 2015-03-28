package com.jbion.web.evstracker.beans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class UploadBean implements Serializable {

    private static final String PATH_PROPERTIES_FILE = "/com/jbion/web/evstracker/beans/paths.properties";
    private static final String PROP_UPLOAD = "uploadLocation";

    private static final int BUFFER_SIZE = 10240;
    private static final String UPLOAD_PATH;
    static {
        UPLOAD_PATH = retrieveFullPath();
    }

    private String filename;
    private boolean uploaded;
    private File file;

    public UploadBean() {
        uploaded = false;
    }

    public boolean getUploaded() {
        return uploaded;
    }

    public String getFilename() {
        return filename;
    }

    private static String retrieveFullPath() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String url = null;
        try (InputStream propertiesFile = classLoader.getResourceAsStream(PATH_PROPERTIES_FILE)) {
            if (propertiesFile == null) {
                throw new RuntimeException("Paths properties file " + PATH_PROPERTIES_FILE + " not found.");
            }
            properties.load(propertiesFile);
            url = properties.getProperty(PROP_UPLOAD);
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Incorrect path properties file " + PATH_PROPERTIES_FILE + ": "
                    + iae.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Paths properties file " + PATH_PROPERTIES_FILE + " could not be loaded.", e);
        }
        return url;
    }

    public void handleUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        filename = uploadedFile.getFileName();
        System.out.println("handleFileUpload(" + filename + ")");
        System.out.println("LE PATH = " + UPLOAD_PATH);
        if (true) {
            return;
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(uploadedFile.getInputstream(), BUFFER_SIZE);
            file = new File(UPLOAD_PATH);
            out = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (file != null) {
                file.delete();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException ignore) {
            }
            try {
                out.close();
            } catch (IOException ignore) {
            }
        }
        uploaded = true;
    }

    public void cancelUpload() {
        System.out.println("cancelUpload(" + filename + ")");
        if (file != null && file.delete()) {
            System.out.println("File " + file.getName() + " deleted.");
            uploaded = false;
        }
    }

}
