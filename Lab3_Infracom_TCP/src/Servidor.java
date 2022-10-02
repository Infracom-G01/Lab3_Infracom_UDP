import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private int Port = 5000; //Puerto FTP debe ser 21 o 22
    private ServerSocket serverSocket; 
    boolean serverRunning = true;
    DataInputStream in;
    DataOutputStream out;

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Servidor() {

        try {
          serverSocket = new ServerSocket(Port);
        } catch (IOException e) {
          System.out.println("Could not create server socket");
          System.exit(-1);
        }
    
        System.out.println("FTP Server started listening on port " + Port);
    
        int noOfThreads = 0;
    
        while (serverRunning) {
    
          try {
    
            Socket Cliente = serverSocket.accept();
    
            // Crear un nuevo thread para atender a todos los clientes
            //System.out.println("New connection received");
            noOfThreads++;


            //PRUEBAAAAAAAAAAA
            System.out.println("Cliente conectado");
            in = new DataInputStream(Cliente.getInputStream());
            out = new DataOutputStream(Cliente.getOutputStream());

            //Leo el mensaje que me envia
            String mensaje = in.readUTF();

            System.out.println(mensaje);

            //Le envio un mensaje
            out.writeUTF("¡Hola mundo desde el servidor!");

            //Cierro el socket
            Cliente.close();
            System.out.println("Cliente desconectado");
            //FIN PRUEBAAAAAAAAAAA


          } catch (IOException e) {
            System.out.println("Exception encountered on accept");
            e.printStackTrace();
          }
        }
    
        try {
          serverSocket.close();
          System.out.println("Server was stopped");
    
        } catch (IOException e) {
          System.out.println("Problem stopping server");
          System.exit(-1);
        }
    
      }

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************
    
    public static void main(String[] args) {
        new Servidor();
      }
}