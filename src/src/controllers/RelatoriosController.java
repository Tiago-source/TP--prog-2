package controllers;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.SkillsService;
import service.TalentoService;
import service.TalentoSkillsService;
import classes.Relatorios;
import classes.Talento;
import classes.TalentoSkills;

/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class RelatoriosController implements Initializable{

	  @FXML
	    private RadioButton btnPais;

	    @FXML
	    private RadioButton btnSkill;

	    @FXML
	    private TableView<Relatorios> tblRelatorios;

	    @FXML
	    private TableColumn<Relatorios, String> clParametro;

	    @FXML
	    private TableColumn<Relatorios, Integer> clMedia;


	    @FXML
	    private Button btnMenuFromRelatorios;
	    
	    SkillsService skillsService;
	    TalentoService talentoService;
	    TalentoSkillsService talentoSkillsService;

	    
	    
	    // Metodo para regressar ao menu principal
	    @FXML
	    void goToMenuFromRelatorios(ActionEvent event) throws IOException {
	    	
	    	FXMLLoader loader = new FXMLLoader();
    	    loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
    	    Parent home = loader.load();
    	    Scene homeScene = new Scene(home);
    	    Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	    homeStage.setScene(homeScene);
    	    homeStage.setMaximized(false);
    	    homeStage.show();

	    }
	    
	    
	    /*Metodo utilizado ao iniciar a aplicação onde configuramos a estrutura da tabela
		 * e criamos novas instancias do skillsService,talentoService e do talentoSkillsService
		 * o que nos permite utilizar os metodos criados nos mesmos. Configuramos tambem
		 * os bindings, e inicializamos os radio buttons.
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			skillsService = SkillsService.getNewInstance();
			talentoService = TalentoService.getNewInstance();
			talentoSkillsService = TalentoSkillsService.getNewInstance();
			
			configuraColunasTblRelatorios();
			radioButtons();
		
		}
		
		
		
		
		
		// Metodo para arredondar um float a duas casas decimais
		public static float round(float d, int decimalPlace) {
	         return BigDecimal.valueOf(d).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).floatValue();
	    }
		
		
		/* Metdodo que calcula a media do custo de mensal por pais
		 * neste metodo agrupamos os talentos por pais e calculamos a tarifa media desse pais
		 * que depois multiplicamos por 176h
		 */
		public void relatorioPais() {
			
			List<Talento> talentos = talentoService.fetchTalentos();
			
			Map<String,Float> r = new HashMap<String,Float>(); 

			for(int i=0; i < talentos.size();i++) {
				
				String pais = talentos.get(i).getTalentoPais();
				Float tarifa = Float.valueOf(talentos.get(i).getTalentoTarifa());
				
				
				if(r.containsKey(pais)) {
				//int count = talentoService.fetchTalentosPorNomePais(pais).size();
				
				
				r.put(pais, ((tarifa + r.get(pais))));
				}else {
					r.put(pais,tarifa);
				}
			}
			
			 @SuppressWarnings("rawtypes")
			Iterator it = r.entrySet().iterator();
			    while (it.hasNext()) {
			        @SuppressWarnings("rawtypes")
					Map.Entry pair = (Map.Entry)it.next();
			        int count = talentoService.fetchTalentosPorNomePais(pair.getKey().toString()).size();
			        float media1 = (Float.valueOf(pair.getValue().toString())/count)*176;
			        float media = round(media1,2);
			        r.put(pair.getKey().toString(), media);
			     
			        
			    }
			
			
		
			List<Relatorios> relatorios =
					
			r.entrySet()
			   .stream()
			   .map(e -> {
			       Relatorios rl = new Relatorios();
			       rl.setParametro(e.getKey());
			       rl.setMedia(e.getValue());
			       return rl;
			 }).collect(Collectors.toList());
		   
			tblRelatorios.getItems().setAll(relatorios);
		}

		
		
		
		
		
		
		
		/* Metdodo que calcula a media do custo de mensal por skill
		 * neste metodo agrupamos os talentos por skill e calculamos a tarifa media dessa skill
		 * que depois multiplicamos por 176h
		 */
		public void relatorioSkills() {
			
			List<TalentoSkills> talentosSkills = talentoSkillsService.fetchTalentoSkills();
	 
	
			Map<String,Float> r = new HashMap<String,Float>(); 

			for(int i=0; i < talentosSkills.size();i++) {
				
				String skill = talentosSkills.get(i).getTalentoSkillsNomeSkill();
				String nomeTalento = talentosSkills.get(i).getTalentoSkillsNome();
				Talento talento3 = talentoService.fetchTalentosPorNome(nomeTalento);
				Float tarifa = Float.valueOf(talento3.getTalentoTarifa());
			
				
				if(r.containsKey(skill)) {
				//int count = talentoSkillsService.fetchTalentoSkillsPorNomeDeSkill(skill).size();
				
	
				
				r.put(skill, ((tarifa + r.get(skill))));
				}else {
					r.put(skill,tarifa);
				}
			}
			
			 @SuppressWarnings("rawtypes")
			Iterator it = r.entrySet().iterator();
			    while (it.hasNext()) {
			        @SuppressWarnings("rawtypes")
					Map.Entry pair = (Map.Entry)it.next();
			        int count = talentoSkillsService.fetchTalentoSkillsPorNomeDeSkill(pair.getKey().toString()).size();
			        float media1 = (Float.valueOf(pair.getValue().toString())/count)*176;
			        float media = round(media1,2);
			        r.put(pair.getKey().toString(), media);
			     
			        
			    }
			
			List<Relatorios> relatorios =
					
			r.entrySet()
			   .stream()
			   .map(e -> {
			       Relatorios rl = new Relatorios();
			       rl.setParametro(e.getKey());
			       rl.setMedia(e.getValue());
			       return rl;
			 }).collect(Collectors.toList());
		   
			tblRelatorios.getItems().setAll(relatorios);
		}

			
			
		
		//Metodo para configurar os radio buttons
		public void radioButtons() {
			
			ToggleGroup radioGroup = new ToggleGroup();

	        btnPais.setToggleGroup(radioGroup);
	        btnSkill.setToggleGroup(radioGroup);
	        
	        
	        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
	            public void changed(ObservableValue<? extends Toggle> ov,
	                Toggle old_toggle, Toggle new_toggle) {
	                    if (radioGroup.getSelectedToggle() == btnPais) {
	                    	relatorioPais();
	                    }else if(radioGroup.getSelectedToggle() == btnSkill){
	                    	relatorioSkills();
	                    }          
	                }
	        });
		}
		


		// Metodo para configurar a estrutura da tabela
		private void configuraColunasTblRelatorios() {
			clParametro.setCellValueFactory(new PropertyValueFactory<>("parametro"));
			clMedia.setCellValueFactory(new PropertyValueFactory<>("media"));
	
		}
		
}
