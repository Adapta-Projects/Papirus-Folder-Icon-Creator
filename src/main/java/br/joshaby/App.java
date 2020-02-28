package br.joshaby;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.DirectoryStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class App {

    static Scanner input = new Scanner(System.in);

    public static String getPWD() throws Exception {
        String pwd = "";
        Process p;
        String s;
        p = Runtime.getRuntime().exec("pwd");
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        pwd = br.readLine();
        return pwd;
    }

    public static Map<String, String> getColors() {
        Map<String, String> colors = new HashMap<>();
        String color = "", oldColor = "";
        while (true) {
            System.out.print("Enter a color in HTML: ");
            color = input.next();
            if (color.equals("exit")) break;
            System.out.print("Enter the color in HTML to be replaced: ");
            oldColor = input.next();
            if (oldColor.equals("exit")) break;
            colors.put(oldColor, color);
        }
        System.out.println("Colors to be modified: ");
        for (String c : colors.keySet()) {
            System.out.printf("\t - %s\n", c);
        }
        System.out.println("New colors: ");
        for (String c : colors.keySet()) {
            System.out.printf("\t - %s\n", colors.get(c));
        }
        return colors;
    }

    public static void changeColor(Path src, String dest, Map<String, String> colors, String nameOfTheColor) throws IOException {
        DirectoryStream<Path> dir = Files.newDirectoryStream(src);
        for (Path c : dir) {
            List<String> lines = Files.readAllLines(c);
            for (int i = 0; i < lines.size(); i ++) {
                for (String oldColor : colors.keySet()) lines.set(i, lines.get(i).replace(oldColor, colors.get(oldColor)));
            }
            String[] oldName = c.getFileName().toString().split("white");
            String name = oldName[0] + nameOfTheColor + oldName[1];
            Files.write(Paths.get(dest + "/" + name), lines, Charset.defaultCharset()
                    , StandardOpenOption.CREATE);
        }
        System.out.println("Operation conclued!");
    }

    public static void init(String src, String dest) throws IOException {
        Map<String, String> colors = getColors();
        System.out.print("Enter a name for the color: ");
        String nameOfTheColor = input.next();
        changeColor(Paths.get(src + "/64x64"), dest + "/64x64", colors, nameOfTheColor);
        changeColor(Paths.get(src + "/48x48"), dest + "/48x48", colors, nameOfTheColor);
        changeColor(Paths.get(src + "/32x32"), dest + "/32x32", colors, nameOfTheColor);
        changeColor(Paths.get(src + "/24x24"), dest + "/24x24", colors, nameOfTheColor);
        changeColor(Paths.get(src + "/22x22"), dest + "/22x22", colors, nameOfTheColor);
    }

    public static void main(String[] args) {
        try {
            System.out.println("Papirus icon folder creator - A Java program for create colorful folders!");
            String IconsPath = getPWD() + "/Icons";
            String outputPath = IconsPath + "/Output";
            init(IconsPath, outputPath);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}