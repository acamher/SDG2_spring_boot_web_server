package com.example.springboot;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;

import javax.activation.FileTypeMap;
import javax.activation.MimetypesFileTypeMap;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("img")
public class ImageController {

    @GetMapping("getImage")
    public ResponseEntity<byte[]> getImage() throws IOException {
        File img = new File("/home/pi/Desktop/image.jpg");
        byte[] media = Files.readAllBytes(img.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, OK);

        return responseEntity;
    }

    @GetMapping("getImageAlert")
        public ResponseEntity<byte[]> getImageAlert() throws IOException {
            File img = new File("/home/pi/Desktop/alert.jpg");
            byte[] media = Files.readAllBytes(img.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, OK);

            return responseEntity;
        }


}