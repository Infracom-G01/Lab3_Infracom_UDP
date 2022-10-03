import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {

    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private int Port = 5000; //Puerto FTP debe ser 21 o 22
    private ServerSocket serverSocket; 
    private boolean serverRunning = true;
    private static Scanner input = new Scanner(System.in);
    private Delegado delegado = new Delegado();

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Servidor() throws Exception {

      System.out.println("¿Que archivo desea enviar a los clientes que se conecten? \n1. Archivo de 100 MB \n2. Achivo 250 MB");
			String Num = input.nextLine();

      int tamArchivo;
      String nomArchivo;
      if (Num.equals("1"))
      {
        tamArchivo = 104857600;
        nomArchivo= "Archivo100.txt";
      }
      else
      {
        tamArchivo = 262144000;
        nomArchivo= "Archivo250.txt";
      }
        

      try {
        serverSocket = new ServerSocket(Port);
      } catch (IOException e) {
        System.out.println("Could not create server socket");
        System.exit(-1);
      }
  
      System.out.println("TCP Server started listening on port " + Port);

      ArrayList<Socket> listaClientes = new ArrayList<Socket>(); 
      int noOfThreads = 0;
  
      while (serverRunning) {
  
        if (noOfThreads==2)
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
