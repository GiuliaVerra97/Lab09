package it.polito.tdp.borders.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		int anno=1817;
		System.out.println("Creo il grafo relativo all' anno "+anno);
		model.creagrafo(anno);
		System.out.println("vertici "+model.getGrafo().vertexSet().size()+ " archi "+ model.getGrafo().edgeSet().size());

		System.out.println(model.elencoStatiGrado());
		System.out.println("Numero componenti connesse "+model.elecncoComponentiConnesse());

		
		
	}

}
