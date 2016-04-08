package fileManagement;

import java.io.Serializable;

public class FileToTransfer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private byte[] content;
	
	public int size(){
		return content.length;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
}
