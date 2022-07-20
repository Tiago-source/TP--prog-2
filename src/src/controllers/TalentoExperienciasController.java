package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import classes.Talento;
import classes.TalentoExperiencia;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.TalentoExperienciaService;
import service.TalentoService;

public class TalentoExperienciasController implements Initializable{
	
	
	@FXML
    private ComboBox<String> comboTalentos;

    @FXML
    private TableView<TalentoExperiencia> tblExperiencias;

    @FXML
    private TableColumn<TalentoExperiencia, Integer> clidExperienciaTalento;

    @FXML
    private TableColumn<TalentoExperiencia, String> clNomeTalento;

    @FXML
    private TableColumn<TalentoExperiencia, String> clEmailTalento;

    @FXML
    private TableColumn<TalentoExperiencia, String> clTituloExperiencia;

    @FXML
    private TableColumn<TalentoExperiencia, String> clEmpresa;

    @FXML
    private TableColumn<TalentoExperiencia, String> clDataInicio;

    @FXML
    private TableColumn<TalentoExperiencia, String> clDataFim;

    @FXML
    private TextField txtIdExperiencia;

    @FXML
    private TextField txtTituloExperiencia;

    @FXML
    private TextField txtEmpresa;

    @FXML
    private TextField txtDataInicio;

    @FXML
    private TextField txtDataFim;

    @FXML
    private Button btnSaveExperienciaOfTalento;

    @FXML
    private Button btnAtualizarExperienciaOfTalento;

    @FXML
    private Button btnApagarExperienciaOfTalento;

    @FXML
    private Button btnLimparExperiencia;

    @FXML
    private Button btnMenuFromExperiencia;
    
    
    private TalentoExperienciaService talentoExperienciaService;
    private TalentoService talentoService;
    
    
    int idExperienciaTalento = 0;
    
    
	    /*Metodo utilizado ao iniciar a aplicação onde configuramos a estrutura da tabela
		 * e criamos novas instancias do talentoService e do talentoExperienciaService
		 * o que nos permite utilizar os metodos criados nos mesmos. Configuramos tambem
		 * os bindings, e inicializamos a combobox de talentos.
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			talentoExperienciaService = TalentoExperienciaService.getNewInstance();
			talentoService = TalentoService.getNewInstance();
			configuraColunas();
			configuraBindings();
			
			addComboTalentos();
		}
		
		
	// Metodo para regressar ao menu principal	
    @FXML
    void goToMenuFromTalentosExperiencias(ActionEvent event) throws IOException {

    		FXMLLoader loader = new FXMLLoader();
    	    loader.setLocation(getClass().getClassLoader().getResource("views/menu_inicial.fxml"));
    	    Parent home = loader.load();
    	    Scene homeScene = new Scene(home);
    	    Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	    homeStage.setScene(homeScene);
    	    homeStage.setMaximized(false);
    	    homeStage.show();
    	    
    	}


    // Metodo para adicionar dados á combobox dos talentos
    private void addComboTalentos() {
    	List<Talento> talentos2 = talentoService.fetchTalentos();
    		 for (int i = 0; i < talentos2.size(); i++) {
    	  
    			 comboTalentos.getItems().addAll(talentos2.get(i).getIdTalento()+" - "+talentos2.get(i).getTalentoNome());
    		 }
    }

    	/* Metodo para verificar se a data de uma nova experiencia de um talento
    	 * nao se sobrepoe as datas das experiencias previamente inseridas no 
    	 * sistema 
    	 */
		public int checkdata(TalentoExperiencia c) throws ParseException {
			
			List<TalentoExperiencia> talentos3 = talentoExperienciaService.fetchTalentoExperienciasPorNome(c.getTalentoNome());
			
			
			String ini = c.getTalentoExperienciaInicio();
			String end = c.getTalentoExperienciaFim();
			
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
			 
			Date StartB = format1.parse(ini);
			Date EndB = format1.parse(end);
			
			int count = 0;
			
			for(int i = 0; i<talentos3.size(); i++) {
				
				String iniarr = talentos3.get(i).getTalentoExperienciaInicio();
				String endarr = talentos3.get(i).getTalentoExperienciaFim();
				
				 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				
				 Date StartA = format.parse(iniarr);
				 Date EndA = format.parse(endarr);
				 
					System.out.println(StartA);
					System.out.println(EndA); 
					
				 if (EndB.before(StartA) || StartB.after(EndA)) {
					 	
					 count = 0 ;
		     
					 
				    }else{
				    	
				    count++;	
				    }
		
			
			}
		
			return count;
		} 
		    
		    
    
