package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	
	private Graph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> mappaCountry;
	private Map<Integer, Country> mappaNazioniConnesse;
	
	
	public Model() {
	
		grafo=new SimpleGraph<>(DefaultEdge.class);
		mappaCountry=new HashMap<>();
		mappaNazioniConnesse=new HashMap<Integer, Country>();
	}

	
	
	
	
	
	
	public void creagrafo(int anno) {
		
		grafo=new SimpleGraph<>(DefaultEdge.class);
		mappaCountry=new HashMap<>();
		mappaNazioniConnesse=new HashMap<Integer, Country>();
		
		BordersDAO dao=new BordersDAO();
		dao.loadAllCountries(mappaCountry);			//in questo modo la mappaCountry in automatico si riempe
		
		for(Border b: dao.getCountryPairs(anno, mappaCountry)) {
			
			DefaultEdge edge=grafo.getEdge(b.getC1(), b.getC2());
			
			if(!mappaNazioniConnesse.containsKey(b.getC1().getCodice())) {
				mappaNazioniConnesse.put(b.getC1().getCodice(), b.getC1());
			}else if(!mappaNazioniConnesse.containsKey(b.getC2().getCodice())) {
				mappaNazioniConnesse.put(b.getC2().getCodice(), b.getC2());
			}
			
			Graphs.addAllVertices(grafo, mappaNazioniConnesse.values());
			
			if(edge==null) {
				Graphs.addEdgeWithVertices(grafo, b.getC1(), b.getC2());		//aggiunge gli archi al grafo
			}	
			
		}
		
			
		
	}



	
	/**
	 * Metodo che mi permette di capire quanti stati sono confinanti con ogni stato (vertice del grafo) 
	 * nell'anno passato nell'interfaccia
	 * @return stringa di tutti gli stati con il numero di stati confinanti a loro
	 */
	
	public String elencoStatiGrado() {
				
		String s="";
		
		for(Country c: this.grafo.vertexSet()) {
			int grado=grafo.degreeOf(c);
			 s=s+" Stato: "+c.toString()+" confinante con "+grado+" stati\n";
		}
				
		return s;
		
	}
	
	
	

	/**
	 * Metodo che permette di capire in quanti pezzi sconnessi si compone il grafo. In questo caso si tratta dei continenti 
	 * @return numero componenti connesse 
	 */

	public int elecncoComponentiConnesse() {		//continenti dove ci sono gli stati connessi tra loro
		ConnectivityInspector<Country, DefaultEdge> conn=new ConnectivityInspector<Country, DefaultEdge>(grafo);
		return conn.connectedSets().size();
	}
	
	

	/**
	 * Metodo che trova tutti gli stati vicini allo stato passato come parametro
	 * @param stato
	 * @return lista di {@link Country}
	 */
	
	public List<Country> trovaStatiVicini(Country stato){
		
		
		List<Country> vicini=new ArrayList<Country>();
		
		BreadthFirstIterator<Country, DefaultEdge> iteratore=new BreadthFirstIterator<Country, DefaultEdge>(this.grafo, stato);
		
		while(iteratore.hasNext()) {
			vicini.add(iteratore.next());
		}
		
		vicini.remove(stato);
		
		return vicini;
		
		
	}
	
	
	

	public Graph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}
	
	public Map<Integer, Country> getMappaCountry() {
		return mappaCountry;
	}

	
	
}
