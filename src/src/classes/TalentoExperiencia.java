package classes;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class TalentoExperiencia {
	
		/* Atributos da classe TalentosExperiencia que representam uma experiencia de trabalho
		 * de um talento e cada experiencia está associada ao seu talneto pelo id do mesmo
		 * mas tambem pelo nome e email.
		 * Assim temos todas as experiencias associadas ao talento e onde não é possivel associar
		 * experiencias com datas sobrepostas
		 */
	
	  	private int idTalentoExperiencia;
	  	private int talentoId;
	  	private String talentoNome;
	  	private String talentoEmail;
	    private String talentoExperienciaTitulo;
	    private String talentoExperienciaEmpresa;
	    private String talentoExperienciaInicio;
	    private String talentoExperienciaFim;
	    
	    
	    // Metodosd getters e setters da classe
	    
		public int getIdTalentoExperiencia() {
			return idTalentoExperiencia;
		}
		
		public void setIdTalentoExperiencia(int idTalentoExperiencia) {
			this.idTalentoExperiencia = idTalentoExperiencia;
		}
		
		public String getTalentoExperienciaTitulo() {
			return talentoExperienciaTitulo;
		}
		
		public void setTalentoExperienciaTitulo(String talentoExperienciaTitulo) {
			this.talentoExperienciaTitulo = talentoExperienciaTitulo;
		}
		
		public String getTalentoExperienciaEmpresa() {
			return talentoExperienciaEmpresa;
		}

		public void setTalentoExperienciaEmpresa(String talentoExperienciaEmpresa) {
			this.talentoExperienciaEmpresa = talentoExperienciaEmpresa;
		}

		public String getTalentoExperienciaInicio() {
			return talentoExperienciaInicio;
		}
		
		public void setTalentoExperienciaInicio(String talentoExperienciaInicio) {
			this.talentoExperienciaInicio = talentoExperienciaInicio;
		}
		
		public String getTalentoExperienciaFim() {
			return talentoExperienciaFim;
		}
		
		public void setTalentoExperienciaFim(String talentoExperienciaFim) {
			this.talentoExperienciaFim = talentoExperienciaFim;
		}

		public String getTalentoNome() {
			return talentoNome;
		}

		public void setTalentoNome(String talentoNome) {
			this.talentoNome = talentoNome;
		}

		public String getTalentoEmail() {
			return talentoEmail;
		}

		public void setTalentoEmail(String talentoEmail) {
			this.talentoEmail = talentoEmail;
		}

		public int getTalentoId() {
			return talentoId;
		}

		public void setTalentoId(int talentoId) {
			this.talentoId = talentoId;
		}

	    
}
