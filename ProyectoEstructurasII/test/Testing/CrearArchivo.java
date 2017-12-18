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
import java.util.StringTokenizer;
import java.util.Random;

public class CrearArchivo {

    public static void main(String[] args) throws IOException {
        ArrayList<Integer> ID = new ArrayList();
        ArrayList<String> name = new ArrayList();
        ArrayList<String> apellido = new ArrayList();
        ArrayList<Integer> age = new ArrayList();
        ArrayList<Integer> cityID = new ArrayList();
        Archivos a;

        String Girlnames = "Emma,Olivia,Sophia,Ava,Isabella,Mia,Abigail,Emily,Charlotte,Harper,Madison,Amelia,Elizabeth,Sofia,Evelyn,Avery,Chloe,Ella,Grace,Victoria,Aubrey,Scarlett,Zoey,Addison,Lily,Lillian,Natalie,Hannah,Aria,Layla,Brooklyn,Alexa,Zoe,Penelope,Riley,Leah,Audrey,Savannah,Allison,Samantha,Nora,Skylar,Camila,Anna,Paisley,Ariana,Ellie,Aaliyah,Claire,Violet,Stella,Sadie,Mila,Gabriella,Lucy,Arianna,Sarah,Madelyn,Eleanor,Kaylee,Caroline,Hazel,Hailey,Genesis,Kylie,Autumn,Piper,Maya,Nevaeh,Serenity,Peyton,Mackenzie,Bella,Eva,Taylor,Naomi,Aubree,Aurora,Melanie,Lydia,Brianna,Ruby,Katherine,Ashley,Alexis,Alice,Cora,Julia,Madeline,Faith,Annabelle,Alyssa,Isabelle,Vivian,Gianna,Alexandra,Hadley,Eliana,Sophie,London,Elena,Kimberly,Bailey,Maria,Luna,Willow,Jasmine,Kinsley,Valentina,Kayla,Delilah,Andrea,Natalia,Lauren,Morgan,Rylee,Sydney,Adalynn,Mary,Ximena,Jade,Liliana,Brielle,Ivy,Trinity,Josephine,Adalyn,Jocelyn,Emery,Adeline,Jordyn,Ariel,Everly,Lilly,Paige,Isla,Lyla,Makayla,Molly,Emilia,Mya,Kendall,Melody,Isabel,Brooke,Mckenzie,Nicole,Payton,Margaret,Mariah,Eden,Athena,Amy,Norah,Londyn,Valeria,Sara,Aliyah,Angelina,Gracie,Rose,Rachel,Juliana,Laila,Brooklynn,Valerie,Alina,Reese,Elise,Eliza,Alaina,Raelynn,Leilani,Catherine,Emerson,Cecilia,Genevieve,Daisy,Harmony,Vanessa,Adriana,Presley,Rebecca,Destiny,Hayden,Julianna,Michelle,Adelyn,Arabella,Summer,Callie,Kaitlyn,Ryleigh,Lila,Daniela,Quinn,Clara,Rose,Khloe";
        StringTokenizer tk = new StringTokenizer(Girlnames, ",", false);

        while (tk.hasMoreTokens()) {
            name.add(tk.nextToken());
        }

        String MaleNames = "Noah,Liam,Mason,Jacob,William,Ethan,James,Alexander,Michael,Benjamin,Elijah,Daniel,Aiden,Logan,Matthew,Lucas,Jackson,David,Oliver,Jayden,Joseph,Gabriel,Samuel,Carter,Anthony,John,Dylan,Luke,Henry,Andrew,Isaac,Christopher,Joshua,Wyatt,Sebastian,Owen,Caleb,Nathan,Ryan,Jack,Hunter,Levi,Christian,Jaxon,Julian,Landon,Grayson,Jonathan,Isaiah,Charles,Thomas,Aaron,Eli,Connor,Jeremiah,Cameron,Josiah,Adrian,Colton,Jordan,Brayden,Nicholas,Robert,Angel,Hudson,Lincoln,Evan,Dominic,Austin,Gavin,Nolan,Parker,Adam,Chase,Jace,Ian,Cooper,Easton,Kevin,Jose,Tyler,Brandon,Asher,Jaxson,Mateo,Jason,Ayden,Zachary,Carson,Xavier,Leo,Ezra,Bentley,Sawyer,Kayden,Blake,Nathaniel,Ryder,Theodore,Elias,,Tristan,Roman,Leonardo,Camden,Brody,Luis,Miles,Micah,Vincent,Justin,Greyson,Declan,Maxwell,Juan,Cole,Damian,Carlos,Max,Harrison,Weston,Brantley,Braxton,Axel,Diego,Abel,Wesley,Santiago,Jesus,Silas,Giovanni,Bryce,Jayce,Bryson,Alex,Everett,George,Eric,Ivan,Emmett,Kaiden,Ashton,Kingston,Jonah,Jameson,Kai,Maddox,Timothy,Ezekiel,Ryker,Emmanuel,Hayden,Antonio,Bennett,Steven,Richard,Jude,Luca,Edward,Joel,Victor,Miguel,Malachi,King,Patrick,Kaleb,Bryan,Alan,Marcus,Preston,Abraham,Calvin,Colin,Bradley,Jeremy,Kyle,Graham,Grant,Jesse,Kaden,Alejandro,Oscar,Jase,Karter,Maverick,Aidan,Tucker,Avery,Amir,Brian,Iker,Matteo,Caden,Zayden,Riley,August,Mark,Maximus,Brady,Kenneth,Paul";
        StringTokenizer tk1 = new StringTokenizer(MaleNames, ",", false);
        while (tk1.hasMoreTokens()) {
            name.add(tk1.nextToken());
        }
        String apellidos = "Adams,Allen,Anderson,Bailey,Baker,Barnes,Bell,Bennett,Brooks,Brown,Butler,Campbell,Carter,Clark,Collins,Cook,Cooper,Cox,Cruz,Davis,Diaz,Edwards,Evans,Fisher,Flores,Foster,Garcia,Gomez,Gonzalez,Gray,Green,Gutierrez,Hall,Harris,Hernandez,Hill,Howard,Hughes,Jackson,James,Jenkins,Johnson,Jones,Kelly,King,Lee,Lewis,Long,Lopez,Martin,Martinez,Miller,Mitchell,Moore,Morales,Morgan,Morris,Murphy,Myers,Nelson,Nguyen,Ortiz,Parker,Perez,Perry,Peterson,Phillips,Powell,Price,Ramirez,Reed,Reyes,Richardson,Rivera,Roberts,Robinson,Rodriguez,Rogers,Ross,Russell,Sanchez,Sanders,Scott,Smith,Stewart,Sullivan,Taylor,Thomas,Thompson,Torres,Turner,Walker,Ward,Watson,White,Williams,Wilson,Wood,Wright,Young,Rossi,Russo,Ferrari,Esposito,Bianchi,Romano,Colombo,Bruno,Ricci,Greco,Marino,Gallo,De Luca,Conti,Costa,Mancini,Giordano,Rizzo,Lombardi,Barbieri,Moretti,Fontana,Caruso,Mariani,Ferrara,Santoro,Rinaldi,Leone,D'Angelo,Longo,Galli,Martini,Martinelli,Serra,Conte,Vitale,De Santis,Marchetti,Messina,Gentile,Villa,Marini,Lombardo,Coppola,Ferri,Parisi,De Angelis,Bianco,Amato,Fabbri,Gatti,Sala,Morelli,Grasso,Pellegrini,Ferraro,Monti,Palumbo,Grassi,Testa,Valentini,Carbone,Benedetti,Silvestri,Farina,D'Amico,Martino,Bernardi,Caputo,Mazza,Sanna,Fiore,De Rosa,Pellegrino,Giuliani,Rizzi,Di Stefano,Cattaneo,Rossetti,Orlando,Basile,Neri,Barone,Palmieri,Riva,Romeo,Franco,Sorrentino,Pagano,D'Agostino,Piras,Ruggiero,Montanari,Battaglia,Bellini,Castelli,Guerra,Poli,Valente,Ferretti";
        StringTokenizer tk2 = new StringTokenizer(apellidos, ",", false);
        while (tk2.hasMoreTokens()) {
            apellido.add(tk2.nextToken());
            //       System.out.println("Mamado");
        }
        System.out.println(name.toString());
        System.out.println(apellido.toString());

        String path = "PersonFile2";
        File f = new File(path);
        a = new Archivos(path);
        Random rn = new Random();
        for (int i = 5000; i < 10000; i++) {
            ID.add(i);
        }
        System.out.println("Array ID " + ID.toString());
        //StringBuilder sb = new StringBuilder();
        String sb = "";
        String nombre = "";
        Random rnNom = new Random();
        Random rnApe = new Random();
        Random edad = new Random();
        Random city = new Random();
        for (int i = 0; i < 5000; i++) {
            nombre = name.get(rnNom.nextInt(399)) + " " + apellido.get(rnApe.nextInt(199));
            System.out.println("Ultimo for: " + i);
            sb = ID.get(i).toString();
            System.out.println("sb " + sb + " length " + ID.get(i).toString().length());
            if (ID.get(i).toString().length() <= 6) {
                for (int j = ID.get(i).toString().length(); j <= 6; j++) {
                    sb += "*";
                }
            }
            sb += "|" + nombre;
            if (nombre.length() < 30) {
                for (int j = nombre.length(); j < 30; j++) {
                    sb += "*";
                }
            }
            String agetostring = Integer.toString(edad.nextInt(100));
            sb += "|" + agetostring;
            if (agetostring.length() < 3) {
                for (int j = agetostring.length(); j < 3; j++) {
                    sb += "*";
                }
            }
            String citytostring = Integer.toString(city.nextInt(99));
            sb += "|" + citytostring;
            if (citytostring.length() < 2) {
                for (int j = citytostring.length(); j < 2; j++) {
                    sb += "*";
                }
            }
            //sb = ID.get(i).toString() + "|" + nombre + "|" + Integer.toString(edad.nextInt(100)) + "|" + Integer.toString(city.nextInt(99));
            System.out.println(sb);
            a.addRegistro(sb);
        }

        System.out.println(a.getRegistros().toString());
        a.save();
    }

}
