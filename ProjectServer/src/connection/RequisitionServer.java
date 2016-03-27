package connection;

import java.net.Socket;
import java.util.ArrayList;

public class RequisitionServer {

	
	String name_client;
	String name_document;
	String document_descriprtion;
	Boolean wasAttended;
	Socket socket;
	ArrayList<RequisitionServer> l;
	
	
	public RequisitionServer(String name_client, String name_document, String document_descriprtion,
			Boolean wasAttended, Socket socket) {
		super();
		this.name_client = name_client;
		this.name_document = name_document;
		this.document_descriprtion = document_descriprtion;
		this.wasAttended = wasAttended;
		this.socket = socket;
	}


	public RequisitionServer(ArrayList l) {
		this.l = l;
		
		
	}


	void createRequisition(String req, Socket s){
		String[] x = req.split(";");
		String name = x[0];
		String document = x[1];
		String description = x[2];
		boolean wasAttended = Boolean.getBoolean(x[3]);
		RequisitionServer reqServer = new RequisitionServer(name, document, document, wasAttended, s);
		addRequisition(reqServer);
		
	}
	void addRequisition(RequisitionServer reqServer){
		l.add(reqServer);
		
		
	}
}
