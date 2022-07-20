package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.Skill;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.SkillsService;
import service.TalentoSkillsService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class SkillsController implements Initializable {
	
	  @FXML
	    private TableView<Skill> tblSkills;

	    @FXML
	    private TableColumn<Skill, Integer> clidSkill;

	    @FXML
	    private TableColumn<Skill, String> clNomeSkill;

	    @FXML
	    private TableColumn<Skill, String> clAreaSkill;

	    @FXML
	    private TextField txtIdSkill;

	    @FXML
	    private TextField txtNomeSkill;

	    @FXML
	    private TextField txtAreaSkill;

	    @FXML
	    private Button btnSaveSkill;

	    @FXML
	    private Button btnAtualizarSkill;

	    @FXML
	    private Button btnApagarSkill;

	    @FXML
	    private Button btnLimparSkill;

	    @FXML
	    private Button btnMenuFromSkills;
	    
	    
	    private SkillsService skillService;
	    private TalentoSkillsService talentoSkillsService;
	    
//		 
	    
	    
	    /*Metodo utilizado ao iniciar a aplicação onde configuramos a estrutura da tabela
		 * e criamos novas instancias do skillService e do talentoSkillsService
		 * o que nos permite utilizar os metodos criados nos mesmos. Configuramos tambem
		 * os bindings, e carregamos dados para a tabela skills.
		 */
			@Override
			public void initialize(URL location, ResourceBundle resources) {
				skillService = SkillsService.getNewInstance();
				talentoSkillsService = TalentoSkillsService.getNewInstance();
				configuraColunas();
				configuraBindings();
				atualizaDadosTabela();
			}
		
	   
			// Metodo para regressar ao menu principal
			@FXML
		    void goToMenuFromSkills(ActionEvent event) throws IOException {
				
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
	            Parent home = loader.load();
	            Scene homeScene = new Scene(home);
	            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	            homeStage.setScene(homeScene);
	            homeStage.setMaximized(false);
	            homeStage.show();

		    }
			
			// Metodo para guardar uma skill, executado quando o botao guardar é primido
			public void guardarSkill() {
				Skill c = new Skill();
				getValores(c);
				skillService.guardarSkill(c);
				atualizaDadosTabela();
			}
			
			
			// Metodo para atualizar uma skill, executado quando o botao atualizar é primido
			public void atualizarSkill() {
				Skill c = tblSkills.getSelectionModel().getSelectedItem();
				getValores(c);
				skillService.atualizarSkill(c);
				atualizaDadosTabela();
			}

			
			/* Metodo para apagar uma skill, executado quando o botao apagar é primido
			 * este metodo verifica se existe algum talento com a skill que queremos 
			 * apagar associada, caso exista não é possivel apagar a skill e um 
			 * alert é exposto no ecra do utiliador
			 */
			public void apagarSkill() {
				Skill c = tblSkills.getSelectionModel().getSelectedItem();
	
				
				if(talentoSkillsService.fetchTalentoSkillsPorNomeDeSkill(c.getSkillNome()).isEmpty() == true) {
				skillService.apagarSkill(c.getIdSkill());
				atualizaDadosTabela();
				Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Information Dialog");
	            alert.setHeaderText(null);
	            alert.setContentText("Skill eliminada");
	            alert.showAndWait();
	            
				} else if(talentoSkillsService.fetchTalentoSkillsPorNomeDeSkill(c.getSkillNome()).isEmpty() == false){
					Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setTitle("Information Dialog");
		            alert.setHeaderText(null);
		            alert.setContentText("Skill Associada a um professional");
		            alert.showAndWait();
				}
			}

			// Metodo que limpa os text fields e a seleção na tabela skills
			public void limpar() {
				tblSkills.getSelectionModel().select(null);
				txtIdSkill.setText("");
				txtNomeSkill.setText("");
				txtAreaSkill.setText("");
			
			}
			
		
			/* Metodo que vai buscar os valores inseridos nos text fields e os atribui 
			 * aos respetivos atributos de uma skill
			 */
			private void getValores(Skill c) {
				c.setSkillNome(txtNomeSkill.getText());
				c.setSkillArea(txtAreaSkill.getText());
			}


			/* Metodo executado quando queremos atualizar os dados da tabela 
			 * deste modo vemos sempre os dados atualizados
			 */
			private void atualizaDadosTabela() {
				tblSkills.getItems().setAll(skillService.fetchSkills());
				limpar();
			}

			//Metodo para configurar a estrutura da tabela
			private void configuraColunas() {
				clidSkill.setCellValueFactory(new PropertyValueFactory<>("idSkill"));
				clNomeSkill.setCellValueFactory(new PropertyValueFactory<>("skillNome"));
				clAreaSkill.setCellValueFactory(new PropertyValueFactory<>("skillArea"));
			}

			// Bindings
			private void configuraBindings() {
				
				//Boolean que verifica se os campos necessarios para uma skill estão preenchidos
				BooleanBinding camposPreenchidos = txtNomeSkill.textProperty().isEmpty()
						.or(txtAreaSkill.textProperty().isEmpty());
				
				//Boolean que indica se algo está selecionado na tabela
				BooleanBinding algoSelecionado = tblSkills.getSelectionModel().selectedItemProperty().isNull();
				
				//Metodos que ativam os botoes se algo estiver selecionado ou os campos preenchidos
				btnApagarSkill.disableProperty().bind(algoSelecionado);
				btnAtualizarSkill.disableProperty().bind(algoSelecionado);
				btnLimparSkill.disableProperty().bind(algoSelecionado.and(camposPreenchidos));
				
				//Ativa o botão save skill quando os campos estão preenchidos e não esta nada selecionada na tabela
				btnSaveSkill.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
				
				// Metodo que vai  buscar os dados da skill selecionada na tabela e os atribui aos respetivos text fields
				tblSkills.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
					if (n != null) {
						txtIdSkill.setText(String.valueOf(n.getIdSkill()));
						txtNomeSkill.setText(n.getSkillNome());
						txtAreaSkill.setText(n.getSkillArea());
					}
				});
			}
			
	    

}
