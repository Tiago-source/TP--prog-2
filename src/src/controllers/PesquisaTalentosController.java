package controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


import classes.TalentoSkills;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.TalentoSkillsService;


/**
 *
* @author Tiago Gomes 17501 & Vitor Rocha 17482
 */

public class PesquisaTalentosController implements Initializable{
	
	
	  @FXML
	    private TextField txtPesquisa;

	    @FXML
	    private Button btnPesquisar;

	    @FXML
	    private TableView<TalentoSkills> tblPesquisaTalentos;

	    @FXML
	    private TableColumn<TalentoSkills, Integer> clidPesquisa;

	    @FXML
	    private TableColumn<TalentoSkills, String> clNomeTalento;

	    @FXML
	    private TableColumn<TalentoSkills, String> clEmailTalento;

	    @FXML
	    private TableColumn<TalentoSkills, String> clNomeSkill;

	    @FXML
	    private TableColumn<TalentoSkills, String> clAnosSkill;

	    @FXML
	    private Button btnMenuFromPesquisa;
	    
	    private TalentoSkillsService talentoSkillsService;
	    
	    
		    
		    /*Metodo utilizado ao iniciar a aplicação onde configuramos a estrutura da tabela
	    	 * e criamos uma nova instancia do talentoSkillsService o que nos permite utilizar os metodos criados no mesmo
	    	 */
			@Override
			public void initialize(URL location, ResourceBundle resources) {
				talentoSkillsService = TalentoSkillsService.getNewInstance();
				configuraColunas();
				
			}

		// Metodo para regressar ao menu principal	
	    @FXML
	    void goToMenuFromPesquisa(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader();
    	    loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
    	    Parent home = loader.load();
    	    Scene homeScene = new Scene(home);
    	    Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	    homeStage.setScene(homeScene);
    	    homeStage.setMaximized(false);
    	    homeStage.show();
	    }


	    
	    /* Metedo para pesquisar talentos por skills , neste metodo separamos as skills inseridas no text field numa unica string
	     * separadas por virgulas e pesquisamos para cada skill os talentos que a possuem e serão dispostos na tabela
	     * os talentos que possuem as skills inseridas
	     */
	    @FXML
	    void pesquisarTalentos(ActionEvent event) {
	    
	      	String pesquisa = txtPesquisa.getText();
	    	String[] words = pesquisa.split(",");
	    	List<TalentoSkills> talentos3 ;
	    	List<TalentoSkills> talentos4 = new ArrayList<TalentoSkills>();
	    
	    	
	    	for(int i = 0; i < words.length; i++){
	    		
	    		talentos3 = talentoSkillsService.fetchTalentoSkillsPorNomeDeSkill(words[i]);
	    		
	    		for(int j = 0; j< words.length; j++) {
	    			  
	    			
	    		talentos4.addAll(talentos3);
	    			
	    		}
	    		
	    	}
	    	
	    	List<TalentoSkills> Distinct = talentos4.stream().distinct().collect(Collectors.toList());
	    	tblPesquisaTalentos.getItems().setAll(Distinct);
	    }
	    
	    
		// Metodo que configura as colunas da tabela
		private void configuraColunas() {
			clidPesquisa.setCellValueFactory(new PropertyValueFactory<>("idTalentoSkills"));
			clNomeTalento.setCellValueFactory(new PropertyValueFactory<>("talentoSkillsNome"));
			clEmailTalento.setCellValueFactory(new PropertyValueFactory<>("talentoSkillsEmail"));
			clNomeSkill.setCellValueFactory(new PropertyValueFactory<>("talentoSkillsNomeSkill"));
			clAnosSkill.setCellValueFactory(new PropertyValueFactory<>("talentoSkillAnosExperiencia"));

		}

}
