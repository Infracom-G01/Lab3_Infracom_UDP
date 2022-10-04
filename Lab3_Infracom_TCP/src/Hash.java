import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class Hash {
    
    public String checksum(MessageDigest digest, File file)throws IOException {
        // Get file input stream for reading the file
        // content
        FileInputStream fis = new FileInputStream(file);
 
        // Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;
 
        // read the data from file and update that data in
        // the message digest
        while ((bytesCount = fis.read(byteArray)) != -1)
        {
            digest.update(byteArray, 0, bytesCount);
        };
 
        // close the input stream
        fis.close();
 
        // store the bytes returned by the digest() method
        byte[] bytes = digest.digest();
 
        StringBuilder sb = new StringBuilder();
       
        // loop through the bytes array
        for (int i = 0; i < bytes.length; i++) {
           

            sb.append(Integer
                    .toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
 
        // finally we return the complete hash
        return sb.toString();
    }
}
