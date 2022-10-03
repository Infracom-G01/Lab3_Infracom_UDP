import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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

              fr = new FileInputStream("Lab3_Infracom_TCP/src/ArchivosEnviados/og.txt");
              byte [] b = new byte[20002];
              fr.read(b,0,b.length);

              os.write(b,0,b.length);
  
              noOfThreads++;
     
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
    
    public static void main(String[] args) {
        new Servidor();
      }
}
