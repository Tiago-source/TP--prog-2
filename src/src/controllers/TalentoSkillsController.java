package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import classes.Skill;
import classes.Talento;
import classes.TalentoSkills;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.SkillsService;
import service.TalentoService;
import service.TalentoSkillsService;


/**
*
* @author Tiago Gomes 17501 & Vitor Rocha 17482
*/

public class TalentoSkillsController implements Initializable {
	
	@FXML
    private ComboBox<String> comboTalentos;

    @FXML
    private TableView<TalentoSkills> tblSkillsByTalento;

    @FXML
    private TableColumn<TalentoSkills, Integer> clidSkillTalento;

    @FXML
    private TableColumn<TalentoSkills, String> clNomeTalento;

    @FXML
    private TableColumn<TalentoSkills, String> clEmailTalento;

    @FXML
    private TableColumn<TalentoSkills, String> clSkill;
    
    @FXML
    private TableColumn<TalentoSkills, Integer> clExperiencia;

    @FXML
    private ComboBox<String> comboSkills;

    @FXML
    private TextField txtIdSkill;

    @FXML
    private TextField txtNomeSkill;

    @FXML
    private TextField txtAreaSkill;

    @FXML
    private TextField txtExperiencia;

    @FXML
    private Button btnSaveSkillOfTalento;

    @FXML
    private Button btnApagarSkillOfTalento;

    @FXML
    private Button btnLimparSkill;

    @FXML
    private Button btnMenuFromSkills;
    
    private int idSkillTalento = 0;
    
    private TalentoSkillsService talentoSkillsService;
    private SkillsService skillsService;
    private TalentoService talentoService;
    
    
    
	    /*Metodo utilizado ao iniciar a aplicação onde configuramos a estrutura da tabela
		 * e criamos novas instancias do skillsService,talentoService e do talentoSkillsService
		 * o que nos permite utilizar os metodos criados nos mesmos. Configuramos tambem
		 * os bindings,definimos os estado inicial da comboSkills como disable e inicializamos as comboboxs.
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			talentoSkillsService = TalentoSkillsService.getNewInstance();
			skillsService = SkillsService.getNewInstance();
			talentoService = TalentoService.getNewInstance();
			configuraColunas();
			configuraBindings();
			
			
			comboSkills.setDisable(true);
			addComboSkills();
			addComboTalentos();
		}
		
		
		
		// Metodos para carregar dados para as comboboxs
		private void addComboSkills() {
	    	List<Skill> skills2 = skillsService.fetchSkills();
	    		 for (int i = 0; i < skills2.size(); i++) {
	    	  
	    			 comboSkills.getItems().addAll(skills2.get(i).getSkillNome());
	    		 }
	    }
		
	
		private void addComboTalentos() {
	    	List<Talento> talentos2 = talentoService.fetchTalentos();
	    		 for (int i = 0; i < talentos2.size(); i++) {
	    	  
	    			 comboTalentos.getItems().addAll(talentos2.get(i).getIdTalento()+" - "+talentos2.get(i).getTalentoNome());
	    		 }
	    }
	
    


	// Metodo para regressar ao menu principal
    @FXML
    void goToMenuFromTalentosSkills(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
        Parent home = loader.load();
        Scene homeScene = new Scene(home);
        Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        homeStage.setScene(homeScene);
        homeStage.setMaximized(false);
        homeStage.show();
        
    }
    
    
    // Metodo para guardar uma nova skill associada a um talento
    public void guardarTalentoSkill() {
		TalentoSkills c = new TalentoSkills();
		getValores(c);
		talentoSkillsService.guardarTalentoSkill(c);
		atualizaDadosTabela();
		limparAfterDelete();
	}

    
    //Metodo para apagar uma skill associada a um talento
	public void apagarTalentoSkill() {
		TalentoSkills c = tblSkillsByTalento.getSelectionModel().getSelectedItem();
		talentoSkillsService.apagarTalentoSkill(c.getIdTalentoSkills());
		atualizaDadosTabela();
		limparAfterDelete();
	}

	//Metodo para limpar todos os text fields e a as seleções das comboboxs e limpar os dados da tabela
	public void limpar() {
		tblSkillsByTalento.getItems().clear();
		comboSkills.getSelectionModel().select(null);
		comboTalentos.getSelectionModel().select(null);
		txtIdSkill.setText("");
		txtNomeSkill.setText("");
		txtAreaSkill.setText("");
		txtExperiencia.setText("");

	}
	
	//Metodo usado depois de apagar um skill associada a um talento para limpar os text fields e a seleção da combobox skills
	public void limparAfterDelete() {
		comboSkills.getSelectionModel().select(null);
		txtIdSkill.setText("");
		txtNomeSkill.setText("");
		txtAreaSkill.setText("");
		txtExperiencia.setText("");

	}
	
	
	/*Metodo que vai buscar os dados as comboboxs do talento e das skills
	 *e aos text fields e os associa aos respetivos atributos de uma skill associada a um talento
	 */
	private void getValores(TalentoSkills c) {
		String Talento = comboTalentos.getValue().toString();
		int Talentoid2 = Integer.parseInt(Talento.replaceAll("[\\D]", ""));
		String nomeTalento = talentoService.fetchTalentosPorId(Talentoid2).getTalentoNome();
		String emailTalento = talentoService.fetchTalentosPorNome(nomeTalento).getTalentoEmail();
		String nomeSkill = comboSkills.getValue().toString();
		
		c.setIdTalentoSkills(idSkillTalento);
		c.setTalentoId(Talentoid2);
		c.setTalentoSkillsNome(nomeTalento);
		c.setTalentoSkillsEmail(emailTalento);
		c.setTalentoSkillsNomeSkill(nomeSkill);
		c.setTalentoSkillAnosExperiencia(Integer.parseInt(txtExperiencia.getText()));
	}


