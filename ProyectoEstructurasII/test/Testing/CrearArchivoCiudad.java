/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Clases.Archivos;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CrearArchivoCiudad {

    public static void main(String[] args) throws IOException {
        ArrayList<Integer> cityID = new ArrayList();
        ArrayList<String> cityName = new ArrayList();

        Archivos a;

        String names = "Seoul,São Paulo,Jakarta,Karachi,Moscow,Istanbul,Mexico City,Shanghai,Tokyo,New York ,Bangkok,Beijing,Delhi,London,Hong Kong,Cairo,Tehran,Bogota,Bandung,Tianjin,Lima,Rio De Janeiro,Lahore,Bogor,Santiago,StPetersburg,Shenyang,Calcutta,Wuhan,Sydney,Guangzhou,Singapore,Madras,Baghdad,Pusan,Los Angeles ,Yokohama,Dhaka,Berlin,Alexandria,Bangalore,Malang,Hyderabad,Chongqing,Ho Chi Minh City,Haerbin,Ankara,Buenos Aires,Chengdu,Ahmedabad,Casablanca,Chicago ,Xian,Madrid,Surabaya,Pyongyang,Nanjing,Kinshasa,Roma,Taipei,Osaka,Kiev,Yangon,Toronto,Zibo,Dalian,Taegu,Addis Ababa,Jinan,Salvador,Inchon,Semarang,Giza,Changchun,Havana,Nagoya,Belo Horizonte,Paris,Tashkent,Fortaleza,Sukabumi,Cali,Guayaquil,Qingdao,Izmir,Cirebon,Taiyuan,Brasilia,Bucuresti,Faisalabad,Quezon City,Medan,Houston,Abidjan,Mashhad,Medellín,Kanpur,Budapest,Caracas,Tegucigalpa";
        StringTokenizer tk2 = new StringTokenizer(names, ",", false);
        while (tk2.hasMoreTokens()) {
            cityName.add(tk2.nextToken());
            //       System.out.println("Mamado");
        }
        System.out.println(cityName.toString());

        String path = "CityFile";
        File f = new File(path);
        a = new Archivos(path);
        Random rn = new Random();
        for (int i = 0; i < 100; i++) {
            cityID.add(i);
        }
        System.out.println("Array ID " + cityID.toString());
        //StringBuilder sb = new StringBuilder();
        String sb = "";

        for (int i = 0; i < 100; i++) {

            System.out.println("Ultimo for: " + i);
            sb = cityID.get(i).toString();
            //System.out.println("sb " + sb + " length " + ID.get(i).toString().length());
            if (cityID.get(i).toString().length() <= 2) {
                for (int j = cityID.get(i).toString().length(); j <= 6; j++) {
                    sb += "*";
                }
            }
            sb += "|" + cityName.get(i);
            if (cityName.get(i).length() < 30) {
                for (int j = cityName.get(i).length(); j < 30; j++) {
                    sb += "*";
                }
            }

            System.out.println(sb);
            a.addRegistro(sb);
        }

        System.out.println(a.getRegistros().toString());
        a.save();
    }

}
