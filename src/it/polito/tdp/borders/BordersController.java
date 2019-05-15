/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader
	
	@FXML
	private ComboBox<Country> menu;

	@FXML
	private Button btnVicini;
	

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		
		txtResult.clear();
		int anno=0;
		
		if(txtAnno.getText()==null) {
			txtResult.setText("Inserire anno\n");
			return;
		}else {
			try {
				anno=Integer.parseInt(txtAnno.getText());
			}catch(NumberFormatException e) {
				txtResult.setText("Errore: bisogna inserire un numero intero");
				return;
			}
			
		} 
		
		if(anno<1816 || anno>2016){
			txtResult.setText("Bisogna inserire un anno compresto tra 1816 e 2000");
			return;
		}else {
			
			model.creagrafo(anno);
			txtResult.appendText("Nell'anno "+anno+" ci sono: "+model.getGrafo().vertexSet().size()+" \n"+model.elencoStatiGrado());
			txtResult.appendText("Le componenti connesse sono "+model.elecncoComponentiConnesse());
			
		}
		
		btnVicini.setDisable(false);
		
		menu.getItems().addAll(model.getGrafo().vertexSet());		//il menu può anche essere settato dentro un action di un bottone

		
	}
	
	
	
	
	
	
	
	
	@FXML
	void doVicini(ActionEvent event) {
		
		txtResult.clear();
		
		if(txtAnno.getText()==null || menu.getValue()==null) {
			txtResult.setText("Inserire anno\n");
			return;
		}else {
			
			Country stato=menu.getValue();
			txtResult.appendText("Nell'anno "+txtAnno.getText()+" lo stato "+stato.getNome()+" è vicino a  \n");			
			
			try {
				
			for(Country c: model.trovaStatiVicini(stato)) {
				txtResult.appendText(c.getNome()+"\n");
			}
			
			}catch(Exception e) {
				txtResult.setText(" nessuno stato ");
			}
			
		}
		
		btnVicini.setDisable(true);
		
	 }

	
	
	

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}

	public void setModel(Model model2) {
		this.model=model2;
	}
}
