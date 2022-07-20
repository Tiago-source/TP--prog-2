package classes;

/**
 *
* @author Tiago Gomes 17501 & Vitor Rocha 17482
 */


public class PropostaTrabalho {
	
	    /* Atributos da classe PropostaTrabalho que representam os dados de uma proposta
	     * de trabalho de um cliente e que está associada ao mesmo pelo id do cliente(idEmpresa)
	     * já registado no sistema
	     */
	
	   	private int idProposta;
	   	private int idEmpresa;
	   	private String PropostaEmpresa;
	    private String PropostaNome;
	    private String PropostaCategoria;
	    private int PropostaHoras;
	    private String PropostaDescricao;
	    
	    // Metodos getters e setters da classe
	    
		public int getIdProposta() {
			return idProposta;
		}
		
		public void setIdProposta(int idProposta) {
			this.idProposta = idProposta;
		}
		
		public String getPropostaNome() {
			return PropostaNome;
		}
		
		public void setPropostaNome(String propostaNome) {
			PropostaNome = propostaNome;
		}
		
		public String getPropostaCategoria() {
			return PropostaCategoria;
		}
		
		public void setPropostaCategoria(String propostaCategoria) {
			PropostaCategoria = propostaCategoria;
		}
		
		public int getPropostaHoras() {
			return PropostaHoras;
		}
		
		public void setPropostaHoras(int propostaHoras) {
			PropostaHoras = propostaHoras;
		}
		
		public String getPropostaDescricao() {
			return PropostaDescricao;
		}
		
		public void setPropostaDescricao(String propostaDescricao) {
			PropostaDescricao = propostaDescricao;
		}
		
		public String getPropostaEmpresa() {
			return PropostaEmpresa;
		}
		
		public void setPropostaEmpresa(String propostaEmpresa) {
			PropostaEmpresa = propostaEmpresa;
		}
		
		public int getIdEmpresa() {
			return idEmpresa;
		}
		
		public void setIdEmpresa(int idEmpresa) {
			this.idEmpresa = idEmpresa;
		}
	
	    
}
