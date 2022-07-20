package classes;

/**
 *
* @author Tiago Gomes 17501 & Vitor Rocha 17482
 */


public class PropostaSkills {
	 
	/* Atributos da Classe PropostaSkills, esta classe representa as skills associadas a uma 
	 * proposta de trabalho pelo id da mesma 
	 */
	
    private int idSkillProposta;
    private String skillNome;
    private String skillAnos;
    private int idProposta;
    
    // Metodos getters e setters da classe
    
	public int getIdSkillProposta() {
		return idSkillProposta;
	}
	
	public void setIdSkillProposta(int idSkillProposta) {
		this.idSkillProposta = idSkillProposta;
	}
	
	public String getSkillNome() {
		return skillNome;
	}
	
	public void setSkillNome(String skillNome) {
		this.skillNome = skillNome;
	}
	
	public String getSkillAnos() {
		return skillAnos;
	}
	
	public void setSkillAnos(String skillAnos) {
		this.skillAnos = skillAnos;
	}
	
	public int getIdProposta() {
		return idProposta;
	}
	
	public void setIdProposta(int idProposta) {
		this.idProposta = idProposta;
	}

}
