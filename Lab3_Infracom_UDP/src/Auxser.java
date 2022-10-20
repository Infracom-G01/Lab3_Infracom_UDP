import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path; 
import java.nio.file.Paths; 

public class Auxser {

    public static void main(String[] args) {
 
        final int PUERTO = 5000;
        byte[] buffer = new byte[10];
 
        try {
            System.out.println("Iniciado el servidor UDP");
            //Creacion del socket
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);
 
            //Siempre atendera peticiones
            while (true) {
                 
                //Preparo la respuesta
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                 
                //Recibo el datagrama
                socketUDP.receive(peticion);
                System.out.println("Recibo la informacion del cliente");
                 
                //Convierto lo recibido y mostrar el mensaje
                String mensaje = new String(peticion.getData());
                System.out.println(mensaje);
 
                //Obtengo el puerto y la direccion de origen
                //Sino se quiere responder, no es necesario
                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();
 
                //mensaje = "¡Hola mundo desde el servidor!";
                //buffer = mensaje.getBytes();

                Path path = Paths.get("Lab3_Infracom_UDP/src/ArchivosEnviados/og.txt");
                buffer = Files.readAllBytes(path); 
 
                //creo el datagrama
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);
 
                //Envio la información
                System.out.println("Envio la informacion del cliente");
                socketUDP.send(respuesta);
                 
            }
 
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
 
    }
}
