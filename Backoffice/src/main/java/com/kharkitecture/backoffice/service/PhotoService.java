package com.kharkitecture.backoffice.service;

import com.kharkitecture.backoffice.dao.PhotoDAO;
import com.kharkitecture.backoffice.entity.Photo;
import liquibase.util.file.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PhotoService {
    @Autowired
    PhotoDAO photoDAO;
    Logger log;

    public static final int SMALL_SIZE_PHOTO = 160;
    public static final int MIDDLE_SIZE_PHOTO = 480;
    public static final int LARGE_SIZE_PHOTO = 720;

    public PhotoService() {
        this.log = LogManager.getLogger(this.getClass());
    }

    public boolean handleFileUpload(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.error("Failed photo uploading. The image is missing");
                throw new Exception();
            }
            String extension = FilenameUtils.getExtension(file.getOriginalFilename()); //find the file extension
            if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("bmp") && !extension.equals("png"))
                throw new Exception();

            try (InputStream in = new ByteArrayInputStream(file.getBytes());
                 ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                BufferedImage bufferedImage = ImageIO.read(in);
                //write to db
                addPhotoInDB(bufferedImage, extension);
                ImageIO.write(bufferedImage, extension, os);
                return true;
            }
        } catch (Exception e) {
            log.error("You were unable to upload an image");
        }
        return false;
    }

    public boolean addPhotoInDB(BufferedImage image, String extension) {
        int[] imageSize = {SMALL_SIZE_PHOTO, MIDDLE_SIZE_PHOTO, LARGE_SIZE_PHOTO};
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        byte[] bytesOriginalPhoto = ostream.toByteArray();
        Photo photo;
        if (!isFullPhotoExistsInDB(bytesOriginalPhoto)) photo = new Photo(bytesOriginalPhoto);
        else return true;

        try {
            for (int i = 0; i < imageSize.length; i++) {
                image = scaleImage(image, imageSize[i]); //return new resized image
                ImageIO.write(image, extension, ostream);
                switch (i) {
                    case (0):
                        photo.setSmallSize(ostream.toByteArray());
                        break;
                    case (1):
                        photo.setMiddleSize(ostream.toByteArray());
                        break;
                    case (2):
                        photo.setLargeSize(ostream.toByteArray());
                        break;
                }
                ostream.reset();
            }

            photoDAO.save(photo);
            ostream.close();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage() + " There was an error changing the size and writing to the image database\n");
            return false;
        }
    }

    //The method of checking the all values of a photo in the database
    private boolean isFullPhotoExistsInDB(byte[] bytesPhoto) {
        if (photoDAO.existsByOriginalSize(bytesPhoto)) {
            Photo photo = photoDAO.findByOriginalSize(bytesPhoto);
            if (photo.getSmallSize() == null || photo.getMiddleSize() == null || photo.getLargeSize() == null)
                return false;
            return true;
        } else return false;
    }

    //methods for resize image
    private BufferedImage scaleImage(BufferedImage img, int heigthAndWidth) {
        int width, height = heigthAndWidth;
        width = height;
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        if (imgWidth * height < imgHeight * width) {
            width = imgWidth * height / imgHeight;
        } else {
            height = imgHeight * width / imgWidth;
        }
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        renderingImage(g, img, height, width);
        return newImage;
    }

    private BufferedImage scaleImage(BufferedImage img, int height, int width) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        if (imgWidth * height < imgHeight * width) {
            width = imgWidth * height / imgHeight;
        } else {
            height = imgHeight * width / imgWidth;
        }
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        renderingImage(g, img, height, width);
        return newImage;
    }

    private void renderingImage(Graphics2D g, BufferedImage img, int height, int width) {
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.clearRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
    }

}