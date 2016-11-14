# Text Encoder Using Huffman Trees

Huffman encoding is done by creating a Huffman Tree based on the frequency of 
a character's appearance in the text file.

Each Character is replaced by a Binary String.

Since the Binary String on an average is usually less than 8 bits, there will be
a significant decrease in file size.

Execute gui.java to begin Huffman Compression.

Note:

    •  The Binary String could not be written in a separate binary file. Instead the 
       Binary String has been grouped into sets of 8 bits and their corresponding
       character has been written.
       
    •  There is no provision to write the character mapping. Hence, a compressed fie must
       be decompressed in the same execution, and also without compressing any other file
       in between. [ Since that would over-write the previous character mapping ]
