package commands;

import utility.Console;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Execute_script {

    private final Console console;
    static final List<String> pathList = new ArrayList<>();

    public Execute_script(Console console){
        this.console = console;
    }

    public static void deleteLastPath() {
        pathList.remove(pathList.size()-1);
    }

    public static List<String> getPathList(){
        return pathList;
    }

    public void execute(String filePath) {
        try {
            if(!pathList.contains(filePath)) {
                File file = new File(filePath);
                Path path = Paths.get(filePath);
                console.scannerIn = new Scanner(path);
                console.setReadFromFileStatus(true);
                pathList.add(filePath);
            }else{
                System.out.println("This script is already running");
            }
        }catch (Exception e){
            System.out.println(e.getClass());
            System.out.println("Wrong file path");
        }
    }
}
