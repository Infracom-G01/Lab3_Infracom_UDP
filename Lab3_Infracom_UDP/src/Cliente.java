import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread{

    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private int ID;
    private int tamArchivo;
    private String nomArchivo;
    private static int totalClientes;

    private final String HOST = "localhost";//"192.168.106.128";
    private final int PUERTO = 5000;
    private int tamChunk = 64000;

    private FileOutputStream fr;
    
    private Log logPrueba = new Log();

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Cliente(int iD, int tamArchivo, String nomArchivo) {
        ID = iD;
        this.tamArchivo=tamArchivo;
        this.nomArchivo=nomArchivo;

    }

    public static void establecerTotalClientes(int totalC)
    {
        totalClientes=totalC;
    }

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************

    public void conexionInicial(int NumClientes, int tamArchivo, String nomArchivo) throws IOException
    {
        //Crear buffer :) - hola nick
        byte[] buffer = new byte[100];

        //Obtengo la localizacion de localhost
        InetAddress direccionServidor = InetAddress.getByName(HOST);

        //Creo el socket de UDP
        DatagramSocket socketUDP = new DatagramSocket();
        
        //Envio un mensaje de config al servidor
        String mensaje = String.valueOf(NumClientes)+"-"+String.valueOf(tamArchivo)+"-"+nomArchivo+"-";
        buffer = mensaje.getBytes();

        //Creo un datagrama
        DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO);

        //Lo envio con send
        System.out.println("Envio el datagrama");
        socketUDP.send(pregunta);

        socketUDP.close();
    }

    @Override
    public void run() 
    {
        try {
            //Creo el socket para conectarme con el cliente
            //Obtengo la localizacion de localhost
            InetAddress direccionServidor = InetAddress.getByName(HOST);
 
            //Creo el socket de UDP
            DatagramSocket socketUDP = new DatagramSocket();

            //Envio un mensaje al cliente
            //"Hola mundo desde el cliente-" + Integer.toString(ID));
            String mensaje = "¡Hola mundo desde el cliente-"+ Integer.toString(ID)+"-";

            byte[] buffer = new byte[10];
            buffer = mensaje.getBytes();

            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO);
            
            //Lo envio con send
            socketUDP.send(pregunta);
  
            //Recibir archivo
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;

            //Recibir archivo
            byte[] archivo = new byte[0];
            
            int i = 0;
            int bytesReceived = 0;
            while (bytesReceived<tamArchivo)
            {
                buffer = new byte[tamChunk];
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                socketUDP.receive(peticion);
                System.out.println("Recibo la peticion");

                byte[] chunksArchivo = peticion.getData();
                

                byte[] res = new byte[ archivo.length + chunksArchivo.length ];

                System.arraycopy( archivo, 0, res, 0, archivo.length);
                System.arraycopy( chunksArchivo, 0, res, archivo.length, chunksArchivo.length );

                archivo = res;

                bytesReceived+=tamChunk;

                i+=1;
                System.out.print("Voy en: ");
                System.out.println(i);
            }

            fr = new FileOutputStream("Lab3_Infracom_UDP/src/ArchivosRecibidos/"+"Cliente"+String.valueOf(this.ID)+"-"+"Prueba-"+ String.valueOf(totalClientes)+".txt");
            fr.write(archivo);
            fr.close();

            System.out.println("Logré salir");
            elapsedTime = System.currentTimeMillis() - startTime;

            // Hacer logs
            LocalDateTime date = LocalDateTime.now();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String nombreLog = dtf.format(date) + "-log";

            logPrueba.GenerarLog(nombreLog, "Cliente");
            logPrueba.GenerarLog(nombreLog, nomArchivo);
            logPrueba.GenerarLog(nombreLog, "Tamanio archivo " + String.valueOf(tamArchivo) + "B");
            logPrueba.GenerarLog(nombreLog, "Se genero la conexion para el cliente con ID "+ID);
            logPrueba.GenerarLog(nombreLog, "La entrega fue exitosa");
            logPrueba.GenerarLog(nombreLog, "Tiempo de tranferencia: "+String.valueOf(elapsedTime)+" Ms");
            socketUDP.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
