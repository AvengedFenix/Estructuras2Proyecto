/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Main.GUI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivos {

    String name;
    ArrayList<Campos> campos = new ArrayList();
    ArrayList<String> registros = new ArrayList();
    ArrayList<Integer> llaves = new ArrayList();

    BTree tree = new BTree();

    public Archivos() {
    }

    public Archivos(String name) {
        this.name = name;
        File f = new File("./Archivos/" + name + ".txt");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Campos> getCampos() {
        return campos;
    }

    public void setCampos(ArrayList<Campos> campos) {
        this.campos = campos;
    }

    public void addCampo(Campos c) {
        this.campos.add(c);
    }

    public ArrayList<String> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<String> registros) {
        this.registros = registros;
    }

    public void addRegistro(String r) {
        this.registros.add(r);
    }

    public ArrayList<Integer> getLlaves() {
        return llaves;
    }

    public void setLlaves(ArrayList<Integer> llaves) {
        this.llaves = llaves;
    }

    public void addLlave(int ll) {
        this.llaves.add(ll);
    }

    public BTree getTree() {
        return tree;
    }

    public void setTree(BTree tree) {
        this.tree = tree;
    }

    public void save() throws IOException {
        String path = "./Archivos/" + name + ".txt";
        File f = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        if (!campos.isEmpty()) {
            for (int i = 0; i < campos.size(); i++) {
                Campos c = campos.get(i);
                writer.append(c.toString() + ", ");
            }

        }
        try {
            if (!registros.isEmpty()) {
                writer.append("\n");
                for (int i = 0; i < registros.size(); i++) {
                    writer.append(registros.get(i) + "\n");
                }

            }
        } catch (Exception NullException) {

        }
        writer.close();
    }

    /*
    public void delete(int reg) throws IOException{
        String path = "./Archivos/" + name;
        File f = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        
        
        if (!campos.isEmpty()) {
            for (int i = 0; i < campos.size(); i++) {
                Campos c = campos.get(i);
                writer.append(c.toString() + ", ");
            }

        }
        try {
            if (!registros.isEmpty()) {
                writer.append("\n");
                for (int i = 0; i < registros.size(); i++) {
                    writer.append(i == reg ? "*" + registros.get(i) + "\n" : registros.get(i) + "\n" );
                }

            }
        } catch (Exception NullException) {

        }
        writer.close();
        
        String avail = "./Archivos/" + name.replaceFirst("[.][^.]+$", "")+ ".avail";
        
        File f2 = new File(avail);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f2));
        
        
        
        bw.append(reg + "," + registros.get(reg).length() +";");
        
        bw.close();
        
        
        
    }
    
        public void agregarRegistro2(String registro){
        String path = "./Archivos" + name;
        String avail = "./Archivos/" + path.replaceFirst("[.][^.]+$", "") + ".avail";
        
        
        File f2 = new File(avail);
        
        
        
    }
     */
    public void agregarRegistro(String registro) throws IOException {//utilizando la avail list
        String path = "./Archivos/" + name;
        String avail = "./Archivos/" + path.replaceFirst("[.][^.]+$", "") + ".avail";
        File f2 = new File(avail);
        BufferedReader br = new BufferedReader(new FileReader(f2));
        String pathReg = "./Archivos/" + name;

        File f = new File(pathReg);
        BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
        try {
            if (br.read() == -1) {//si avail list esta vacio, se agrega el registro al final del archivo

                writer.append(registro);

                writer.flush();

            } else {//revisar el offset y tamano de los registros en el avail list
                StringTokenizer st = new StringTokenizer(br.readLine(), ";", true);
                boolean encontroEspacio = false;
                int offset = 0;
                int size;
                while (st.hasMoreTokens()) {
                    StringTokenizer st2 = new StringTokenizer(st.nextToken(), ",", true);
                    offset = Integer.parseInt(st2.nextToken());
                    size = Integer.parseInt(st2.nextToken());

                    if (size >= registro.length()) {
                        encontroEspacio = true;
                    }
                    st.nextToken();
                }

                if (encontroEspacio) {
                    reemplazarEliminado(registro/*String del registro*/, offset/*posicion del registro eliminado en el archivo*/);
                    //reemplazar el registro borrado por el registro que se va a agregar y actualizar avail list
                } else {//si no encuentra espacio, el registro se agrega al final
                    writer.append(registro);
                }
                /*System.out.println(st.nextToken());
                
                System.out.println(st.nextToken());
                 */
            }

        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            br.close();
            writer.close();

        }

    }

    public void reemplazarEliminado(String registro, int offset) throws IOException {
        //recibe registro y posicion del registro eliminado y se reemplaza el eliminado por el nuevo registro 

        String reg = registros.get(offset);

        String pathReg = "./Archivos/" + name;

        File f = new File(pathReg);
        BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));

        int size = registros.get(offset).length();

        if (registro.length() == size) {
            //si lenght = size del
            if (!campos.isEmpty()) {
                for (int i = 0; i < campos.size(); i++) {
                    Campos c = campos.get(i);
                    writer.append(c.toString() + ", ");
                }

            }

            try {
                if (!registros.isEmpty()) {
                    writer.append("\n");
                    registros.set(offset, registro);

                    for (int i = 0; i < registros.size(); i++) {
                        /*if(i == offset){
                        writer.append(registro + "\n");
                    }else{
                        writer.append(registros.get(i) + "\n");
                    }*/

                        writer.append(i == offset
                                ? registro + "\n"
                                : registros.get(i) + "\n");
                        writer.append(registros.get(i) + "\n");
                    }

                }
            } catch (Exception NullException) {

            } finally {

                writer.close();

            }

            //si el length del nuevo registro es igual al length del registro a modificar, solo se reemplaza directamente
        } //sino se tendria que actualizar en el avail list el espacio que queda disponible despues de reemplazar el registro
        else {
            int espaciolibre = size - registro.length();

            if (!campos.isEmpty()) {
                for (int i = 0; i < campos.size(); i++) {
                    Campos c = campos.get(i);
                    writer.append(c.toString() + ", ");
                }

                try {
                    if (!registros.isEmpty()) {
                        writer.append("\n");
                        StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < espaciolibre; i++) {
                            sb.append("^");
                        }

                        for (int i = 0; i < registros.size(); i++) {
                            /*if(i == offset){
                        writer.append(registros.get(i) +  sb.toString() + "\n");
                    }else{
                        writer.append(registros.get(i) + "\n");
                    }*/
                            writer.append(i == offset
                                    ? registros.get(i) + sb.toString() + "\n"
                                    : registros.get(i) + "\n");
                        }

                    }
                } catch (Exception NullException) {

                } finally {

                    writer.close();

                }

            }

            registros.set(offset, registro);
            String path = "./Archivos/" + name;
            String avail = "./Archivos/" + path.replaceFirst("[.][^.]+$", "") + ".avail";
            File f2 = new File(avail);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f2));

            try {
                bw.append((offset + 1) + "," + espaciolibre + ";");
                bw.flush();
                bw.close();
            } catch (Exception e) {
            }

        }

    }

    public void delete(int reg) throws IOException {
        String path = "./Archivos/" + name;
        File f = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));

        if (!campos.isEmpty()) {
            for (int i = 0; i < campos.size(); i++) {
                Campos c = campos.get(i);
                writer.append(c.toString() + ", ");
            }

        }
        try {
            if (!registros.isEmpty()) {
                writer.append("\n");
                for (int i = 0; i < registros.size(); i++) {
                    writer.append(i == reg ? "*" + registros.get(i) + "\n" : registros.get(i) + "\n");
                }

            }
        } catch (Exception NullException) {

        }
        writer.close();

        String avail = "./Archivos/" + name.replaceFirst("[.][^.]+$", "") + ".avail";

        File f2 = new File(avail);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f2, true));

        bw.append(reg + "," + registros.get(reg).length() + ";");

        bw.flush();
        bw.close();

    }

    public void save2() throws IOException {
        String path = "./Archivos/" + name + ".txt";
        File f = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        if (!campos.isEmpty()) {
            for (int i = 0; i < campos.size(); i++) {
                writer.append(campos.get(i).getName() + ", ");
            }

        }
        if (!registros.isEmpty()) {
            for (int i = 0; i < registros.size(); i++) {
                writer.append(registros.get(i) + "\n");
            }
        }
        writer.close();
    }

    public Archivos read(String path) throws IOException {
        //String path = "./Archivos/" + name + ".txt";
        File f = new File(path);
        Archivos archivo = new Archivos();
        BufferedReader reader = new BufferedReader(new FileReader(f));
        Scanner sc = new Scanner(f);
        Scanner sc2 = new Scanner(f);

        String header = sc2.nextLine();
        //HEADER
        StringTokenizer token = new StringTokenizer(header, ",", false);

        while (token.hasMoreTokens()) {
            StringTokenizer token2 = new StringTokenizer(token.nextToken(), ":[]", false);
            String fieldname = token2.nextToken();
            boolean key = false;
            if (token2.hasMoreTokens()) {
                String fieldtype = token2.nextToken().substring(1);
                int length = Integer.parseInt(token2.nextToken());
                //System.out.println("antes de aKa");
                if (token2.nextToken().equals("k")) {
                    //System.out.println("entre aKa");
                    key = true;
                }
                archivo.addCampo(new Campos(fieldname, fieldtype, length, key));
            }
        }
        //REGISTROS

        while (sc2.hasNextLine()) {
            String registry = sc2.nextLine();
            archivo.addRegistro(registry);
        }

        archivo.setName(f.getName());

        return archivo;
    }

    public void listar() {
        for (int i = 0; i < campos.size(); i++) {
            System.out.println(campos);
        }
    }

    public void saveXML() throws IOException {
        String path = "./ArchivosXML/" + name + ".XML";
        System.out.println("Name: " + name);
        File f = new File(path);
        String saving = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE XML>\n\n";
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        if (!campos.isEmpty()) {
            for (int i = 0; i < campos.size(); i++) {
                if (campos.get(i).isKey() == true) {
                    saving += "\t<Key>\n";
                } else {
                    saving += "\t<NoKey>\n";
                }
                saving += "\t\t<" + campos.get(i).getType() + " [" + campos.get(i).getLength() + "]> \n";
                saving += "\t\t\t<" + campos.get(i).getName() + ">" + /*registros.get(i)*/ "Text here" + "</" + campos.get(i).getName() + ">\n";

                saving += "\t\t</" + campos.get(i).getType() + ">\n";
                if (campos.get(i).isKey() == true) {
                    saving += "\t</Key>\n";
                } else {
                    saving += "\t</NoKey>\n";
                }
                //saving = "";
                //writer.append(saving);
            }
            writer.write(saving);

        }

        writer.close();
    }

    public void llenarTree(ArrayList disponibles) {
        tree = new BTree();
        for (int i = 0; i < this.llaves.size(); i++) {
            tree.put(llaves.get(i), disponibles.get(i));
            System.out.println(tree.toString());
        }
    }

    public String buscarTree(String key) {
        String val = (String) tree.get(Integer.parseInt(key));

        return val;
    }
    
    public void eliminar(String key)
    {
        tree.remover(Integer.parseInt(key));
        System.out.println(tree.toString());
    }

}
