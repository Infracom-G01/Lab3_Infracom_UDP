import java.util.Scanner;

public class main {

    private static Log logPrueba = new Log();
    private static Scanner input = new Scanner(System.in);
    //private static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();

    public static void main (String args[]) throws Throwable{
        
        System.out.println("¿Cuantas conexiones desea que se generen? (Minimmo 25)");
        int NumClientes = Integer.parseInt(input.nextLine());

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

        Cliente Cinicial = new Cliente(0);
        Cinicial.conexionInicial(NumClientes, tamArchivo, nomArchivo);

        Cliente.establecerTotalClientes(NumClientes);

        for(int i=1; i<=NumClientes; i++)
        {
            Cliente C=new Cliente(i);
            C.start();
        }
               
	}
}
