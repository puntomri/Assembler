import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class Symbols extends HashMap<String,Integer> {


    int counter;



    Symbols(){
        counter=16;

        this.put("SP",0);
        this.put("LCL",1);
        this.put("ARG",2);
        this.put("THIS",3);
        this.put("THAT",4);
        this.put("SCREEN",16384);
        this.put("KBD",24576);

        for (int i = 0;i<16;i++) {
            this.put("R"+i,i);

        }




    }


    public void addSymbol(String name){

        if (!this.containsKey(name)){

            this.put(name,counter);

            counter++;

        }



    }





}





