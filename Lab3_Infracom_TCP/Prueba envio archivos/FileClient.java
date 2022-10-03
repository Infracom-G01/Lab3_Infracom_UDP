import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class FileClient{

    public static void main(String[] args) throws Exception{
        byte [] b = new byte [20002];
        Socket sr = new Socket("localhost",6777);
        InputStream is = sr.getInputStream();
        FileOutputStream fr = new FileOutputStream("hola.txt");
        is.read(b,0,b.length);
        fr.write(b,0,b.length);

        sr.close();
        fr.close();
    }
}