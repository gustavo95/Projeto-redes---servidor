package connection;

import java.io.Serializable;

public class Requisition implements Serializable{

	private static final long serialVersionUID = 1L;
	private User user;
	private String name_document;
	private String document_descriprtion;
	private Boolean wasAttended;
	
	public Requisition(){
		super();
		this.name_document = null;
		this.document_descriprtion = null;
		this.wasAttended = null;
		this.user = null;
	}
	
	public Requisition(User user, String name_document, String document_descriprtion,
			Boolean wasAttended) {
		super();
		this.user = user;
		this.name_document = name_document;
		this.document_descriprtion = document_descriprtion;
		this.wasAttended = wasAttended;
	}
	
	public User getUser() {
		return user;
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
