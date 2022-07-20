package classes;


/**
 *
* @author Tiago Gomes 17501 & Vitor Rocha 17482
 */


public class Relatorios {
	
	//Atributos da classe Realatorios que representam os relatorios de preço medio mensal por pais e por skill

    private String parametro;
    private float  media;
    
    //Metodos getters e setters da classe
    
	public String getParametro() {
		return parametro;
	}
	
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	public float getMedia() {
		return media;
	}
	
	public void setMedia(float media) {
		this.media = media;
	}

}
