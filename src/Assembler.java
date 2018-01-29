import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler{

    File file;
    Scanner scanner;
    ArrayList<String> lines;
    ArrayList<String> outputlines;
    int linescounter;
    Symbols symbols;

    Assembler(String name){

        linescounter=0;

    //upload file
    file = new File(name+".asm");
    try{
        scanner = new Scanner(file);
    } catch (FileNotFoundException e){
        System.out.println("File not found");
    }
    lines = new ArrayList<>();
        outputlines = new ArrayList<>();
    while(scanner.hasNextLine()){
        lines.add(scanner.nextLine());
    }

    symbols = new Symbols();

    prelist(lines);

    makelist(lines);


        try{
            PrintWriter writer = new PrintWriter(name+".hack", "UTF-8");
            for(int i=0;i<outputlines.size();i++) writer.println(outputlines.get(i));
            writer.close();
        } catch (IOException e){
            System.out.println("problem");
        }



    }


    public void prelist(ArrayList<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line=lines.get(i);
            line=line.trim();


            if(!(line.length()==0||line.charAt(0)=='/')) {

                if (line.charAt(0) == '(') {
                    line = line.replace('(', ' ');
                    line = line.replace(')', ' ');
                    line = line.trim();

                    symbols.put(line,linescounter);


                }  else linescounter++;


            }
        }

    }

    public void makelist(ArrayList<String> lines){
        for (int i=0; i<lines.size();i++){
            String line = lines.get(i);


            if(!(line.length()==0||line.charAt(0)=='/'||line.charAt(0)=='(')) {

                String linep=lines.get(i);
                String[] tr = linep.split("//");
                line= tr[0];

                line=line.trim();


                if(line.charAt(0)=='@'){
                     line=line.replace('@',' ');
                      line=line.trim();

                      try {
                          int x = Integer.parseInt(line);
                          String bin = Integer.toBinaryString(x);
                          int a = bin.length();
                          for (int j = 0; j < 16 - a; j++) {
                              bin = '0' + bin;
                          }
                          outputlines.add(bin);
                      } catch (NumberFormatException e){
                          int value;
                          if(symbols.containsKey(line)) value = symbols.get(line);
                          else{

                                  symbols.addSymbol(line);
                                  value = symbols.get(line);

                          }
                          System.out.println(line+"  "+value);
                          String bin = Integer.toBinaryString(value);
                          int a = bin.length();
                          for (int j = 0; j < 16 - a; j++) {
                              bin = '0' + bin;
                          }
                          outputlines.add(bin);
                      }
                } else if (line.contains("=")&&(line.charAt(0)=='D'||line.charAt(0)=='A'||line.charAt(0)=='M')){
                    char[] out = new char[16];
                    for(int g=0;g<16;g++) out[g]='0';
                    out[0]='1';
                    out[1]='1';
                    out[2]='1';

                    String[] splited =line.split("=");
                    if(splited[0].contains("A")) out[10]='1';
                    if(splited[0].contains("D")) out[11]='1';
                    if(splited[0].contains("M")) out[12]='1';

                    if(splited[1].contains("M")) out[3]='1';

                    if(splited[1].equals("0")) {out[4]='1'; out[6]='1'; out[8]='1';}
                    if(splited[1].equals("1")) {out[4]='1'; out[5]='1'; out[6]='1';out[7]='1'; out[8]='1'; out[9]='1';}
                    if(splited[1].equals("-1")) {out[4]='1'; out[5]='1'; out[6]='1';out[8]='1';}
                    if(splited[1].equals("D")) {out[6]='1';out[7]='1';}
                    if(splited[1].equals("A")||splited[1].equals("M")) {out[4]='1'; out[5]='1';}
                    if(splited[1].equals("!D")) {out[6]='1';out[7]='1';out[9]='1';}
                    if(splited[1].equals("!A")||splited[1].equals("!M")) {out[4]='1'; out[5]='1'; out[9]='1';}
                    if(splited[1].equals("-D")) {out[6]='1';out[7]='1'; out[8]='1'; out[9]='1';}
                    if(splited[1].equals("-A")||splited[1].equals("-M")) {out[4]='1'; out[5]='1';out[8]='1'; out[9]='1';}
                    if(splited[1].equals("D+1")) {out[5]='1'; out[6]='1';out[7]='1'; out[8]='1'; out[9]='1';}
                    if(splited[1].equals("A+1")||splited[1].equals("M+1")) {out[4]='1'; out[5]='1';out[7]='1'; out[8]='1'; out[9]='1';}
                    if(splited[1].equals("D-1")) {out[6]='1';out[7]='1'; out[8]='1';}
                    if(splited[1].equals("A-1")||splited[1].equals("M-1")) {out[4]='1'; out[5]='1'; out[8]='1';}
                    if(splited[1].equals("D+A")||splited[1].equals("D+M")) {out[8]='1';}
                    if(splited[1].equals("D-A")||splited[1].equals("D-M")) {out[5]='1';out[8]='1'; out[9]='1';}
                    if(splited[1].equals("A-D")||splited[1].equals("M-D")) {out[7]='1'; out[8]='1'; out[9]='1';}
                    if(splited[1].equals("D&A")||splited[1].equals("D&M")) {}
                    if(splited[1].equals("D|A")||splited[1].equals("D|M")) {out[5]='1';out[7]='1';out[9]='1';}

                    String a=new String(out);
                    outputlines.add(a);

                }  else if (line.contains(";")){
                    char[] out = new char[16];
                    for(int g=0;g<16;g++) out[g]='0';
                    out[0]='1';
                    out[1]='1';
                    out[2]='1';

                    String[] splited =line.split(";");

                    if(splited[0].contains("M")) out[3]='1';

                    if(splited[0].equals("D")) {out[6]='1';out[7]='1';}
                    if(splited[0].equals("A")||splited[1].equals("M")) {out[4]='1'; out[5]='1';}
                    if(splited[0].equals("0")) {out[4]='1'; out[6]='1';out[8]='1';}

                    if(splited[1].equals("JGT")){ out[15]='1';}
                    if(splited[1].equals("JEQ")){out[14]='1';}
                    if(splited[1].equals("JGE")){out[14]='1'; out[15]='1';}
                    if(splited[1].equals("JLT")){ out[13]='1';}
                    if(splited[1].equals("JNE")){out[13]='1';out[15]='1';}
                    if(splited[1].equals("JLE")){out[13]='1'; out[14]='1';}
                    if(splited[1].equals("JMP")){out[13]='1';out[14]='1'; out[15]='1';}

                    String a=new String(out);
                    outputlines.add(a);
                }

            }

        }
    }



    public static void main(String[] args){
        Assembler asm = new Assembler("Pong");




    }
}
