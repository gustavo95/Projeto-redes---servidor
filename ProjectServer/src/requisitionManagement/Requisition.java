package requisitionManagement;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class Requisition  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name_client;
	private String name_document;
	private String document_descriprtion;
	private Boolean wasAttended;
	private Socket socket;
	private ArrayList<Requisition> l;
	
	
	public Requisition(String name_client, String name_document, String document_descriprtion,
			Boolean wasAttended, Socket socket) {
		super();
		this.name_client = name_client;
		this.name_document = name_document;
		this.document_descriprtion = document_descriprtion;
		this.wasAttended = wasAttended;
		this.socket = socket;
	}


	public Requisition(ArrayList<Requisition> l) {
		this.l = l;
	}


	public void createRequisition(String req, Socket s){
		String[] x = req.split(";");
		name_client = x[0];
		name_document = x[1];
		document_descriprtion = x[2];
		wasAttended = Boolean.getBoolean(x[3]);
		addRequisition(this);
	}
	
	public void addRequisition(Requisition reqServer){
		l.add(reqServer);	
	}


	public String getName_client() {
		return name_client;
	}


	public String getName_document() {
		return name_document;
	}


	public String getDocument_descriprtion() {
		return document_descriprtion;
	}


	public Boolean getWasAttended() {
		return wasAttended;
	}


	public Socket getSocket() {
		return socket;
	}


	public ArrayList<Requisition> getL() {
		return l;
	}
	
}
