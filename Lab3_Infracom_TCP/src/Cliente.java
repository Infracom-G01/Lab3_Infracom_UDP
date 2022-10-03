import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    DataInputStream intxt;
    DataOutputStream outtxt;
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

            intxt = new DataInputStream(in);
            outtxt = new DataOutputStream(sc.getOutputStream());

            //Envio un mensaje al cliente
            outtxt.writeUTF("¡Hola mundo desde el cliente!" + Integer.toString(ID));

            // Recibir mensaje confirmacion del servidor

            String mensaje = intxt.readUTF();
            System.out.println("Se recibio mensaje "+mensaje);

            //Recibir archivo
            fr = new FileOutputStream("Lab3_Infracom_TCP/src/ArchivosRecibidos/recibido.txt");
            
            byte [] b = new byte [20002];
            in.read(b,0,b.length);
            fr.write(b,0,b.length);
            sc.close();
 
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
