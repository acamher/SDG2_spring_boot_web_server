package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.Socket;
import java.io.DataOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import java.io.FileNotFoundException;  // Import this class to handle errors

@RestController
public class HelloRestController{

    @RequestMapping("/mqtt")
    public String sensor(@RequestParam(name="dato", required=false, defaultValue="A") String dato) {
        return leer();	//El directorio en el que se ejecuta es /gs-spring-boot-master/complete por tanto para sacarlo de la app sería "../../dirx".
    }

    @RequestMapping("/send")
    public String send(@RequestParam(name="estado", required=false, defaultValue="1") String dato) {
        //System.out.println(dato);

        final String ip = read_ip();
        final int puerto = 23;

        /*try{
            Socket sc = new Socket(ip,puerto);
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(dato);
            sc.close();
        }catch (Exception e){
            System.out.println(e + "Probablemente sea que Arduino esté apagado");
        }*/

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sensor?serverTimezone=Europe/Berlin" ,"spring","web_ddbb");

            String query = null;
            if (dato.equals("1")) {
                query = "UPDATE stado SET estado='on' WHERE id=1";
            }else if (dato.equals("2")){
                query = "UPDATE stado SET estado='off' WHERE id=1";
            }else{
                query = "UPDATE stado SET estado='man' WHERE id=1";
            }

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            con.close();
            System.out.println("Se ha cambiado el sistema");
        } catch (Exception e) {
            System.out.println(e);
        }

        return "";
    }

    @RequestMapping("/estado")
    public String getEstado(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sensor?serverTimezone=Europe/Berlin" ,"spring","web_ddbb");
            Statement stmt = con.createStatement();

            ResultSet rs = null;
            String resultado = null;
            rs = stmt.executeQuery("SELECT estado FROM stado WHERE id=1");
            if(rs.next())
                resultado = "" + rs.getString("estado");

            return resultado;

        } catch (Exception e) {
            System.out.println(e);
            return "" ;
        }
    }


    private String read_ip(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sensor?serverTimezone=Europe/Berlin" ,"spring","web_ddbb");
            Statement stmt = con.createStatement();

            ResultSet rs = null;
            String resultado = null;
            rs = stmt.executeQuery("SELECT direccion FROM ip WHERE id=1");
            if(rs.next())
                resultado = "" + rs.getString("direccion");

            return resultado;

        } catch (Exception e) {
            System.out.println(e);
            return "" ;
        }
    }

    // Devuelve los valores del sensor y el estado del sistema.
    private String leer() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sensor?serverTimezone=Europe/Berlin" ,"spring","web_ddbb");
            Statement stmt = con.createStatement();

            ResultSet rs = null;
            String resultado = null;
            rs = stmt.executeQuery("SELECT distancia FROM data WHERE id=1");
            if(rs.next())
                resultado = "" + rs.getInt(1);
            rs = null;
            rs = stmt.executeQuery("SELECT distancia FROM data WHERE id=2");
            if(rs.next())
                resultado = resultado + ";" + rs.getInt(1);
            rs = null;
            rs = stmt.executeQuery("SELECT distancia FROM data WHERE id=3");
            if(rs.next())
                resultado = resultado + ";" + rs.getInt(1);
            rs = null;
            rs = stmt.executeQuery("SELECT estado FROM stado WHERE id=1");
            if(rs.next())
                resultado = resultado + ";" + rs.getString("estado");

            return resultado;

        } catch (Exception e) {
            System.out.println(e);
            return "" ;
        }
    }
}
