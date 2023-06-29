package edu.caltech.cs2.project01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class CipherStrip {
    public CipherStrip() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        File cryptogramtxt = new File("/Users/jonayet/IdeaProjects/project01-jlavin/cryptogram.txt");
        Scanner scan = new Scanner(cryptogramtxt);

        String line, betterLine = "";
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) >= 'A' && line.charAt(i) <= 'Z') {
                    betterLine += line.charAt(i);
                }
            }
        }
        FileWriter writer = new FileWriter("/Users/jonayet/IdeaProjects/project01-jlavin/strippedCipher.txt");
        writer.write(betterLine);
        writer.close();
    }
}
