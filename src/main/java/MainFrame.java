import Lab3.*;
import Lab4ParseAndWrite.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import Lab4ParseAndWrite.WriteRactorsTypesFromFile;

public class MainFrame {
    private ReactorTypeHolder reactorTypeHolder = new ReactorTypeHolder();
    private static MainFrame currentMainFrame;
    private Parser parser = new Parser();
    private Map<String, Double> loadFactorMap;

    public void showFrame() {
        currentMainFrame = this;
        JFrame frame = new JFrame();
        frame.setTitle("Лабораторная 3");
        JLabel label = new JLabel("Выберите файл для импорта информации:");
        JButton chooseButton = new JButton("Выбрать файл");
        JButton parsetButton = new JButton("Спарсить данные");
        JButton writeIntoDBButton = new JButton("Залить данные в базу");
        JButton wtitePrisButton = new JButton("Слить спаршенные данные");
        chooseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = null;
                try {
                    fileChooser = new JFileChooser(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile());
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON, XML, YAML Files", "json", "xml", "yaml");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        importFile(selectedFile);
                        displayReactorTree();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                loadFactorMap = reactorTypeHolder.getLoadFactorMap();


            }
        });
        parsetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    parser.start(loadFactorMap);
                    JOptionPane.showMessageDialog(null, "Парсинг завершен");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        wtitePrisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<String>> dataFromPris = parser.getDataList();
                WriteReactorFromPris writer = new WriteReactorFromPris();
                writer.write(dataFromPris);
                WriteCountriesRegions.write();

                JOptionPane.showMessageDialog(null, "Записано");
            }
        });

        writeIntoDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                WriteRactorsTypesFromFile.write(reactorTypeHolder);
            }
        });

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(chooseButton);
        panel.add(parsetButton);
        panel.add(writeIntoDBButton);
        panel.add(wtitePrisButton);
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void importFile(File file) throws IOException {
        JsonFileImporter importerChain = new JsonFileImporter();
        XmlFileImporter xmlImporter = new XmlFileImporter();
        YamlFileImporter yamlImporter = new YamlFileImporter();
        importerChain.setNext(xmlImporter);
        xmlImporter.setNext(yamlImporter);
        importerChain.importFile(file, reactorTypeHolder);
    }

    private void displayReactorTree() {
        JFrame treeFrame = new JFrame("Lab3.Reactor Tree");
        treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Lab3.Reactor Types");
        for (String type : reactorTypeHolder.getReactorMap().keySet()) {
            DefaultMutableTreeNode reactorNode = new DefaultMutableTreeNode(type);
            root.add(reactorNode);
        }

        JTree tree = new JTree(root);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null && selectedNode.getUserObject() instanceof String) {
                    String reactorName = (String) selectedNode.getUserObject();
                    ReactorType reactorType = reactorTypeHolder.getReactorMap().get(reactorName);
                    if (reactorType != null) {
                        showReactorInfo(reactorType);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);
        treeFrame.add(scrollPane, BorderLayout.CENTER);

        treeFrame.pack();
        treeFrame.setLocationRelativeTo(null);
        treeFrame.setVisible(true);
    }

    private void showReactorInfo(ReactorType reactorType) {
        JFrame infoFrame = new JFrame("REACTORS");
        JTextArea infoTextArea = new JTextArea(reactorType.toString());
        infoTextArea.setEditable(false);
        infoFrame.add(infoTextArea);
        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }

    public static MainFrame getCurrentMainFrame() {
        return currentMainFrame;
    }
}
