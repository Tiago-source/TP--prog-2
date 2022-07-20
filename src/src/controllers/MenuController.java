package controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 *
* @author Tiago Gomes 17501 & Vitor Rocha 17482
 */

public class MenuController {

	
	
	 @FXML
	    private Button btnGoToClientes;

	    @FXML
	    private Button btnGoToTalentos;

	    @FXML
	    private Button btnGoToSkills;
	    
	    @FXML
	    private Button btnSkillsTalento;
	    
	    @FXML
	    private Button btnExperienciasTalento;


	    @FXML
	    private Button btnPesquisaTalentos;
	    
	    @FXML
	    private Button btnPropostasTrabalho;
	    
	    @FXML
	    private Button btnTalentosPorProposta;
	    
	    @FXML
	    private Button btnRelatorios;

	    
	    @FXML
	    private Button btnExit;
	    
	    
	    // Metodo para sair da aplicação
	    @FXML
	    void exit(ActionEvent event) {
	    		Platform.exit();
	    }

	    // Metodos para navegar para as diversas paginas da aplicação
	    
	    @FXML
	    void goToClientes(ActionEvent event) throws IOException {
	    	
	    	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/clientes.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

	    }

	    @FXML
	    void goToSkills(ActionEvent event) throws IOException {
	    	
	    	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/skills.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

	    }
	    
	    @FXML
	    void goToTalentoSkills(ActionEvent event) throws IOException {
	    	
	    	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/skillsTalentos.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

	    }

	    @FXML
	    void goToTalentos(ActionEvent event) throws IOException {

	    	
	    	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/talentos.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();
	    	
	    }
	    
	    

	    @FXML
	    void goToTalentoExperiencias(ActionEvent event) throws IOException {
	    	
	      	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/experienciasTalento.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

	    }
	    
	    
	    @FXML
	    void goToPesquisaTalentos(ActionEvent event) throws IOException {
	    	
	       	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/pesquisaTalentos.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

	    }
	    
	    
	    
	    @FXML
	    void goToPropostasTrabalho(ActionEvent event) throws IOException {
	    	
	    	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/propostasTrabalho.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

	    }
	    
	    

	    @FXML
	    void goToTalentosPorProposta(ActionEvent event) throws IOException {
	    	
	      	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/talentosProposta.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

	    }
	    
	    
	    @FXML
	    void goToRelatorios(ActionEvent event) throws IOException {
	    	
	    	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/relatorios.fxml"));
            Parent home = loader.load();
            Scene homeScene = new Scene(home);
            Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            homeStage.setScene(homeScene);
            homeStage.setMaximized(false);
            homeStage.show();

	    }
	
	
}
