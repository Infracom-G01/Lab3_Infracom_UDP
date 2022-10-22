import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 


public class Delegado{
    
    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private Log logPrueba = new Log();
    private int tamChunk = 64000;

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************

    public void envioDeArchivo(DatagramSocket socketUDP, DatagramPacket peticion, int tamArchivo, String nomArchivo) throws IOException, InterruptedException
    {
        
        byte[] buffer = new byte[tamArchivo];

        try {
            System.out.println("Cliente conectado");

            // Leo el mensaje que me envia
            String mensaje = new String(peticion.getData());
            System.out.println("Se recibio mensaje " + mensaje);
            
            int puertoCliente = peticion.getPort();
            InetAddress direccion = peticion.getAddress();

            Path path = Paths.get("Lab3_Infracom_UDP/src/ArchivosEnviados/"+nomArchivo);
            System.out.println("Lab3_Infracom_UDP/src/ArchivosEnviados/"+nomArchivo);
            buffer = Files.readAllBytes(path); 

            DatagramPacket respuesta = new DatagramPacket(buffer, tamChunk, direccion, puertoCliente);

            // enviar archivo 
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;

            int bytesSent = 0;
            while (bytesSent < tamArchivo) {

                socketUDP.send(respuesta);
                
                bytesSent += tamChunk;
                if ((tamArchivo-bytesSent)>0)
                {
                    respuesta.setData(buffer, bytesSent, tamChunk);
                }
                else
                {
                    respuesta.setData(buffer, bytesSent, (tamArchivo-bytesSent+tamChunk));
                }
                
                
                Thread.sleep(50);
            }

            elapsedTime = System.currentTimeMillis() - startTime;

            // Información para el log
            LocalDateTime date = LocalDateTime.now();
            String numCliente = mensaje.split("-")[1];

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String nombreLog = dtf.format(date) + "-log";

            logPrueba.GenerarLog(nombreLog, "Delegado");
            logPrueba.GenerarLog(nombreLog, nomArchivo);
            logPrueba.GenerarLog(nombreLog, "Tamanio archivo " + String.valueOf(tamArchivo) + "B");
            logPrueba.GenerarLog(nombreLog, "Se genero la conexion para el cliente con ID "+numCliente);
            logPrueba.GenerarLog(nombreLog, "La entrega fue exitosa");
            logPrueba.GenerarLog(nombreLog, "Tiempo de tranferencia: "+String.valueOf(elapsedTime)+" Ms");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}