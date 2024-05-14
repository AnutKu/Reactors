import Lab4.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUIFrame {
    private  ReactorHolder reactorHolder = new ReactorHolder();
    public void ShowFrame() {
        JFrame frame = new JFrame("PRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();


        JButton selectFileButton = new JButton("Выбрать файл");
        JButton testButton = new JButton("Посмотреть список");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл SQLite");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SQLite Database (*.sqlite, *.db)", "sqlite", "db");
        fileChooser.setFileFilter(filter);
        String initialDirectory = "D:\\2_курс_4_семестр\\Программирование\\Reactors\\src\\main\\resources";
        fileChooser.setCurrentDirectory(new File(initialDirectory));
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        ReadFromDB.read(selectedFile.getAbsolutePath(), reactorHolder);
                        JOptionPane.showMessageDialog(null, "Данные считаные");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Ошибка при чтении файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(reactorHolder.getReactorList());
            }
        });
        panel.add(selectFileButton);
        panel.add(testButton);
        frame.getContentPane().add(panel);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }}