		/*Metodo para adicionar uma nova experiencia, é neste metodo que executamos o 
		 * metodo que vai verificar as datas e caso se sobreponham é emitido um alert no
		 * ecra do utilizador
		 */
		public void guardarTalentoExperiencia() throws ParseException {
			TalentoExperiencia c = new TalentoExperiencia();
			getValores(c);
			int val = checkdata(c);
			System.out.println(val);	
			
			if(val != 0) {
					 	Alert alert = new Alert(AlertType.INFORMATION);
			            alert.setTitle("Information Dialog");
			            alert.setHeaderText(null);
			            alert.setContentText("Data sobrepostas");
			            alert.showAndWait();
			} else if (val == 0) {
				     	Alert alert = new Alert(AlertType.INFORMATION);
			            alert.setTitle("Information Dialog");
			            alert.setHeaderText(null);
			            alert.setContentText("Experiencia Adicionada com sucesso");
			            alert.showAndWait();
			            
				    	talentoExperienciaService.guardarTalentoExperiencia(c);
				    	atualizaDadosTabela();
				    	limparAfterApagar();
			}
		}

		// Metodo para apagar uma experiencia de um talento
		public void apagarTalentoExperiencia() {
			TalentoExperiencia c = tblExperiencias.getSelectionModel().getSelectedItem();
			talentoExperienciaService.apagarTalentoExperiencia(c.getIdTalentoExperiencia());
			atualizaDadosTabela();
			limparAfterApagar();
		}

		// Metodo  para atualizar uma expeiencia de um talento
		public void atualizarTalentoExperiencia() {
			TalentoExperiencia c = tblExperiencias.getSelectionModel().getSelectedItem();
			getValoresActualizar(c);
			talentoExperienciaService.atualizarTalentoExperiencia(c);
			atualizaDadosTabela();
		}

		//Metodo para limpar os text fields da janela, a seleção da combobox de talentos e atabela das experiencias
		public void limpar() {
			tblExperiencias.getItems().clear();
			comboTalentos.getSelectionModel().select(null);
			txtIdExperiencia.setText("");
			txtTituloExperiencia.setText("");
			txtEmpresa.setText("");
			txtDataInicio.setText("");
			txtDataFim.setText("");
		
		}

		
		//Metodo para limpar os text fields da janela
		public void limparAfterApagar() {
			txtIdExperiencia.setText("");
			txtTituloExperiencia.setText("");
			txtEmpresa.setText("");
			txtDataInicio.setText("");
			txtDataFim.setText("");
		
		}

		/*Metodo que quando algum talento é selecionado na combobox vai buscar os dados desse talento
		 * e vai buscar os dados inseridos nos text fields e associa-os aos atributos de uma experiencia
		 * de um talento		 
		 */
		private void getValores(TalentoExperiencia c) {
			String Talento = comboTalentos.getValue().toString();
			int Talentoid2 = Integer.parseInt(Talento.replaceAll("[\\D]", ""));
			String nomeTalento = talentoService.fetchTalentosPorId(Talentoid2).getTalentoNome();
			String emailTalento = talentoService.fetchTalentosPorId(Talentoid2).getTalentoEmail();
			
			c.setIdTalentoExperiencia(idExperienciaTalento);
			c.setTalentoId(Talentoid2);
			c.setTalentoNome(nomeTalento);
			c.setTalentoEmail(emailTalento);
			
			c.setTalentoExperienciaTitulo(txtTituloExperiencia.getText());
			c.setTalentoExperienciaEmpresa(txtEmpresa.getText());
			c.setTalentoExperienciaInicio(txtDataInicio.getText());
			c.setTalentoExperienciaFim(txtDataFim.getText());
		}
		
		
		// Metodo para atualizar uma experiencia de um talento
		private void getValoresActualizar(TalentoExperiencia c) {
			String Talento = comboTalentos.getValue().toString();
			int Talentoid2 = Integer.parseInt(Talento.replaceAll("[\\D]", ""));
			String nomeTalento = talentoService.fetchTalentosPorId(Talentoid2).getTalentoNome();
			String emailTalento = talentoService.fetchTalentosPorId(Talentoid2).getTalentoEmail();
			
			c.setIdTalentoExperiencia(Integer.parseInt(txtIdExperiencia.getText()));
			c.setTalentoNome(nomeTalento);
			c.setTalentoEmail(emailTalento);
			
			c.setTalentoExperienciaTitulo(txtTituloExperiencia.getText());
			c.setTalentoExperienciaEmpresa(txtEmpresa.getText());
			c.setTalentoExperienciaInicio(txtDataInicio.getText());
			c.setTalentoExperienciaFim(txtDataFim.getText());
		}
		
		
		//Metodo chamado para atualizar os dados da tabela, desta forma vemos sempre os dados atualizados
		private void atualizaDadosTabela() {
			String Talento = comboTalentos.getValue().toString();
			int Talentoid = Integer.parseInt(Talento.replaceAll("[\\D]", ""));
			tblExperiencias.getItems().setAll(talentoExperienciaService.fetchTalentoExperienciasPorIdDoTalento(Talentoid));
		}
		
