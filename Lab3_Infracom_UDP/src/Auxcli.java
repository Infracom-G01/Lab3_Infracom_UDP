import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Auxcli {
    public static void main(String[] args) {
 
        //puerto del servidor
        final int PUERTO_SERVIDOR = 5000;
        //buffer donde se almacenara los mensajes
        byte[] buffer = new byte[10];
 
        try {
            //Obtengo la localizacion de localhost
            InetAddress direccionServidor = InetAddress.getByName("localhost");
 
            //Creo el socket de UDP
            DatagramSocket socketUDP = new DatagramSocket();
 
            String mensaje = "¡Hola mundo desde el cliente!";
 
            //Convierto el mensaje a bytes
            buffer = mensaje.getBytes();
 
            //Creo un datagrama
            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
 
            //Lo envio con send
            System.out.println("Envio el datagrama");
            socketUDP.send(pregunta);


            byte[] archivo = new byte[0];
            
            int i = 0;
            int bytesReceived = 0;
            while (bytesReceived<262144000)
            {
                buffer = new byte[64000];
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                socketUDP.receive(peticion);
                System.out.println("Recibo la peticion");

                byte[] chunksArchivo = peticion.getData();
                

                byte[] res = new byte[ archivo.length + chunksArchivo.length ];

                System.arraycopy( archivo, 0, res, 0, archivo.length);
                System.arraycopy( chunksArchivo, 0, res, archivo.length, chunksArchivo.length );

                archivo = res;

                bytesReceived+=64000;

                i+=1;
                System.out.println("Voy en");
                System.out.println(i);
            }


            FileOutputStream fr = new FileOutputStream("Lab3_Infracom_UDP/src/ArchivosRecibidos/"+"Cliente.txt");

            fr.write(archivo);
            fr.close();
            
            System.out.println(mensaje);
 
            //cierro el socket
            socketUDP.close();
 
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
 
    }
    
}
