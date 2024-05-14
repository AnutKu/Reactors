import Lab4.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUIFrame {
    public void ShowFrame() {
        JFrame frame = new JFrame("PRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        JButton selectFileButton = new JButton("Выбрать файл");

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
                        ReadFromDB.read(selectedFile.getAbsolutePath());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Ошибка при чтении файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(selectFileButton);
        frame.getContentPane().add(panel);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }}
