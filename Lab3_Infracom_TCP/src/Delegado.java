import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Delegado{
    
    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private Hash hash = new Hash();

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************

    public void envioDeArchivoYhash(Socket cliente, int tamArchivo, String nomArchivo)
    {
        try {
            OutputStream os = cliente.getOutputStream();
            DataInputStream intxt = new DataInputStream(cliente.getInputStream());
            DataOutputStream outtxt = new DataOutputStream(os);

            System.out.println("Cliente conectado");

            // Leo el mensaje que me envia
            String mensaje = intxt.readUTF();
            System.out.println("Se recibio mensaje " + mensaje);
            
            // Envio mensaje de confirmacion
            outtxt.writeUTF("ACK desde el servidor-" + String.valueOf(tamArchivo)+"-"+nomArchivo);

            File file = new File("Lab3_Infracom_TCP/src/ArchivosEnviados/"+nomArchivo);
            MessageDigest mdigest = MessageDigest.getInstance("MD5");
    
            String hashArchivo = hash.checksum(mdigest, file);
            System.out.println("El MD5 checksum de " + "Lab3_Infracom_TCP/src/ArchivosEnviados/"+nomArchivo + " es " + hashArchivo);

            FileInputStream fr = new FileInputStream("Lab3_Infracom_TCP/src/ArchivosEnviados/"+nomArchivo);
            
            byte [] b = new byte[tamArchivo];
            fr.read(b,0,b.length);
            outtxt.write(b,0,b.length);

            fr.close();

        } catch (IOException | NoSuchAlgorithmException e) {
            //e.printStackTrace();
        }
    
    }
}
