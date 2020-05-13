package com.example.springboot;

import java.awt.*;
import java.io.*;
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

import static org.apache.tomcat.jni.Time.sleep;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("img")
public class ImageController {

    private String filenameLastPicture="";

    //Funcion original de getImage
    //@GetMapping("getImage")
    @GetMapping("getImage")
    public ResponseEntity<byte[]> getImage(@RequestParam(name="name", required=false, defaultValue="") String name) throws IOException {
        System.out.println("Nombre: "+name);
        try {
            File img = new File("/home/pi/Desktop/" + name);
            /*while ( !img.canWrite()) {
                Thread.sleep(100);
                System.out.println("Esperando desbloqueo: "+name);
            }*/
            //byte[] media = Files.readAllBytes(img.toPath());
            byte[] media = read(img);
            if (!filenameLastPicture.equals("")) {
                File imgdel = new File("/home/pi/Desktop/" + filenameLastPicture);
                System.out.println("Borrando: " + filenameLastPicture);
                imgdel.delete();
            }
            filenameLastPicture=name;
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, OK);

            return responseEntity;
        } catch (Exception e) {
            System.out.println("Excep " + e.getMessage());
            return null;
        }
    }

    public byte[] read(File file) throws IOException {

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        }finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    //@GetMapping("getImageLocked")
    @GetMapping("getImageOther")
    public ResponseEntity<byte[]> getImageLocked(@RequestParam(name="name", required=false, defaultValue="") String name) throws IOException {
        System.out.println("Nombre: "+name);
        try {
            File img = new File("/home/pi/Desktop/" + name);
            while ( !img.canWrite()) {
                Thread.sleep(100);
                System.out.println("Esperando desbloqueo: "+name);
            }
            byte[] media = leerArchivoDesbloqueado(img);

            filenameLastPicture=name;
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, OK);

            return responseEntity;
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] leerArchivoDesbloqueado(File img) throws IOException {
        byte[] media = null;

        if (img.canWrite()){
            media = Files.readAllBytes(img.toPath());
        }else{
            System.out.println("No se ha podido leer, intento en 100 milisegundos");
            sleep(100);
            leerArchivoDesbloqueado(img);
        }

        return media;
    }

    /*@GetMapping("getImageAlert")
        public ResponseEntity<byte[]> getImageAlert() throws IOException {
            File img = new File("/home/pi/Desktop/alert.jpg");
            byte[] media = Files.readAllBytes(img.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, OK);

            return responseEntity;
        }*/


}