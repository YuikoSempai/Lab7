package utility;

import commands.Execute_script;
import utility.interfaces.ConsoleInterface;

import java.util.List;
import java.util.Scanner;

public class Console implements ConsoleInterface {
    public Scanner scannerIn;
    public boolean readFromFileStatus;

    public Console(){
        scannerIn = new Scanner(System.in);
        readFromFileStatus = false;
    }

    public void setReadFromFileStatus(boolean b) {
        readFromFileStatus = b;
    }
    public boolean getReadFromFileStatus(){return readFromFileStatus;}

    /**
     * Read next line in Scanner
     * @return next line in scanner
     */
    public String nextLine() {
        if (readFromFileStatus) {
            if (!scannerIn.hasNextLine()) {
                List<String> pathList = Execute_script.getPathList();
                Execute_script.deleteLastPath();
                if(pathList.size()>0)
                {
                    scannerIn = new Scanner(pathList.get(pathList.size()-1));
                }else{
                    setReadFromFileStatus(false);
                    scannerIn = new Scanner(System.in);
                    System.out.println("\t\tEnter the command");
                }

            }
        }
        return scannerIn.nextLine();
    }
    public String next(){
        return scannerIn.next();
    }
    /**
     * Close scanner
     */
    public void close() {
        scannerIn.close();
    }


}
