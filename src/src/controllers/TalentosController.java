package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.Talento;
import javafx.beans.binding.BooleanBinding;
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
import service.TalentoService;
import service.TalentoSkillsService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class TalentosController implements Initializable {
	
    @FXML
    private TableView<Talento> tblTalentos;

    @FXML
    private TableColumn<Talento, Integer> clidTalento;

    @FXML
    private TableColumn<Talento, String> clNomeTalento;

    @FXML
    private TableColumn<Talento, String> clPaisTalento;

    @FXML
    private TableColumn<Talento, String> clEmailTalento;

    @FXML
    private TableColumn<Talento, Integer> clTarifaTalento;

    @FXML
    private TextField txtIdTalento;

    @FXML
    private TextField txtNomeTalento;

    @FXML
    private TextField txtPaisTalento;

    @FXML
    private TextField txtEmailTalento;

    @FXML
    private TextField txtTarifaTalento;

    @FXML
    private Button btnSaveTalento;

    @FXML
    private Button btnAtualizarTalento;

    @FXML
    private Button btnApagarTalento;

    @FXML
    private Button btnLimparTalento;

    @FXML
    private Button btnMenuFromTalentos;
    
    private TalentoService talentoService;
    private TalentoSkillsService talentoSkillsService;
    
    
    
	    /*Metodo utilizado ao iniciar a aplicação onde configuramos a estrutura da tabela
		 * e criamos novas instancias do talentoService e do talentoSkillsService
		 * o que nos permite utilizar os metodos criados nos mesmos. Configuramos tambem
		 * os bindings e carregamos os dados para a tabela.
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			talentoService = TalentoService.getNewInstance();
			talentoSkillsService = TalentoSkillsService.getNewInstance();
			configuraColunas();
			configuraBindings();
			atualizaDadosTabela();
		}
	
		
		
  
		  // Metodo para regressar ao menu principal
		  @FXML
		    void goToMenuFromTalentos(ActionEvent event) throws IOException {	
			
			FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
           Parent home = loader.load();
           Scene homeScene = new Scene(home);
           Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
           homeStage.setScene(homeScene);
           homeStage.setMaximized(false);
           homeStage.show();

	    }
		
		  
		// Metodo para guardar um novo talento
		public void guardarTalento() {
			Talento c = new Talento();
			getValores(c);
			talentoService.guardarTalento(c);
			atualizaDadosTabela();
		}
		
		// Metodo para atualizar um talento
		public void atualizarTalento() {
			Talento c = tblTalentos.getSelectionModel().getSelectedItem();
			getValores(c);
			talentoService.atualizarTalento(c);
			atualizaDadosTabela();
		}

		/* Metodos para apagar um talento e que vai ao ficheiro onde estão guardadas
		 * as skills associadas aos talentos e apaga todos os registos deste talento
		 */
		public void apagarTalento() {
			Talento c = tblTalentos.getSelectionModel().getSelectedItem();
			int id = c.getIdTalento();
					apagarSkillsTalento(id);
			
			
			for(int i = 0; i <= talentoSkillsService.fetchTalentoSkillsPorIdDoTalento(id).size();i++) {	
			
				apagarSkillsTalento(id);
			
			}
			
			if(talentoSkillsService.fetchTalentoSkillsPorIdDoTalento(id).size() == 0) {
				talentoService.apagarTalento(c.getIdTalento());
			}
			atualizaDadosTabela();
		}
		
		
		
		public void apagarSkillsTalento(int idTalento) {
			
			for(int i = 0 ; i < talentoSkillsService.fetchTalentoSkills().size(); i++) {
		
				int id = talentoSkillsService.fetchTalentoSkills().get(i).getTalentoId();


				if(id == idTalento) {
					int idApagar =  talentoSkillsService.fetchTalentoSkills().get(i).getIdTalentoSkills();
					talentoSkillsService.apagarTalentoSkill(idApagar);
				}
			}
			
		
		}
		

		// Metodo para limpar os text fields e a seleção da tabela de talentos
		public void limpar() {
			tblTalentos.getSelectionModel().select(null);
			txtIdTalento.setText("");
			txtNomeTalento.setText("");
			txtPaisTalento.setText("");
			txtEmailTalento.setText("");
			txtTarifaTalento.setText("");
		
		}
		
		// Metodo que vai buscar os valores inseridos nos text fields e os associa aos respetivos atributos de um talento
		private void getValores(Talento c) {
			c.setTalentoNome(txtNomeTalento.getText());
			c.setTalentoPais(txtPaisTalento.getText());
			c.setTalentoEmail(txtEmailTalento.getText());
			c.setTalentoTarifa(Integer.parseInt(txtTarifaTalento.getText()));
			
		}


		//Metodo que atualiza os dados da tabela para que deste modo seja possivel ver os dados sempre atualizados
		private void atualizaDadosTabela() {
			tblTalentos.getItems().setAll(talentoService.fetchTalentos());
			limpar();
		}

		//Metodo que configura a estrutura da tabela
		private void configuraColunas() {
			clidTalento.setCellValueFactory(new PropertyValueFactory<>("idTalento"));
			clNomeTalento.setCellValueFactory(new PropertyValueFactory<>("talentoNome"));
			clPaisTalento.setCellValueFactory(new PropertyValueFactory<>("talentoPais"));
			clEmailTalento.setCellValueFactory(new PropertyValueFactory<>("talentoEmail"));
			clTarifaTalento.setCellValueFactory(new PropertyValueFactory<>("talentoTarifa"));
		}

		//Bindings
		private void configuraBindings() {
			
			//Boolean que verifica se os campos necessarios para um talento estão preenchidos
			BooleanBinding camposPreenchidos = txtNomeTalento.textProperty().isEmpty()
					.or(txtPaisTalento.textProperty().isEmpty()).or(txtEmailTalento.textProperty().isEmpty().or(txtTarifaTalento.textProperty().isEmpty()));
			
			//Boolean que verifica se existe algo selecionado na tabela talentos
			BooleanBinding algoSelecionado = tblTalentos.getSelectionModel().selectedItemProperty().isNull();
			
			//Botões que são ativados quando algo é selecionado na tabela ou os campos estão preenchidos
			btnApagarTalento.disableProperty().bind(algoSelecionado);
			btnAtualizarTalento.disableProperty().bind(algoSelecionado);
			btnLimparTalento.disableProperty().bind(algoSelecionado.and(camposPreenchidos));
			
			//Botão para guardar talento que é ativado quando os campos estão todos preenchidos e não existe um talento selecionado na tabela
			btnSaveTalento.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
			
			//Metodo que quando algum talento é selecionado na tabela vai buscar os dados do mesmo e preenche os respetivos text fields com os mesmos
			tblTalentos.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
				if (n != null) {
					txtIdTalento.setText(String.valueOf(n.getIdTalento()));
					txtNomeTalento.setText(n.getTalentoNome());
					txtPaisTalento.setText(n.getTalentoPais());
					txtEmailTalento.setText(n.getTalentoEmail());
					txtTarifaTalento.setText(String.valueOf(n.getTalentoTarifa()));
				}
			});
		}
		
   

}
