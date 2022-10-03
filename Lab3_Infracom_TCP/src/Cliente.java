import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread{

    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private int ID;
    final String HOST = "localhost";
    final int PUERTO = 5000;
    InputStream in;
    FileOutputStream fr;

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Cliente(int iD) {
        ID = iD;

    }

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************

    @Override
    public void run() 
    {
        try {
            //Creo el socket para conectarme con el cliente
            Socket sc = new Socket(HOST, PUERTO);
            
            in = sc.getInputStream();
            fr = new FileOutputStream("Lab3_Infracom_TCP/src/ArchivosRecibidos/recibido.txt");
            
            //Envio un mensaje al cliente
            //out.writeUTF("¡Hola mundo desde el cliente!" + Integer.toString(ID));
 
            //Recibo el mensaje del servidor
            //String mensaje = in.readUTF();
 
            //System.out.println(mensaje);
            byte [] b = new byte [20002];
            in.read(b,0,b.length);
            fr.write(b,0,b.length);
            sc.close();
 
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
