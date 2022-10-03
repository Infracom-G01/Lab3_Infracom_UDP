import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Log {
	
	public void GenerarLog(String nomLog, String contenido) {	


		File archivoCodigo;
		FileWriter escribir;
		PrintWriter linea;
		archivoCodigo = new File("Lab3_Infracom_TCP/src/Logs/"+nomLog +".txt");
		
		if(!archivoCodigo.exists()) {
			try {
				archivoCodigo.createNewFile(); 
				escribir= new FileWriter(archivoCodigo, true);
				linea = new PrintWriter(escribir);
				linea.println(contenido);//ACA A DENTRO VA LA INFO EN STRING PARA EL ARCHIVO
				linea.close();
				escribir.close();
			} catch (Exception e) {}		
		
		} 
		
		else {
			try {
				escribir= new FileWriter(archivoCodigo, true);
				linea = new PrintWriter(escribir);
				linea.println(contenido);//ACA A DENTRO VA LA INFO EN STRING PARA EL ARCHIVO
				linea.close();
				escribir.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				}
			}
		}
		
		public void removeLog(String nomLog) throws Throwable 	
		{ 	
			File archivoCodigo = new File(nomLog +".txt");
			archivoCodigo.delete();
	}
	
	public List<String> LeerLog(String nombreArchivo) throws Throwable
	{
	
		File archivo;
		FileReader leer;
		
		List<String> informacion = new ArrayList<>();
				
		archivo = new File(nombreArchivo+".txt");
		
			leer = new FileReader(archivo);
			BufferedReader almacenamiento = new BufferedReader(leer);
			
			String cadena=almacenamiento.readLine();

			while (cadena!=null)
			{
					if (cadena!=null && !cadena.equals(" ")) 
					{
						informacion.add(cadena);
					}
					cadena=almacenamiento.readLine();
			}	
		almacenamiento.close();
		leer.close();
		return informacion;
	}
    
}
