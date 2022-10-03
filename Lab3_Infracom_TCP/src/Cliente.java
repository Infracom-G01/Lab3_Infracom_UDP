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
    private final String HOST = "localhost";
    private final int PUERTO = 5000;

    private DataInputStream intxt;
    private DataOutputStream outtxt;
    private InputStream in;
    private FileOutputStream fr;
    private Hash hash = new Hash();

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Cliente(int iD) {
        ID = iD;

    }

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************

    public void conexionInicial(int NumClientes, int tamArchivo, String nomArchivo) throws IOException
    {
        Socket sc = new Socket(HOST, PUERTO);

        outtxt = new DataOutputStream(sc.getOutputStream());

        //Envio un mensaje al cliente
        outtxt.writeUTF(String.valueOf(NumClientes)+"-"+String.valueOf(tamArchivo)+"-"+nomArchivo);
      
        sc.close();
    }


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
            outtxt.writeUTF("¡Hola mundo desde el cliente " + Integer.toString(ID)+"!");

            // Recibir mensaje confirmacion del servidor
            String mensaje = intxt.readUTF();
            System.out.println("Se recibio mensaje "+mensaje);

            String[] aux = mensaje.split("-");
            int tamArchivo = Integer.parseInt(aux[1]);
            String nomArchivo= aux[2];


            //Recibir archivo
            fr = new FileOutputStream("Lab3_Infracom_TCP/src/ArchivosRecibidos/"+ String.valueOf(this.ID) +"-"+ nomArchivo);
            
            byte [] b = new byte [tamArchivo];
            in.read(b,0,b.length);
            fr.write(b,0,b.length);


            File file = new File("Lab3_Infracom_TCP/src/ArchivosRecibidos/"+ String.valueOf(this.ID) +"-"+ nomArchivo);
            MessageDigest mdigest = MessageDigest.getInstance("MD5");
     
            String hashArchivo = hash.checksum(mdigest, file);
            System.out.println("El MD5 checksum de " + "Lab3_Infracom_TCP/src/ArchivosRecibidos/"+ String.valueOf(this.ID) +"-"+ nomArchivo + " es " + hashArchivo);

            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
