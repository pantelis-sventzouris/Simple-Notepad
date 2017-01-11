package gr.pantelis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.TextArea;


public class Notepad extends JFrame
{
	private JMenuBar bar;
	private TextArea textArea;
	private Font defaultFont;
	private Font textAreaFont;
	
	private JMenu fileMenu;
	private JMenuItem openButton;
	private JMenuItem saveButton;
	private JMenuItem exitButton;
	
	private JMenu helpMenu;
	private JMenuItem aboutButton;
		
	public Notepad()
	{
		//Set Tile
		super("Simple Notepad");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//instantiate the first top menubar
		bar = new JMenuBar();
		bar.setBackground(Color.ORANGE);
		
		defaultFont = new Font("Arial",Font.BOLD,16);
		
		//create the File menu
		fileMenu = new JMenu("File");		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.setBackground(Color.ORANGE);
		fileMenu.setFont(defaultFont);
		bar.add(fileMenu);
		
		//create the Open item
		openButton = new JMenuItem("Open");
		openButton.setMnemonic(KeyEvent.VK_O);
		openButton.setBackground(Color.ORANGE);
		openButton.setFont(defaultFont);
		fileMenu.add(openButton);		
		
		//create the Save item
		saveButton = new JMenuItem("Save");
		saveButton.setMnemonic(KeyEvent.VK_S);
		saveButton.setBackground(Color.ORANGE);
		saveButton.setFont(defaultFont);
		fileMenu.add(saveButton);
		
		//create the Exit item
		exitButton = new JMenuItem("Exit");
		exitButton.setMnemonic(KeyEvent.VK_X);
		exitButton.setBackground(Color.ORANGE);
		exitButton.setFont(defaultFont);
		fileMenu.add(exitButton);
		
		//create the Help Menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.setBackground(Color.ORANGE);
		helpMenu.setFont(defaultFont);
		bar.add(helpMenu);
		
		aboutButton = new JMenuItem("About");
		aboutButton.setMnemonic(KeyEvent.VK_A);
		aboutButton.setBackground(Color.ORANGE);
		aboutButton.setFont(defaultFont);
		aboutButton.addActionListener(new About());
		helpMenu.add(aboutButton);
		
		//create the text area
		textAreaFont = new Font("Arial",Font.BOLD,12);
		textArea = new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setBackground(Color.ORANGE);
		textArea.setFont(textAreaFont);
		
		//create the layout
		setLayout(new BorderLayout());
		setJMenuBar(bar);
		add(textArea);
		
		//exclusive listeners for buttons
		//open-file listener
		openButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent event){
			JFileChooser open = new JFileChooser();
			int option = open.showOpenDialog(open);
			if(option==JFileChooser.APPROVE_OPTION)
			{
				textArea.setText("");
				try
				{
					Scanner text = new Scanner(new FileReader(open.getSelectedFile().getPath()));
					while(text.hasNext())
					{
						textArea.append(text.nextLine()+"\n");
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}});
		
		//save-file listener
		saveButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent event){
			JFileChooser save = new JFileChooser();
			int option = save.showSaveDialog(save);
			if(option==JFileChooser.APPROVE_OPTION)
			{
				try
				{
					BufferedWriter data = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
					data.write(textArea.getText());
					data.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}});
		
		//exit listener. if not saved, asks for saving first
		exitButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent event){
			if(textArea.getText().isEmpty())
			{
				System.exit(0);
			}
			else
			{
				JFileChooser save = new JFileChooser();
				int option = save.showSaveDialog(save);
				if(option==JFileChooser.APPROVE_OPTION)
				{
					try
					{
						BufferedWriter data = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
						data.write(textArea.getText());
						data.close();
						System.exit(0);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}});
		
		setSize(600,600);
		setVisible(true);
	}
	
	//action listener implementation for about button
	private class About implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String title = "About info";
			String message = "Created by Pantelis Sventzouris";
			message += "\n https://www.github.com/pantelis.sventzouris";
			JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//tests the app
	public static void main(String[] argv)
	{
		new Notepad();
		
	}	
}
