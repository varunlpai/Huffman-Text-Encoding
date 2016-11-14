import java.util.*;
import java.io.*;
import java.*;
public class Client
{
	static HuffmanTree one;
	static int extra;
	
	public static String display()
	{
		return one.disp();
	}
	
	public static boolean isNonPrintable(int c)
    	{
        	boolean nonPrint=false;
        	if(c==0)nonPrint=true;
        	return nonPrint;
    	}
	
	public static String Compress(String file_input,String filename)throws IOException
	{
		String inp="";
		String file_output="Files/"+filename+"_compress.bin";
		FileReader fin=null;
		DataOutputStream fout=null;
		
				
		try
		{
			fin=new FileReader(file_input);
			
	fout = new DataOutputStream(new BufferedOutputStream(new FileOutputStream  (file_output)));
	
			int ch;
			
			while(true)
            		{
                		ch=fin.read();
                		if(ch==-1)
                    			break;
                		if(!isNonPrintable(ch))
                    			inp+=Character.toString((char)ch);
            		}
			
			one=new HuffmanTree(inp);
			one.HuffmanCode();
			one.createCode();
			extra=0;
         		int len;
			String abc=one.getEncodedMessage();
         		len=abc.length();
         		if(abc.length()%8!=0)
        		{
            			for(int i=8;i>len%8;i--)
            			{abc+="0";extra++;}
         		}
			
			String finout="";
         		int m=0;
         		while(m<len)
			{
				String j=abc.substring(m,m+8);
				int bint=0;
				m=m+8;
				bint=Integer.parseInt(j,2);
				char c=(char)bint;
				fout.write(c);
			}
						
		}
		
		
		catch(IOException e)
		{
			System.out.println(e);
		}
		
		
		finally
		{
			if(fin != null)
				fin.close();
			if(fout != null)
				fout.close();			
		}
		
	return file_output;
	
	}
	
	
	public static String Decompress(String file_input,String filename)throws IOException
	{
		String inp="";
		DataInputStream fin=null;
		PrintWriter fout=null;
		String file_output="Files/"+filename+"_decoded.txt";
		try
		{
		
		fin=new DataInputStream(new BufferedInputStream(new FileInputStream(file_input)));
			fout = new PrintWriter(file_output);
						
			while(fin.available() > 0)
			{  
            			char temp=(char)fin.read();
            			int stemp=(int)temp;
				byte b1=(byte)stemp;
            			String btemp=String.format("%8s",Integer.toBinaryString(b1 & 0xFF)).replace(' ','0');
            			inp+=btemp;
			}	
								
         		inp=inp.substring(0,inp.length()-extra);
         		
		}
		
		catch(IOException e)
		{}
		
		finally
		{
			inp=inp.trim();
			String abc=one.decomp(inp);
			fout.println(abc);
			if(fin != null)
				fin.close();
			if(fout != null)
				fout.close();
		}
		
	return file_output;
	
	}
	
}
