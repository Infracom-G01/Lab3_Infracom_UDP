import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//import java.net.ServerSocket;
import java.util.ArrayList;

public class Servidor {

    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private int PUERTO = 5000; 
    //private ServerSocket serverSocket; 
    private boolean serverRunning = true;
    private Delegado delegado = new Delegado();
    

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Servidor() throws Exception {

      //Creacion del socket del servidor UDP
      DatagramSocket socketUDP = new DatagramSocket(PUERTO);
  
      System.out.println("UDP Server started listening on port " + PUERTO);

      //Preparo la respuesta
      byte[] buffer = new byte[100];
      DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                 
      socketUDP.receive(peticion);
      //Convierto lo recibido y mostrar el mensaje
      String mensajeInicial = new String(peticion.getData());
      System.out.println(mensajeInicial);

      String[] aux = mensajeInicial.split("-");

      int numClientes = Integer.parseInt(aux[0]);
      int tamArchivo = Integer.parseInt(aux[1]);
      String nomArchivo = aux[2];

      ArrayList<DatagramPacket> listaClientes = new ArrayList<DatagramPacket>(); 
      int noOfThreads = 0;

      //Conexiones restantes
      while (serverRunning) {
  
        if (noOfThreads==numClientes)
        {

          for (int i = 0; i<listaClientes.size(); i++)
          {
            delegado.envioDeArchivo(socketUDP, listaClientes.get(i), tamArchivo, nomArchivo);
          }
          
          noOfThreads=0;
          serverRunning=false;
        }

        else
        {
          try {
            
            buffer = new byte[tamArchivo];
            peticion = new DatagramPacket(buffer, buffer.length);

            socketUDP.receive(peticion);    
            
            listaClientes.add(peticion);
            noOfThreads++;    
            
          } catch (IOException e) {
            System.out.println("Exception encountered on accept");
            e.printStackTrace();
          }
        }

      }
  
      // try {
      //   serverSocket.close();
      //   System.out.println("Server was stopped");
  
      // } catch (IOException e) {
      //   System.out.println("Problem stopping server");
      //   System.exit(-1);
      // }
    
    }

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************
    
    public static void main(String[] args) throws Exception {
        new Servidor();
      }
}
