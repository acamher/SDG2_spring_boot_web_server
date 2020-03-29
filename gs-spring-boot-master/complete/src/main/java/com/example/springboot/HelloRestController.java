package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

import java.io.FileNotFoundException;  // Import this class to handle errors

@RestController
public class HelloRestController{

	@RequestMapping("/mqtt")
	public String sensor(@RequestParam(name="dato", required=false, defaultValue="prueba.txt") String dato) {
		return leer(dato);	//El directorio en el que se ejecuta es /gs-spring-boot-master/complete por tanto para sacarlo de la app sería "../../dirx".
	}

	private String leer(String path) {

	    try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sensor","spring","web_ddbb");
            Statement stmt = con.createStatement();

            ResultSet rs = null;

            switch (path){
                case "A":
                    rs = stmt.executeQuery("SELECT distancia FROM data WHERE id=1");
                case "B":
                    rs = stmt.executeQuery("SELECT distancia FROM data WHERE id=2");
                case "C":
                    rs = stmt.executeQuery("SELECT distancia FROM data WHERE id=3");
            }

            if(rs.next()){
                return "" + rs.getInt(1);
            }else{
                return "";
            }

        } catch (Exception e) {
		    System.out.println(e);
            return "" ;
        }

	}

}
