/*---------------------------------------------CREATING TEXT EDITOR------------------------------------------------------------------------------------------------- */


import java.awt.*;/////////////////////IMPORTING REQUIRED LIBRARIES
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;
public class TextEditor extends JFrame implements ActionListener {
	JTextArea t;    ///creating text editing area
    JFrame f;  //creating frame

	TextEditor()
	{
		f = new JFrame("TEXT EDITOR BY JAGADEESH");      //name for the text editor its customizable
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); //creating a metalic look panel

			
			MetalLookAndFeel.setCurrentTheme(new OceanTheme()); //we are using the ocean theme.
		}
		catch (Exception e) {
		}

		t = new JTextArea();
		JMenuBar mb = new JMenuBar();  // creating a menubar
		JMenu m1 = new JMenu("File");       //create file option

		JMenuItem mi1 = new JMenuItem("New"); // creating required file menu options
		JMenuItem mi2 = new JMenuItem("Open");  //  ''
		JMenuItem mi3 = new JMenuItem("Save");  //
		JMenuItem mi9 = new JMenuItem("Print");  //
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi9.addActionListener(this);

                m1.add(mi1);
                m1.add(mi2);
                m1.add(mi3);
                m1.add(mi9);

		JMenu m2 = new JMenu("Edit");   //creating EDIT menu

		JMenuItem mi4 = new JMenuItem("cut");   //inserting the options in edit option
		JMenuItem mi5 = new JMenuItem("copy");  //
		JMenuItem mi6 = new JMenuItem("paste"); //

                mi4.addActionListener(this);
                mi5.addActionListener(this);
                mi6.addActionListener(this);

                m2.add(mi4);
                m2.add(mi5);
                m2.add(mi6);

		JMenuItem mc = new JMenuItem("close");      //close option on menubar

		mc.addActionListener(this);

		mb.add(m1);
		mb.add(m2);
		mb.add(mc);

		f.setJMenuBar(mb);
		f.add(t);
		f.setSize(500, 500);
		f.show();
	}

	public void actionPerformed(ActionEvent e)   //action of buttons
	{
		String s = e.getActionCommand();

		if (s.equals("cut")) {                  //for cut option in the text
			t.cut();
		}
		else if (s.equals("copy")) {             //for copy option in the text
			t.copy();
		}
		else if (s.equals("paste")) {            //for paste option in the text
			t.paste();
		}
		else if (s.equals("Save")) {            // //for save option in the text
		
			JFileChooser j = new JFileChooser("f:");
			int r = j.showSaveDialog(null);

			if (r == JFileChooser.APPROVE_OPTION) {

		
				File fi = new File(j.getSelectedFile().getAbsolutePath());      //selecting the file directory 

				try {
					FileWriter wr = new FileWriter(fi, false);  // file writer
					BufferedWriter w = new BufferedWriter(wr);  //buffer
					w.write(t.getText());       //write the text
					w.flush();
					w.close();
				}
				catch (Exception evt) {
					JOptionPane.showMessageDialog(f, evt.getMessage());
				}
			}
			else
				JOptionPane.showMessageDialog(f, "the user cancelled the operation");}//if the user cancels
		else if (s.equals("Print")) {       //print the editedd text
			try {
				t.print();
			}
			catch (Exception evt) {
				JOptionPane.showMessageDialog(f, evt.getMessage());
			}
		}
		else if (s.equals("Open")) {            //if user opens the file
			JFileChooser j = new JFileChooser("f:");
			int r = j.showOpenDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {     //if the user selects the file

				File fi = new File(j.getSelectedFile().getAbsolutePath()); //path of the file

				try {
				
					String s1 = "", sl = "";  //

					FileReader fr = new FileReader(fi); //reading the file
					BufferedReader br = new BufferedReader(fr);

					sl = br.readLine(); //initialize line

					while ((s1 = br.readLine()) != null) {  //read data from file
						sl = sl + "\n" + s1;
					}
					t.setText(sl);
				}
				catch (Exception evt) {
					JOptionPane.showMessageDialog(f, evt.getMessage());}}
		
			else            //else condition for user cancels the operation
				JOptionPane.showMessageDialog(f, "the user cancelled the operation");
		}
		else if (s.equals("New")) {      //else if for new task
			t.setText("");
		}
		else if (s.equals("close")) {  //otherwise close
			f.setVisible(false);
		}
	}


	public static void main(String args[])
	{
		Editor e = new Editor();
	}
}
