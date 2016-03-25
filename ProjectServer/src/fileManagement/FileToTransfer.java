package fileManagement;

public class FileToTransfer {
	
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
