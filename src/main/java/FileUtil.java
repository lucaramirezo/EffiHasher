import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Luca Ramirez
 * Class to save and read text and binary files
 */
public class FileUtil {
	/**
	 * path to the file
	 */
	private String filePath;
	
	/**
	 * Constructor (filePath is mandatory)
	 * @param filePath
	 */
	public FileUtil(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * It writes text to a file
	 * @param text text to be written
	 * @return false if something goes wrong
	 */
	public boolean writeToFile(String text){
		boolean bRet=true;
		if(!filePath.equals("")) {
			Path file=Paths.get(filePath);
			Charset charset = Charset.forName("UTF-8");
			//Default option use StandardOpenOption.CREATE (create a new one if doesn't exists) and StandardOpenOption.TRUNCATE_EXISTING
			//We can use also StandardOpenOption.APPEND (more efficient), but, what happens when the user delete? 
			try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
				writer.write(text, 0, text.length());
				writer.close();
			} catch (IOException x) {
				bRet=false;
				System.err.format("IOException: %s%n", x);
			}
		}
		return bRet;
	}
	/**
	 * Reads the content of a file
	 * @return String content of the file
	 */
	public String readFromFile(){
		String sRet="";
		Path file=Paths.get(filePath);
		Charset charset = Charset.forName("UTF-8");
		String sTemp="";
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			while ((sTemp=reader.readLine())!= null) {
				sRet+=sTemp;
			}
			reader.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		return sRet;
	}
	
	/**
	 * Serialize an Serilizable object to a file
	 * @param obj Serializable object to be saved
	 * @return false if something goes wrong
	 */
	public boolean serializeToFile(Serializable obj){
		boolean bRet=true;
        try(FileOutputStream fos=new FileOutputStream(filePath);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(obj);
        }catch(IOException e){
        	bRet=false;
            System.out.println("Problema de IO al serializar");
            System.out.println(e);
        }
        return bRet;
    }
    
	/**
	 * Deserialize an object from a file
	 * @return Serializable object or null
	 */
    public Serializable deserializeFromFile(){
        try(FileInputStream fis=new FileInputStream(filePath);
            ObjectInputStream oos=new ObjectInputStream(fis)){
            return (Serializable) oos.readObject();
        }catch(ClassNotFoundException e){
            System.out.println("No se ha encontrado la clase al deserializar");
            System.out.println(e.toString());
            return null;
        } catch (IOException e) {
            System.out.println("Problema de IO al deserializar");
            System.out.println(e.toString());
            return null;
        }
    }
}
