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

@Controller
public class HelloController {

	@GetMapping("/")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,Model model) {
		//model.addAttribute("fichero", name);
		return "mqtt";
	}

}
