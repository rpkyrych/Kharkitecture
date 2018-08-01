package com.kharkitecture.backoffice.controller;

import com.kharkitecture.backoffice.service.PhotoService;
import liquibase.util.file.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Controller
public class PhotoUploadController {
    Logger log;
    @Autowired
    PhotoService photoService;

    @RequestMapping(value = "/uploadphoto", method = RequestMethod.POST, produces={"image/jpg","image/png", "image/bmp", "image/png"})
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file) {
        this.log = LogManager.getLogger(this.getClass());
        try {
            if (file.isEmpty()) {
                log.error("Failed photo uploading. The image is missing");
                throw new Exception();
            }
            String extension = FilenameUtils.getExtension(file.getOriginalFilename()); //find the file extension

            if (!extension.equals("jpg") && !extension.equals("jpeg") &&
                    !extension.equals("bmp") && !extension.equals("png")) throw new Exception();

            InputStream in = new ByteArrayInputStream(file.getBytes());
            BufferedImage bufferedImage = ImageIO.read(in);
            //write to db
            photoService.addPhoto(bufferedImage,extension);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, extension, os);
            return "Success photo uploading";
        } catch (Exception e) {
            log.error("You were unable to upload an image");
        }
            return "Failed photo uploading  ";
    }



}
