import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread{

    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private int ID;
    private static int totalClientes;

    private final String HOST = "192.168.106.128";
    private final int PUERTO = 5000;

    private DataInputStream intxt;
    private DataOutputStream outtxt;
    private InputStream in;
    private FileOutputStream fr;
    private Hash hash = new Hash();
    
    private Log logPrueba = new Log();

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Cliente(int iD) {
        ID = iD;

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
            outtxt.writeUTF("Hola mundo desde el cliente-" + Integer.toString(ID));

            // Recibir mensaje confirmacion del servidor
            String mensaje = intxt.readUTF();
            System.out.println("Se recibio mensaje "+mensaje);

            String[] aux = mensaje.split("-");
            int tamArchivo = Integer.parseInt(aux[1]);
            String nomArchivo = aux[2];

            String textHashArchivo = intxt.readUTF();

            //Recibir archivo
            fr = new FileOutputStream("Lab3_Infracom_TCP/src/ArchivosRecibidos/"+"Cliente"+String.valueOf(this.ID)+"-"+"Prueba-"+ String.valueOf(totalClientes)+".txt");
            
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;

            byte [] b = new byte [tamArchivo];
            in.read(b,0,b.length);
            fr.write(b,0,b.length);

            elapsedTime = System.currentTimeMillis() - startTime;

            //Calular el hash
            File file = new File("Lab3_Infracom_TCP/src/ArchivosRecibidos/"+"Cliente"+String.valueOf(this.ID)+"-"+"Prueba-"+ String.valueOf(totalClientes)+".txt");
            MessageDigest mdigest = MessageDigest.getInstance("MD5");
     
            String hashArchivo = hash.checksum(mdigest, file);

            //Validar integridad
            String exito;
            if (textHashArchivo.equals(hashArchivo))
            {
                System.out.println("Se aseguro la integridad de el archivo enviado ya que: ");
                System.out.println("Hash obtenido: " + textHashArchivo);
                System.out.println("Hash calculado: " + hashArchivo);
                outtxt.writeUTF("La entrega fue exitosa");
                exito="La entrega fue exitosa";
            }
            else
            {
                System.out.println("No se puede asegurar la integridad del mensaje");
                outtxt.writeUTF("La entrega no fue exitosa :(");
                exito="La entrega no fue exitosa :(";
            }

            // Hacer logs
            LocalDateTime date = LocalDateTime.now();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String nombreLog = dtf.format(date) + "-log";

            logPrueba.GenerarLog(nombreLog, nomArchivo);
            logPrueba.GenerarLog(nombreLog, "Tamanio archivo " + String.valueOf(tamArchivo) + "B");
            logPrueba.GenerarLog(nombreLog, "Se genero la conexion para el cliente con ID "+ID);
            logPrueba.GenerarLog(nombreLog, exito);
            logPrueba.GenerarLog(nombreLog, "Tiempo de tranferencia: "+String.valueOf(elapsedTime)+" Ms");

            sc.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
