import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import javax.management.timer.Timer;


public class Delegado{
    
    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private Hash hash = new Hash();
    //private Timer timer = new Timer();
    private Log logPrueba = new Log();

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************

    public void envioDeArchivoYhash(Socket cliente, int tamArchivo, String nomArchivo) throws IOException
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
            outtxt.writeUTF(hashArchivo);

            FileInputStream fr = new FileInputStream("Lab3_Infracom_TCP/src/ArchivosEnviados/"+nomArchivo);
            
            byte [] b = new byte[tamArchivo];
            fr.read(b,0,b.length);

            // Información para el log
            LocalDateTime date = LocalDateTime.now();
            String numCliente = mensaje.split("-")[1];

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String nombreLog = dtf.format(date) + "-log";

            logPrueba.GenerarLog(nombreLog, nomArchivo);
            logPrueba.GenerarLog(nombreLog, "Tamanio archivo " + String.valueOf(tamArchivo) + "B");
            logPrueba.GenerarLog(nombreLog, "Se genero la conexion para el cliente con ID "+numCliente);
            
            outtxt.write(b,0,b.length);

            //FALTA ARREGLAR EXITO Y TIEMPO EN TRANFERENCIA
            String exito = intxt.readUTF();
            System.out.println(exito);

            fr.close();
            cliente.close();            

        } catch (IOException | NoSuchAlgorithmException e) {
            //e.printStackTrace();
        }
    }
}