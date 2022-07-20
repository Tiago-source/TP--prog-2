package classes;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class TalentoSkills {
	
	
	/* Atributos da classe TalentoSkills que representam a skill de um determinado Talento
	 * Um talento pode posssuir varias skills e deste modo associamos as skills de um talento 
	 * atraves do id do talento e tambem pelo nome e email. As skills tem que se encontrar previamente
	 * registadas no sistema
	 */
    
    private int idTalentoSkills;
    private int talentoId;
    private String talentoSkillsNome;
    private String talentoSkillsEmail;
    private String talentoSkillsNomeSkill;
    private int talentoSkillAnosExperiencia;
    
    // Metodos getters e setters da classe
    
	public int getIdTalentoSkills() {
		return idTalentoSkills;
	}
	
	public void setIdTalentoSkills(int idTalentoSkills) {
		this.idTalentoSkills = idTalentoSkills;
	}
	
	public String getTalentoSkillsNomeSkill() {
		return talentoSkillsNomeSkill;
	}
	
	public void setTalentoSkillsNomeSkill(String talentoSkillsNomeSkill) {
		this.talentoSkillsNomeSkill = talentoSkillsNomeSkill;
	}
	
	public String getTalentoSkillsEmail() {
		return talentoSkillsEmail;
	}
	
	public void setTalentoSkillsEmail(String talentoSkillsEmail) {
		this.talentoSkillsEmail = talentoSkillsEmail;
	}
	
	public String getTalentoSkillsNome() {
		return talentoSkillsNome;
	}
	
	public void setTalentoSkillsNome(String talentoSkillsNome) {
		this.talentoSkillsNome = talentoSkillsNome;
	}
	
	public int getTalentoSkillAnosExperiencia() {
		return talentoSkillAnosExperiencia;
	}
	
	public void setTalentoSkillAnosExperiencia(int talentoSkillAnosExperiencia) {
		this.talentoSkillAnosExperiencia = talentoSkillAnosExperiencia;
	}
	
	public int getTalentoId() {
		return talentoId;
	}
	
	public void setTalentoId(int talentoId) {
		this.talentoId = talentoId;
	}



}
