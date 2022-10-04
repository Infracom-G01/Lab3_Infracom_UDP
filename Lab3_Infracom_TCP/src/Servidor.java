import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private int Port = 5000; 
    private ServerSocket serverSocket; 
    private boolean serverRunning = true;
    private Delegado delegado = new Delegado();

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Servidor() throws Exception {

      //Creacion del socket del servidor
      serverSocket = new ServerSocket(Port);
  
      System.out.println("TCP Server started listening on port " + Port);

      ArrayList<Socket> listaClientes = new ArrayList<Socket>(); 
      int noOfThreads = 0;

      // Cliente inicial
      Socket clienteInical = serverSocket.accept();
      DataInputStream intxtInicial = new DataInputStream(clienteInical.getInputStream());

      String mensajeInicial = intxtInicial.readUTF();
      String[] aux = mensajeInicial.split("-");

      int numClientes = Integer.parseInt(aux[0]);
      int tamArchivo = Integer.parseInt(aux[1]);
      String nomArchivo = aux[2];

      clienteInical.close();

      //Conexiones restantes
      while (serverRunning) {
  
        if (noOfThreads==numClientes)
        {

          for (int i = 0; i<listaClientes.size(); i++)
          {
            delegado.envioDeArchivoYhash(listaClientes.get(i), tamArchivo, nomArchivo);;
          }
          
          noOfThreads=0;
        }

        else
        {
          try {
            Socket cliente = serverSocket.accept();
            System.out.println("New connection received" );
            
            listaClientes.add(cliente);
            noOfThreads++;    
            
          } catch (IOException e) {
            System.out.println("Exception encountered on accept");
            e.printStackTrace();
          }
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
    
    public static void main(String[] args) throws Exception {
        new Servidor();
      }
}
