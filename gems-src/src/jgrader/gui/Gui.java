package jgrader.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Gui {
  private String projectDirectory;
  private JFileChooser fc;
  private JPanel p;
  private JLabel labelOne;
  private JLabel labelLoad;
  private JButton buttonOne;
  private JButton buttonTwo;
  private JTextArea textArea;
  private JScrollPane jsp;
  private JFrame f;
  private boolean done;
  private boolean redo;

  public Gui() {
    f = new JFrame();
    textArea = new JTextArea("...");
    jsp = new JScrollPane(textArea);
    p = new JPanel();
    fc = new JFileChooser(System.getProperty("user.dir"));
    labelOne = new JLabel();
    labelLoad = new JLabel();
    buttonOne = new JButton();
    buttonTwo = new JButton();
    done = false;
    redo = false;
  }

  public void setGui() {
    buttonOne.setVisible(true);

    f.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 50));
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setPreferredSize(new Dimension(750, 800));
    f.setResizable(false);

    jsp.setPreferredSize(new Dimension(650, 600));

    textArea.setBackground(Color.BLACK);
    textArea.setForeground(Color.LIGHT_GRAY);
    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
    textArea.setEditable(false);
    textArea.setLineWrap(true);

    labelOne.setText("Please select the directory of the files you would like to test: ");
    p.add(labelOne);

    buttonOne.setText("Select Directory");
    p.add(buttonOne);

    f.add(p);
    f.add(jsp);
    f.pack();
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
  
  public void setAgain() {
	  buttonOne.setText("Select Directory");
	  buttonOne.setVisible(true);
	  labelOne.setText("Please select the directory of the files you would like to test: ");
  }

  public void appendTextArea(String str) {
    textArea.append(str);
  }

  public void setRedoButton(String str) {
	labelLoad.setVisible(false);
    labelOne.setText(str);
    labelOne.setVisible(true);
    if (str.contains("SUCCESS!")) {
    	f.remove(jsp);
    	f.setPreferredSize(new Dimension(750, 150));
    	buttonTwo.setText("Check More Files");
    }
    buttonTwo.setText("Check More Files");
    p.add(buttonTwo);
    f.pack();
  }

  public boolean getRedo() {
	redo = false;
    buttonTwo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        buttonTwo.setVisible(false);
        f.dispose();
        redo = true;
      }
    });
    while (redo == false) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return redo;
  }
  
  public void resizeFrame() {
	  f.removeAll();
	  buttonTwo.setText("Check More Files");
	  f.add(buttonTwo);
	  labelOne.setText("SUCCESS! There were no errors found!");
	  f.add(labelOne);
	  f.setPreferredSize(new Dimension(750, 160));
  }

  public String getProjectDirectory() {
	done = false;
    buttonOne.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        int rv = fc.showOpenDialog(null);
        if (rv == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          projectDirectory = file.getPath().substring(0, file.getPath().length() - file.getName().length() - 1);
          buttonOne.setVisible(false);
          labelOne.setVisible(false);
          ClassLoader cldr = this.getClass().getClassLoader();
          java.net.URL imageURL   = cldr.getResource("img/ajax-loader.gif");
          ImageIcon load = new ImageIcon(imageURL);
          labelLoad = new JLabel("Printing Error Suggestions...", load, JLabel.CENTER);
          p.add(labelLoad);
          textArea.setText(null);
          done = true;
        }
      }
    });
    while (done == false) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return projectDirectory;
  }
}
