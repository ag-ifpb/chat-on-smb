package br.edu.ifpb.chatgroup.model;



import java.time.LocalDateTime;


public class Message {
	private String id;
    private String mensagem;
    private LocalDateTime timestamp;

    public Message(String id, String mensagem, LocalDateTime timestamp) {
		this.id = id;
		this.mensagem = mensagem;
		this.timestamp = timestamp;
	}
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
