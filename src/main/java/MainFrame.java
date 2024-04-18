import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainFrame {
    private ReactorHolder reactorHolder = new ReactorHolder();

    public static void main(String[] args) {
        showFrame();
    }

    public static void showFrame() {
        MainFrame mainFrame = new MainFrame();
        JFrame frame = new JFrame();
        frame.setTitle("Лабораторная 3");
        JLabel label = new JLabel("Выберите файл для импорта информации:");
        JButton chooseButton = new JButton("Выбрать файл");

        chooseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON, XML, YAML Files", "json", "xml", "yaml");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        mainFrame.importFile(selectedFile);
                        mainFrame.displayReactorTree();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(chooseButton);
        frame.add(panel);
        frame.setSize(300, 100);
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

        importerChain.importFile(file, reactorHolder);
    }

    private void displayReactorTree() {
        JFrame treeFrame = new JFrame("Reactor Tree");
        treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Reactor Types");
        for (String type : reactorHolder.getReactorMap().keySet()) {
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
                    Reactor reactor = reactorHolder.getReactorMap().get(reactorName);
                    if (reactor != null) {
                        showReactorInfo(reactor);
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

    private void showReactorInfo(Reactor reactor) {
        JFrame infoFrame = new JFrame("Reactor Info");
        JTextArea infoTextArea = new JTextArea(reactor.toString());
        infoTextArea.setEditable(false);
        infoFrame.add(infoTextArea);
        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }
}