		//Configura a estrutura da tabela
		private void configuraColunas() {
			clidExperienciaTalento.setCellValueFactory(new PropertyValueFactory<>("idTalentoExperiencia"));
			clNomeTalento.setCellValueFactory(new PropertyValueFactory<>("talentoNome"));
			clEmailTalento.setCellValueFactory(new PropertyValueFactory<>("talentoEmail"));
			clTituloExperiencia.setCellValueFactory(new PropertyValueFactory<>("talentoExperienciaTitulo"));
			clEmpresa.setCellValueFactory(new PropertyValueFactory<>("talentoExperienciaEmpresa"));
			clDataInicio.setCellValueFactory(new PropertyValueFactory<>("talentoExperienciaInicio"));
			clDataFim.setCellValueFactory(new PropertyValueFactory<>("talentoExperienciaFim"));
		}
		
		
		//Bindings
		private void configuraBindings() {
			
			//Boolean que verifica se os campos necessarios para uma experiencia estão preenchidos
			BooleanBinding camposPreenchidos = txtTituloExperiencia.textProperty().isEmpty()
					.or(txtEmpresa.textProperty().isEmpty().or(txtDataInicio.textProperty().isEmpty()).or(txtDataFim.textProperty().isEmpty())).or(comboTalentos.getSelectionModel().selectedItemProperty().isNull());
			
			//Boolean que verifica se existe alguma experiencia selecionada na tabela
			BooleanBinding algoSelecionado = tblExperiencias.getSelectionModel().selectedItemProperty().isNull();
			
			//Botões que são ativados caso exista algo selecionado na tabela ou caso os campos estejam preenchidos
			btnApagarExperienciaOfTalento.disableProperty().bind(algoSelecionado);
			btnAtualizarExperienciaOfTalento.disableProperty().bind(algoSelecionado);
			btnLimparExperiencia.disableProperty().bind(algoSelecionado.and(camposPreenchidos));
			
			//Botão para guardar experiencia que é ativado quando os campos estão preenchidos e não existe nenhuma experiencia selecionada na tabela
			btnSaveExperienciaOfTalento.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
			
			/*Metodo que quando alguma experiencia é selecionada na tabela os dados da mesma são 
			 * atribuidos aos respetivos text fields 
			 */
			tblExperiencias.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
				if (n != null) {
					
					txtIdExperiencia.setText(String.valueOf(n.getIdTalentoExperiencia()));
					txtTituloExperiencia.setText(n.getTalentoExperienciaTitulo());
					txtEmpresa.setText(n.getTalentoExperienciaEmpresa());
					txtDataInicio.setText(n.getTalentoExperienciaInicio());
					txtDataFim.setText(n.getTalentoExperienciaFim());
				}
			});
			
			
			/* Metodo utilizado para que quando um talento é selecionado na combobox 
			 * os dados da tabela sejam atualizados para esse talento e os text fields sejam limpos
			 */
			comboTalentos.getSelectionModel().selectedItemProperty().addListener((b, o ,n) -> {
				if (n != null) {
					atualizaDadosTabela();
					limparAfterApagar();
					}
					
					
				});
			}
		}