	// Metodo atualizar os dados da tabela deste modo é possivel vermos sempre os dados atualizados
	private void atualizaDadosTabela() {
		String Talento = comboTalentos.getValue().toString();
		int Talentoid = Integer.parseInt(Talento.replaceAll("[\\D]", ""));
		tblSkillsByTalento.getItems().setAll(talentoSkillsService.fetchTalentoSkillsPorIdDoTalento(Talentoid));
	}

	// Metodo para definir a estrutura da tabela 
	private void configuraColunas() {
		clidSkillTalento.setCellValueFactory(new PropertyValueFactory<>("idTalentoSkills"));
		clNomeTalento.setCellValueFactory(new PropertyValueFactory<>("talentoSkillsNome"));
		clEmailTalento.setCellValueFactory(new PropertyValueFactory<>("talentoSkillsEmail"));
		clSkill.setCellValueFactory(new PropertyValueFactory<>("talentoSkillsNomeSkill"));
		clExperiencia.setCellValueFactory(new PropertyValueFactory<>("talentoSkillAnosExperiencia"));
	}

	
	//Bindings
	private void configuraBindings() {
		
		//Boolean que verifica se todos os campos necessarios a uma skill de um talento estão preenchidos
		BooleanBinding camposPreenchidos = txtIdSkill.textProperty().isEmpty().or(txtNomeSkill.textProperty().isEmpty())
				.or(txtAreaSkill.textProperty().isEmpty().or(txtExperiencia.textProperty().isEmpty()));
		
		//Boolean que verifica se existe algo selecionado na tabela ou na combo skills
		BooleanBinding algoSelecionado = comboSkills.getSelectionModel().selectedItemProperty().isNull();
		BooleanBinding algoSelecionado2 = tblSkillsByTalento.getSelectionModel().selectedItemProperty().isNull();
		
		// Botoões ativados quando algo é selecionado na tabela ou na combobox
		btnApagarSkillOfTalento.disableProperty().bind(algoSelecionado2);
		btnLimparSkill.disableProperty().bind(algoSelecionado);
		
		// Botão para guardar skill associada a um talento que é ativado quando os campos estão preenchidos
		btnSaveSkillOfTalento.disableProperty().bind(camposPreenchidos);
		
		/*Metodo que verifica se existe alguma skill selecionada na combobox skills 
		 * e caso exista insere os dados da skill nos respetivos text fields
		 */
		comboSkills.getSelectionModel().selectedItemProperty().addListener((b, o ,n) -> {
		if (n != null) {
				String nomeSkill = comboSkills.getValue().toString();
				int idSkill = skillsService.fetchSkillsPorNome(nomeSkill).getIdSkill();
				String areaSkill = skillsService.fetchSkillsPorNome(nomeSkill).getSkillArea();
				
				txtIdSkill.setText(String.valueOf(idSkill));
				txtNomeSkill.setText(nomeSkill);
				txtAreaSkill.setText(areaSkill);
			}
			
			
		});
		
		
		/*Metodo que verifica se existe algum talento selecionado na combobox talentos 
		 * e caso exista atializa os dados da tabela e ativa a combobox skills
		 */
		comboTalentos.getSelectionModel().selectedItemProperty().addListener((b, o ,n) -> {
			if (n != null) {
				comboSkills.setDisable(false);
				atualizaDadosTabela();
				}else if(n==null){
					comboSkills.setDisable(true);
				}
			});
	}
	

 

}
