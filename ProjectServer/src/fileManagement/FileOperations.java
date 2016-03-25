package fileManagement;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileOperations {
	
	public FileToTransfer readFile(String location){
		FileToTransfer f = null;
		try{
			File file = new File(location);
		    byte[] result = new byte[(int)file.length()];
		    
		    InputStream input = null;
		    int totalBytesRead = 0;
		    input = new BufferedInputStream(new FileInputStream(file));
		    
	        while(totalBytesRead < result.length){
	          int bytesRemaining = result.length - totalBytesRead;
	          int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
	          if (bytesRead > 0){
	            totalBytesRead = totalBytesRead + bytesRead;
	          }
	        }
	        
	        f = new FileToTransfer();
	        f.setContent(result);
	        f.setName(getFileNameByLocation(location));
	        
	        input.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Erro ao ler o arquivo");
		}
	        
		return f;
	}
	
	public void writeFile(FileToTransfer f, String location){
		try{
			OutputStream Output = new BufferedOutputStream(new FileOutputStream(location + "\\" + f.getName()));
			Output.write(f.getContent());
			Output.close();
		}catch(Exception e){
			System.out.println("Erro ao salvar o arquivo");
		}
	}
	
	public String getFileNameByLocation(String location){
		String aux = location.replace("\\", "");
		int i = location.length() - aux.length();
		return location.split("\\\\")[i];
	}
	
}
