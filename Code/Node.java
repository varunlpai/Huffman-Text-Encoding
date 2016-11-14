public class Node
{
    public  char character;
    public  int frequency;
    public  Node left;
    public  Node right;
    public String code;
    Node(char character, int frequency, Node left, Node right)
    {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;        
    }     
    public boolean isLeaf()
    {
        /* Return true if the node has no left and right child */
        return (left == null  &&  right == null);
    }
    @Override
    public String toString()
    {
        return character+" \t : \t "+frequency+"\n";
    }
}
