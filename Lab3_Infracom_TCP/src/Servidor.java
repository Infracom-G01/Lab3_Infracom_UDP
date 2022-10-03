import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Servidor {

    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    private int Port = 5000; //Puerto FTP debe ser 21 o 22
    private ServerSocket serverSocket; 
    boolean serverRunning = true;
    FileInputStream fr;
    OutputStream os;
    DataInputStream intxt;
    DataOutputStream outtxt;
    Hash hash = new Hash();

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Servidor() throws Exception {

        try {
          serverSocket = new ServerSocket(Port);
        } catch (IOException e) {
          System.out.println("Could not create server socket");
          System.exit(-1);
        }
    
        System.out.println("TCP Server started listening on port " + Port);

        ArrayList<Delegado> listaDelegados = new ArrayList<Delegado>(); 
        int noOfThreads = 0;
    
        while (serverRunning) {
    
          if (noOfThreads==654654545)
          {
            for (int i = 0; i<listaDelegados.size(); i++)
              {listaDelegados.get(i).start();}
            noOfThreads = 0;
          }
          else
          {
            try {

              Socket cliente = serverSocket.accept();
              System.out.println("New connection received" );
              
              os = cliente.getOutputStream();
              intxt = new DataInputStream(cliente.getInputStream());
              outtxt = new DataOutputStream(os);

              System.out.println("Cliente conectado");

              // Leo el mensaje que me envia
              String mensaje = intxt.readUTF();
              System.out.println("Se recibio mensaje " + mensaje);
              
              // Envio mensaje de confirmacion
              outtxt.writeUTF("ACK desde el servidor");



              File file = new File("Lab3_Infracom_TCP/src/ArchivosEnviados/Archivo250.txt");
              MessageDigest mdigest = MessageDigest.getInstance("MD5");
       
              String hashArchivo = hash.checksum(mdigest, file);
              System.out.println("El MD5 checksum de " + "Lab3_Infracom_TCP/src/ArchivosEnviados/Archivo250.txt" + " es " + hashArchivo);

              fr = new FileInputStream("Lab3_Infracom_TCP/src/ArchivosEnviados/Archivo250.txt");
              
              byte [] b = new byte[262144000];
              fr.read(b,0,b.length);
              outtxt.write(b,0,b.length);
  
              noOfThreads++;

              System.out.println(noOfThreads);
     
              // cliente.close();

              // System.out.println(noOfThreads );
              // Delegado D = new Delegado(noOfThreads, in, out, cliente);
              // listaDelegados.add(D);

              
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
