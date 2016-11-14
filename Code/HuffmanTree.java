import java.util.*;
import java.text.DecimalFormat;
import java.io.*;
public class HuffmanTree 
{
    ArrayList<Character> c=new ArrayList<Character>();
    Node root;
    String decoded="";
    PriorityQueue<Node> pq;
    PriorityQueue<Node> p;
    Comparator<Node> comp=new MyComparator();
    String message;
    String huffCode="";
    Map<Character, String> char_code = new HashMap<Character, String>();
    
    
    public HuffmanTree(String m)
    {
        message=m;
        message=message.trim();
    }
    /* Main function to build Huffman tree */
    public  void HuffmanCode()
    {
        String text=message;
        /* Count the frequency of each character in the string */
        //if the character does not exist in the list add it
        for(int i=0;i<text.length();i++)
        {
            if(!(c.contains(text.charAt(i))))
                c.add(text.charAt(i));
        }        
        int[] freq=new int[c.size()];
        //counting the frequency of each character in the text
        for(int i=0;i<c.size();i++)
            for(int j=0;j<text.length();j++)
                if(c.get(i)==text.charAt(j))
                    freq[i]++;
        /* Build the huffman tree */
        
        root= buildTree(freq);       
        
    }
    /* This function builds the Huffman tree */
    public  Node buildTree(int[] frequency)
    {
        /* Initialize the priority queue */
        pq=new PriorityQueue<Node>(c.size(),comp);  
        p=new PriorityQueue<Node>(c.size(),comp);          
        /* Create leaf node for each unique character in the string */
        for(int i = 0; i  <  c.size(); i++)
        {   
            pq.offer(new Node(c.get(i), frequency[i], null, null));
        } 
        createCopy(pq);            
        /* Until there is only one node in the priority queue */
        while(pq.size() > 1)
        {  
             /* Minimum frequency is kept in the left */
             Node left = pq.poll();
             /* Next minimum frequency is kept in the right */
             Node right = pq.poll();
             /* Create a new internal node as the parent of left and right */
             pq.offer(new Node('\0', left.frequency+right.frequency, left, right));                 
        }         
        /* Return the only node which is the root */
        return pq.poll();
    }     
    public void createCode()
    {
        createCodeRecursive(root,"");
    }
    public void createCodeRecursive(Node t,String c)
    {
        if(t!=null)
        {
            if(t.left==null && t.right==null)
                char_code.put(t.character,c);            
            if(t.left!=null)
                createCodeRecursive(t.left,c+"0");
            if(t.right!=null)
                 createCodeRecursive(t.right,c+"1");
        }           
    }
    public String getEncodedMessage()
    {
        for(int i=0;i<message.length();i++)
            huffCode+=char_code.get(message.charAt(i));
        return huffCode;
    }    
    public String decomp(String code)
    {
    
    	return decompress(root,code,code.length(),0);
    
    }    
    public String decompress(Node t, String c,int l,int i)
    {
            if(l==0)
                return String.valueOf(t.character);
            else
            if(t.left==null && t.right==null)
               return String.valueOf(t.character)+decompress(root,c,l,i);
            else
            if(c.charAt(i) == '0')
                return decompress(t.left,c,--l,++i);
            else
            if(c.charAt(i) == '1')
                return decompress(t.right,c,--l,++i);
            return "";
    }
    
    public String disp()
    {
    	String stringreturn="";
        stringreturn+="The total number of letters are:"+root.frequency;
        stringreturn+="\n-------------------------------------------------------\n\n";
        stringreturn+="Characters \t :        Frequency\n";
        stringreturn+="----------------------------------------------------------------\n";
        Iterator it=p.iterator();        
        while(it.hasNext())
           	stringreturn+=(Node)it.next();     
        stringreturn+="\n-------------------------------------------------------\n";  
        stringreturn+="-------------------------------------------------------\n\n";        
        stringreturn+="Characters \t : \tHuffmanCodes";
        stringreturn+="\n---------------------------------------------------------------";        
        for (Map.Entry<Character, String> entry : char_code.entrySet()) 
        {
    		stringreturn+="\n"+entry.getKey()+" \t : \t "+entry.getValue();
	}		
		stringreturn+="\n\nCompression Ratio: "+calcCompRatio()+"%"+"\n";	
		PrintWriter fout=null;
		try
		{			
			writeToFile(fout);
		}
		catch(IOException e)
		{}
	return stringreturn;			     
    } 
    public void writeToFile(PrintWriter fout)throws IOException
    {
    	fout = new PrintWriter("Files/statistics.txt");
    	fout.println("\nThe total number of letters are:"+root.frequency);
        fout.println("------------------------------------------------------------\n\n");
        fout.println("Characters \t : \t Frequency");
        Iterator it=p.iterator();        
        while(it.hasNext())
           	fout.println("\t"+(Node)it.next());     
        fout.println("-----------------------------------------------------------\n\n");           
        fout.println("Characters \t : \t HuffmanCodes");
        fout.println("-------------------------------------------------------------");        
        for (Map.Entry<Character, String> entry : char_code.entrySet()) 
        {
    		fout.println("\t"+entry.getKey()+" \t : \t "+entry.getValue());
	}
	fout.println("\n\n***************************************"); 		
	fout.println("\n\nCompression Ratio: "+calcCompRatio());
	fout.println("***************************************");
	if(fout !=null)
	     fout.close();		    	
    }  
    public void createCopy(PriorityQueue<Node> q)
    {
    	Iterator it=q.iterator();
        while(it.hasNext())
        {
        	p.offer((Node)it.next());
        } 
    }  
    public double calcCompRatio()
    {
    	double compressionRatio=(1-((huffCode.length()*1)/((double)(root.frequency*8))))*100;
	DecimalFormat cr=new DecimalFormat("#.##");
	compressionRatio=Double.valueOf(cr.format(compressionRatio));
	return compressionRatio;
    } 
}
