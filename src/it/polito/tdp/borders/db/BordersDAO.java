package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	
	
	
	
	public List<Country> loadAllCountries(Map<Integer, Country> mappaCountry) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				
				if(!mappaCountry.containsKey(rs.getInt("ccode"))) {
					Country c=new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
					mappaCountry.put(rs.getInt("ccode"), c);
					result.add(c);
				}else {
					result.add(mappaCountry.get(rs.getInt("ccode")));
				}
				
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	
	
	
	
	
	
	public List<Border> getCountryPairs(int anno, Map<Integer, Country> mappaCountry ) {

		//System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		
		final String sql="SELECT state1no, state2no, YEAR, conttype " + 
				"FROM contiguity " + 
				"WHERE YEAR<=? and conttype=? ";
		
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, 1);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Border b1=new Border(mappaCountry.get(rs.getInt("state1no")), mappaCountry.get(rs.getInt("state2no")), rs.getInt("YEAR"), rs.getInt("conttype"));
				Border b2=new Border(mappaCountry.get(rs.getInt("state2no")), mappaCountry.get(rs.getInt("state1no")), rs.getInt("YEAR"), rs.getInt("conttype"));

				
				if(!result.contains(b1) && !result.contains(b2)) {
					result.add(b1);
				}
				
				
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		
		
	}
	
	
	
	
	
	
}
