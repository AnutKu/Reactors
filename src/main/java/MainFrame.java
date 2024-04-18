import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainFrame {
    private static Map<String, Reactor> reactorMap;

    public static void showFrame() {
        JFrame frame = new JFrame();
        JLabel label = new JLabel("Имя листа:");
        JButton chooseButton = new JButton("Выбрать файл");
        reactorMap = new HashMap<>();

        chooseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
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
            }
        });

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(chooseButton);
        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void importFile(File file) throws IOException {
        JsonFileImporter importerChain = new JsonFileImporter();
        XmlFileImporter xmlImporter = new XmlFileImporter();
        YamlFileImporter yamlImporter = new YamlFileImporter();

        importerChain.setSuccessor(xmlImporter);
        xmlImporter.setSuccessor(yamlImporter);

        importerChain.importFile(file, reactorMap);
    }

    private static void displayReactorTree() {
        JFrame treeFrame = new JFrame("Reactor Tree");
        treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Reactor Types");
        for (String type : reactorMap.keySet()) {
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
                    Reactor reactor = reactorMap.get(reactorName);
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

    private static void showReactorInfo(Reactor reactor) {
        JFrame infoFrame = new JFrame("Reactor Info");
        JTextArea infoTextArea = new JTextArea(reactor.toString());
        infoTextArea.setEditable(false);
        infoFrame.add(infoTextArea);
        infoFrame.pack();
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setVisible(true);
    }
}



