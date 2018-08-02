package com.kharkitecture.backoffice.service;

import com.kharkitecture.backoffice.dao.PhotoDAO;
import com.kharkitecture.backoffice.entity.Photo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PhotoService {
    @Autowired
    PhotoDAO photoDAO;
    Logger log;

    public static final int SMALL_SIZE_PHOTO = 160;
    public static final int MIDDLE_SIZE_PHOTO = 480;
    public static final int LARGE_SIZE_PHOTO = 720;

    //The method of checking the value of a photo in the database
    //returns a new photo object if the photo is not in the database
    Photo givePhoto(byte[] photo){
        if(photoDAO.existsByOriginalSize(photo)) return photoDAO.findByOriginalSize(photo);
        else return new Photo(photo);
    }

    public boolean addPhoto(BufferedImage image, String extension) {
        this.log = LogManager.getLogger(this.getClass());
        int[] imageSize = {SMALL_SIZE_PHOTO, MIDDLE_SIZE_PHOTO, LARGE_SIZE_PHOTO};
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        Photo photo = givePhoto(ostream.toByteArray());//check and return photo is exist in db or return new photo

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
            log.error(e.getMessage()+" There was an error changing the size and writing to the image database\n");
            return false;
        }
    }

    public BufferedImage scaleImage(BufferedImage img, int heigthAndWidth) {
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
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.clearRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
        return newImage;
    }

    public BufferedImage scaleImage(BufferedImage img,int height, int width) {
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
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.clearRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
        return newImage;
    }

}