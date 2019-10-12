import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

import java.io.*;

public class Notepad extends JFrame implements ActionListener {
	JFileChooser fc = new JFileChooser();
	
	JLabel l1; //Areas that will contain text
    JTextArea area;
    
    JMenuBar mb;
    
    JMenu fileMenu;
    JMenuItem open, save, saveA, newF, exit;
    
    JMenu editMenu;
    JMenuItem copy, cut, paste, info;
    
    JMenu viewMenu;
    JMenuItem zoomIn, zoomOut;
    
    BufferedReader reader; //Read/Write text files
    PrintWriter writer;
    
    String filePath;
    
    Notepad() 
    {
    	/*
    	 * Initialize Text Area
    	 */
    	setTitle("Notepad--");
    	
    	setSize(1920, 1000);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                updateBounds();
            }
        });
    	
        mb = new JMenuBar();
        
        fileMenu = new JMenu("File");
        
        newF = new JMenuItem("New");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        saveA = new JMenuItem("Save As");
        exit = new JMenuItem("Exit");
        
        fileMenu.add(newF);
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveA);
        fileMenu.add(exit);
        
        newF.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveA.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + InputEvent.SHIFT_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        editMenu = new JMenu("Edit");
        
        copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        copy.setText("Copy");
        cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Cut");
        paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Paste");
        info = new JMenuItem("Info");
        
        copy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        cut.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        paste.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        info.setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        editMenu.add(copy);
        editMenu.add(cut);
        editMenu.add(paste);
        editMenu.add(info);
        
        viewMenu = new JMenu("View");
        
        zoomIn = new JMenuItem("Zoom In");
        zoomOut = new JMenuItem("Zoom Out");
        
        zoomIn.setAccelerator(KeyStroke.getKeyStroke('=', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        zoomOut.setAccelerator(KeyStroke.getKeyStroke('-', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        viewMenu.add(zoomIn);
        viewMenu.add(zoomOut);
        
        open.addActionListener(this);
        save.addActionListener(this);
        saveA.addActionListener(this);
        newF.addActionListener(this);
        exit.addActionListener(this);
        info.addActionListener(this);
        zoomIn.addActionListener(this);
        zoomOut.addActionListener(this);
        
        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(viewMenu);
        
        setJMenuBar(mb);
        
        l1 = new JLabel("Hello there! Please take a look at the menu bar for more information.");
        area = new JTextArea();
        updateBounds();
        add(l1);
        add(area);
        setLayout(new BorderLayout());
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) 
    {
    	if(ae.getSource() == info) //Get info about text
    	{
	        String text = area.getText();
	        String words[] = text.split("\\s");
	        l1.setText("Words: " + words.length + " \t " + "Characters: " + text.length());
    	}
    	else if(ae.getSource() == save && filePath != null) //Save File
    	{
    		String fileLoc = filePath;
    		String text = area.getText();
    		
    		try 
    		{
				writer = new PrintWriter(fileLoc, "UTF-8");
				String lines[] = text.split("\\n");
				for(int i = 0; i < lines.length; i++)
				{
					if(lines[i] != null)
					{
						writer.println(lines[i]);
					}
				}
				writer.close();
				
				this.setTitle(fileLoc);
				l1.setText("Saved: " + fileLoc);
			} 
    		catch (FileNotFoundException e1) 
    		{
				e1.printStackTrace();
	    		l1.setText("File not found: " + fileLoc);
			} 
    		catch (UnsupportedEncodingException e1) 
    		{
				e1.printStackTrace();
				l1.setText("Error with file: " + fileLoc);
			}
    	}
    	else if(ae.getSource() == saveA || (ae.getSource() == save && filePath == null))
    	{
    		int returnVal = fc.showOpenDialog(this);
    		
    		if (returnVal == JFileChooser.APPROVE_OPTION) 
    		{
                File file = fc.getSelectedFile();
                filePath = file.getPath();
                
                String fileLoc = filePath;
        		String text = area.getText();
        		
        		try 
        		{
    				writer = new PrintWriter(fileLoc, "UTF-8");
    				String lines[] = text.split("\\n");
    				for(int i = 0; i < lines.length; i++)
    				{
    					if(lines[i] != null)
    					{
    						writer.println(lines[i]);
    					}
    				}
    				writer.close();
    				
    				this.setTitle(fileLoc);
    				l1.setText("Saved: " + fileLoc);
    			} 
        		catch (FileNotFoundException e1) 
        		{
    				e1.printStackTrace();
    	    		l1.setText("File not found: " + fileLoc);
    			} 
        		catch (UnsupportedEncodingException e1) 
        		{
    				e1.printStackTrace();
    				l1.setText("Error with file: " + fileLoc);
    			}
    		}
    		else
    		{
    			l1.setText("ERROR!");
    		}
    	}
    	else if(ae.getSource() == open) //Open File
    	{
    		int returnVal = fc.showOpenDialog(this);
    		
    		if (returnVal == JFileChooser.APPROVE_OPTION) 
    		{
                File file = fc.getSelectedFile();
                filePath = file.getPath();
                
                String fileLoc = filePath;
        		try
        		{
        			String text = "";
        			reader = new BufferedReader(new FileReader(fileLoc));
        			String line = reader.readLine();
        			while(line != null)
        			{
        				text += line + "\n";
        				line = reader.readLine();
        			}
        			area.setText(text);
        			reader.close();
        			
        			this.setTitle(fileLoc);
            		l1.setText("Opened " + fileLoc);
        		}
        		catch(IOException ie)
        		{
        			ie.printStackTrace();
        			l1.setText("Failed to open " + fileLoc);
        		}
    		}
    		else
    		{
    			l1.setText("Failed to open.");
    		}
			
    	}
    	else if(ae.getSource() == newF)
    	{
    		filePath = null;
    		area.setText("");
    		l1.setText("Created new file.");
    		this.setTitle("New File (Not Saved)");
    	}
    	else if(ae.getSource() == zoomIn)
    	{
    		Font font1 = area.getFont();
    		Font font2 = new Font(font1.getFontName(), font1.getStyle(), font1.getSize()+1);
    		area.setFont(font2);
    	}
    	else if(ae.getSource() == zoomOut)
    	{
    		Font font1 = area.getFont();
    		Font font2 = new Font(font1.getFontName(), font1.getStyle(), font1.getSize()-1);
    		area.setFont(font2);
    	}
    	else if(ae.getSource() == exit) //Close program
    	{
    		System.exit(0);
    	}
    }
    
    public void updateBounds() //Resize elements to accommodate new window size
    {
    	l1.setBounds((int) (getWidth() * .0104), (int) ( getHeight() * 0), (int) (getWidth() * .417), (int) (getHeight() * .03));
    	area.setBounds((int) (getWidth() * (0.0104)), (int) (getHeight() * (.03)), (int) (getWidth() * (.98)), (int) (getHeight() * (.9)));
    }
    
    public static void main(String[] args) {
        new Notepad();
    }
}