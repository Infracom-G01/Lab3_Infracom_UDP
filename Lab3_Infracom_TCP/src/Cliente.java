import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.MessageDigest;
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
    Hash hash = new Hash();

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
            fr = new FileOutputStream("Lab3_Infracom_TCP/src/ArchivosRecibidos/Archivo250-"+ String.valueOf(this.ID) +".txt");
            
            byte [] b = new byte [262144000];
            in.read(b,0,b.length);
            fr.write(b,0,b.length);


            File file = new File("Lab3_Infracom_TCP/src/ArchivosRecibidos/Archivo250-"+ String.valueOf(this.ID) +".txt");
            MessageDigest mdigest = MessageDigest.getInstance("MD5");
     
            String hashArchivo = hash.checksum(mdigest, file);
            System.out.println("El MD5 checksum de " + "Lab3_Infracom_TCP/src/ArchivosRecibidos/Archivo250-"+ String.valueOf(this.ID) +".txt" + " es " + hashArchivo);


            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
