package requisitionManagement;

import java.io.Serializable;

public class Requisition implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name_client;
	private String name_document;
	private String document_descriprtion;
	private Boolean wasAttended;
	
	public Requisition(){
		super();
		this.name_client = null;
		this.name_document = null;
		this.document_descriprtion = null;
		this.wasAttended = null;
	}
	
	public Requisition(String name_client, String name_document, String document_descriprtion,
			Boolean wasAttended) {
		super();
		this.name_client = name_client;
		this.name_document = name_document;
		this.document_descriprtion = document_descriprtion;
		this.wasAttended = wasAttended;
	}

	public void createRequisition(String req){
		String[] x = req.split(";");
		name_client = x[0];
		name_document = x[1];
		document_descriprtion = x[2];
		wasAttended = Boolean.getBoolean(x[3]);
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
	
}
