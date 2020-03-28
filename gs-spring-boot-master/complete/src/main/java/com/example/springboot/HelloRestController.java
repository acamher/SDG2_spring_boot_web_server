package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



import java.io.File;  // Import the File class
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileNotFoundException;  // Import this class to handle errors

@RestController
public class HelloRestController{

	@RequestMapping("/mqtt")
	public String sensor(@RequestParam(name="dato", required=false, defaultValue="prueba.txt") String dato) {
		return leer(dato);	//El directorio en el que se ejecuta es /gs-spring-boot-master/complete por tanto para sacarlo de la app sería "../../dirx".
	}

	private String leer(String path) {
		String data ="";

		//System.out.println("Directorio actual: " + System.getProperty("user.dir"));

		try {
      File myObj = new File(path);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
      }
      myReader.close();
			return data;
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
			return "Error";
    }
	}

}
