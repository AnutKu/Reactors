import Lab4.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

public class GUIFrame {
    private  ReactorHolder reactorHolder = new ReactorHolder();
    public void ShowFrame() {
        JFrame frame = new JFrame("PRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton selectFileButton = new JButton("Выбрать файл");
        JButton countryButton = new JButton("Агрегация по странам");
        JButton operatorButton = new JButton("Агрегация по операторам");
        JButton regionButton = new JButton("Агрегация по региона");
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
                        reactorHolder.calculateConsumptionPerYear();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Ошибка при чтении файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        countryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, HashMap<Integer, Double>> agregatePerCountryMap = reactorHolder.agregatePerCountry();
                String[] columnNames = {"Страна", "Год", "Объем ежегодного потребеления"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                for (HashMap.Entry<String, HashMap<Integer, Double>> entry : agregatePerCountryMap.entrySet()) {
                    String country = entry.getKey();
                    HashMap<Integer, Double> yearData = entry.getValue();
                    for (int year = 2014; year <= 2024; year++) {
                        Object[] rowData = new Object[3];
                        rowData[0] = country;
                        rowData[1] = year;
                        rowData[2] = yearData.getOrDefault(year, 0.0);
                        model.addRow(rowData);
                    }
                }
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                JFrame tableFrame = new JFrame("Суммарное потребление по уровню агрегации: страна");
                tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tableFrame.setSize(800, 600);
                tableFrame.add(scrollPane);
                tableFrame.setLocationRelativeTo(null);
                tableFrame.setVisible(true);
            }
        });

        operatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, HashMap<Integer, Double>> agregatePerCountryMap = reactorHolder.agregatePerOperator();
                String[] columnNames = {"Оператор", "Год", "Объем ежегодного потребеления"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                for (HashMap.Entry<String, HashMap<Integer, Double>> entry : agregatePerCountryMap.entrySet()) {
                    String country = entry.getKey();
                    HashMap<Integer, Double> yearData = entry.getValue();
                    for (int year = 2014; year <= 2024; year++) {
                        Object[] rowData = new Object[3];
                        rowData[0] = country;
                        rowData[1] = year;
                        rowData[2] = yearData.getOrDefault(year, 0.0);
                        model.addRow(rowData);
                    }
                }
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                JFrame tableFrame = new JFrame("Суммарное потребление по уровню агрегации: оператор");
                tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tableFrame.setSize(800, 600);
                tableFrame.add(scrollPane);
                tableFrame.setLocationRelativeTo(null);
                tableFrame.setVisible(true);
            }
        });

        regionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, HashMap<Integer, Double>> agregatePerCountryMap = reactorHolder.agregatePerRegion();
                String[] columnNames = {"Регион", "Год", "Объём ежегодного потребеления"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                for (HashMap.Entry<String, HashMap<Integer, Double>> entry : agregatePerCountryMap.entrySet()) {
                    String country = entry.getKey();
                    HashMap<Integer, Double> yearData = entry.getValue();
                    for (int year = 2014; year <= 2024; year++) {
                        Object[] rowData = new Object[3];
                        rowData[0] = country;
                        rowData[1] = year;
                        rowData[2] = yearData.getOrDefault(year, 0.0);
                        model.addRow(rowData);
                    }
                }
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                JFrame tableFrame = new JFrame("Суммарное потребление по уровню агрегации: регион");
                tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tableFrame.setSize(800, 600);
                tableFrame.add(scrollPane);
                tableFrame.setLocationRelativeTo(null);
                tableFrame.setVisible(true);
            }
        });
        panel.add(selectFileButton);
        panel.add(countryButton);
        panel.add(operatorButton);
        panel.add(regionButton);
        frame.getContentPane().add(panel);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }}
