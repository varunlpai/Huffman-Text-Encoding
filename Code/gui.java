//pls run the file using command:
//java -Xss512m gui 
//or else it results in stackoverflow exception

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class gui extends JPanel implements ActionListener 
{

    JButton selectButton, compressButton,decompressButton,closeButton;
    JTextArea log;
    JFrame frame;
    String fullpath;   
    JFileChooser fc;
    HuffmanTree filetodecompress;
    File file1,file2;
    

    public gui() 
    {
        super(new BorderLayout());
	log = new JTextArea(20,40);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        fc = new JFileChooser();

        
        selectButton = new JButton("Select File"); 
        selectButton.addActionListener(this);

        
        compressButton = new JButton("Compress");
        compressButton.addActionListener(this);
        compressButton.setEnabled(false);
        
        decompressButton = new JButton("Decompress");
        decompressButton.addActionListener(this);
	decompressButton.setEnabled(false);
	
	closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        closeButton.setEnabled(true);
        
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.add(selectButton);
        buttonPanel.add(compressButton);
        buttonPanel.add(decompressButton);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e)
     {

        if (e.getSource() == selectButton) 
        {
        	log.append("\nNEW FILE:\n");
 	    int returnVal1 = fc.showOpenDialog(gui.this);

            if (returnVal1 == JFileChooser.APPROVE_OPTION)
            {
                file1 = fc.getSelectedFile();
                
            	compressButton.setEnabled(true);
            	
            	fullpath=file1.getAbsolutePath();
            	
            	log.append("\nFile selected: " + fullpath + "." +"\n");
            	
                JOptionPane.showMessageDialog(null, "File Selected Successfully");
            }
            else log.append("Select command cancelled by user." +"\n");
            
	    log.setCaretPosition(log.getDocument().getLength());
	
	}
	
	else if (e.getSource() == compressButton)
	{
			String destinationfile="";
			try {destinationfile=Client.Compress(fullpath,file1.getName());}
			catch(Exception m){}
			log.append("\nCOMPRESSION:\n");
			log.append("........................\n");
			JOptionPane.showMessageDialog(null, "File Compression Successful!!");
			log.append("compressed filelocation: "+destinationfile+"\n\n");
			log.append("Compression Statistics:\n"+Client.display()+"\n\n");
			decompressButton.setEnabled(true);
			log.setCaretPosition(log.getDocument().getLength());
			compressButton.setEnabled(false);
		
	}
	
	else if (e.getSource() == decompressButton)
	{
		
		decompressButton.setEnabled(false);
	
		
	    	int returnVal2 = fc.showOpenDialog(gui.this);

            	if (returnVal2 == JFileChooser.APPROVE_OPTION)
            	{
		        file2 = fc.getSelectedFile();
		        File f=null;
		    	String destinationdecompress="";
		    	String decompresspath=file2.getAbsolutePath();
		    	log.append("\nDECOMPRESSION:\n");
			log.append(".........................\n");
		   	try { destinationdecompress=Client.Decompress(decompresspath,file2.getName());}
		   	catch(Exception m){}
		    
		    	log.append("File selected for decompression: " + decompresspath + "." +"\n");
		        JOptionPane.showMessageDialog(null, "File Decompression Successful!!\nPlease 				check the directory");
		        try
		        {	
		        	f = new File("Files/");
                     		Desktop.getDesktop().open(f);
                     	}
                     	catch(Exception r){}
		        log.append("Decompressed File saved in location:"+destinationdecompress+"\n");
            	}
            	else 
            	{
            		log.append("Decompress command cancelled by user." +"\n");
            		decompressButton.setEnabled(true);
            	}
            	
	    	log.setCaretPosition(log.getDocument().getLength());
		
	}
	
    
    
    	else if(e.getSource()==closeButton)
    	{
        	System.exit(0);
    	}
	
    }


    
    private  void createAndShowGUI() 
    {
    
        frame = new JFrame("HUFFMAN CODING");
        frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

     	frame.add(new gui());

      	frame.pack();
        frame.setVisible(true);
        
    }

    public static void main(String[] args) 
    {
      	gui x=new gui();
        x.createAndShowGUI();
    }
}
