/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrente;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class Concurrente {

//    public List<String> generaValor() {
//        return UUID.randomUUID().toString();
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileWriter fw = null;
        try {
            File f = new File("/tmp/datos.txt");
            fw = new FileWriter(f);
            // TODO code application logic here
            try (PrintWriter out = new PrintWriter(fw)) {
                // TODO code application logic here
                Random r = new Random();
                for (int i = 0; i < 100; i++) {
                    out.println(r.nextInt(100000));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Concurrente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Concurrente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
