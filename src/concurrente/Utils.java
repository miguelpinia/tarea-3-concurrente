/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrente;

import concurrente.aleatorio.ArbolAleatorio2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class Utils {

    /**
     * Genera una lista de tamaño TAMAÑO de elementos desde 1 hasta TAMAÑO. Esta
     * lista contiene los elementos permutados.11
     *
     * @param tamaño El tamaño de la lista.
     * @return Una lista de elementos permutados desde 1 hasta TAMAÑO.
     */
    public static List<Integer> generaPermutacion(int tamaño) {
        List<Integer> datos = new ArrayList<>();
        for (int i = 0; i < tamaño; i++) {
            datos.add(i + 1);
        }
        Collections.shuffle(datos);
        return datos;
    }

    public static List<Integer> obtenDatos(String filename) {
        FileReader fr = null;
        try {
            List<Integer> datos = new ArrayList<>();
            fr = new FileReader(new File(filename));
            BufferedReader br = new BufferedReader(fr);
            String aux;
            while ((aux = br.readLine()) != null) {
                Integer val = Integer.parseInt(aux);
                datos.add(val);
            }
            return datos;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArbolAleatorio2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArbolAleatorio2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(ArbolAleatorio2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileWriter fw = null;
        try {
            File f = new File("/tmp/datos.txt");
            fw = new FileWriter(f);
            List<Integer> permutacion = generaPermutacion(100000);
            // TODO code application logic here
            try (PrintWriter out = new PrintWriter(fw)) {
                for (int i = 0; i < permutacion.size(); i++) {
                    out.println(permutacion.get(i));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
