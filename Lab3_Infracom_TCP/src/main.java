import java.util.ArrayList;

public class main {

    private static Log logPrueba = new Log();
    private static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();

    public static void main (String args[]) throws Throwable{
		
        System.out.println("Esto es una prueba de log");

        logPrueba.GenerarLog("Jessica202013355", "Jeje\nSi sirve brrrrr");

        for(int i=0; i<25; i++)
        {
            Cliente C=new Cliente(i);
            C.start();
            listaClientes.add(C);
        }
	}
}
