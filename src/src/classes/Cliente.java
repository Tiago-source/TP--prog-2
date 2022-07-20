package classes;


/**
 *
* @author Tiago Gomes 17501 & Vitor Rocha 17482
 */


public class Cliente {
	
		// Atributos da classe Cliente

	  	private int idCliente;
	    private String clienteNome;
	    private String clienteEmail;
	    
	 // Getters e setters dos atribuos da Classe Cliente
	    
		public int getIdCliente() {
			return idCliente;
		}
		
		public void setIdCliente(int idCliente) {
			this.idCliente = idCliente;
		}
		
		public String getClienteEmail() {
			return clienteEmail;
		}
		
		public void setClienteEmail(String clienteEmail) {
			this.clienteEmail = clienteEmail;
		}
		
		public String getClienteNome() {
			return clienteNome;
		}
		
		public void setClienteNome(String clienteNome) {
			this.clienteNome = clienteNome;
		}
		
	
}
