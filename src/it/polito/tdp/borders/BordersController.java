/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
			}
			
			model.creagrafo(anno);
			txtResult.appendText("Nell'anno "+anno+" ci sono: "+model.getGrafo().vertexSet().size()+" \n"+model.elencoStatiGrado());
			txtResult.appendText("Le componenti connesse sono "+model.elecncoComponentiConnesse());
			
		}
		

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
