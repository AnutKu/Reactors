import javax.swing.*;
import java.io.IOException;
import java.util.List;
import Lab4.CreateNewDatabase;

public class Main {
    public static void main(String args[]){
        MainFrame mainFrame = new MainFrame();
        mainFrame.showFrame();
        CreateNewDatabase.createNewDatabase("reactors.sqlite");
}}
