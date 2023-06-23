

import javax.swing.*; /////////IMPORT REQUIRED LIBRARIES
import java.awt.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.StyleConstants;



public class SimpleSearchEngine extends JFrame {

    private final JTextField searchField;
    public JButton clearButton;

    public SimpleSearchEngine() {
        setTitle(" SEARCH - ENGINE");
        setSize(1000, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLUE);
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(30, 20));
        JButton searchButton = new JButton("Search");
       // JButton .setBackground=new Color(0, 204, 255);
        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            JOptionPane.showMessageDialog(SimpleSearchEngine.this, "Search for \"" + query + "\"");
        });

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> searchField.setText(" "));


        JPanel buttonPanel = new JPanel();//
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);//

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());//
        contentPane.add(searchField, BorderLayout.NORTH);//
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SimpleSearchEngine searchEngine = new SimpleSearchEngine();
        searchEngine.setVisible(true);
    }
}
