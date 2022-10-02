import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Delegado extends Thread{
    
    //***********************************************************
    //************************Atributos**************************
    //*********************************************************** 

    int Id;
    DataInputStream in;
    DataOutputStream out;
    Socket cliente;

    //***********************************************************
    //**********************Constructor**************************
    //*********************************************************** 

    public Delegado(int id, DataInputStream in, DataOutputStream out, Socket cliente) {
        Id = id;
        this.in = in;
        this.out = out;
        this.cliente=cliente;
    }

    //***********************************************************
    //***********************Funciones***************************
    //***********************************************************

    @Override
    public void run() 
    {
        try {

            System.out.println("Cliente conectado");

            //Leo el mensaje que me envia
            String mensaje;
            mensaje = in.readUTF();
            System.out.println(mensaje);

            //Le envio un mensaje
            out.writeUTF("¡Hola mundo desde el servidor!");
   
            cliente.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Algo pasó y se murió");
        }

    }
}
