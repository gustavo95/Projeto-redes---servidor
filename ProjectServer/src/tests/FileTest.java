package tests;

import fileManagement.FileOperations;
import fileManagement.FileToTransfer;

public class FileTest {
	
	public static void main(String argv[]) {
		FileOperations fo = new FileOperations();
		
		String location = "C:\\Users\\guga\\Documents\\5\\Grafos\\Grafos - Introdução e Prática.pdf";
		FileToTransfer f = fo.readFile(location);
		
		if(f != null){
			System.out.println("Nome: " + f.getName());
			System.out.println("Tamanho: " + f.getContent().length);
			
			location = "C:\\Users\\guga\\Documents";
			fo.writeFile(f, location);
			System.out.println("Salvo!");
		}
	}

}
