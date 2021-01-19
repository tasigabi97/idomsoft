package hu.idomsoft.firstmicroservice;


import java.util.ArrayList;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mycompany.mavenproject1.OkmanyDTO;
import com.mycompany.mavenproject1.SzemelyDTO;

public class Szemely {
	private SzemelyDTO szemelyDTO = new SzemelyDTO();
	
    public SzemelyDTO getSzemelyDTO() {
		return szemelyDTO;
	}

	private void setNeme(String neme) {
    	if(neme.equals("N") || neme.equals("F")) {
    		System.out.println("setNeme("+neme+")");
    		szemelyDTO.setNeme(neme);}
    	else {
    		throw new IllegalArgumentException("Got "+neme+" but F or N needed.");}
    }
	
    private void setOkmLista(ArrayList<OkmanyDTO> okmLista) {
    	System.out.println("setOkmLista(");
    	ArrayList<OkmanyDTO> correctedOkmLista = new ArrayList<>();
    	ArrayList<String> ervenyesTipusok = new ArrayList<>();
    	RestTemplate rt = new RestTemplate();
    	for(OkmanyDTO originalOkmanyDTO:okmLista) {
    		ResponseEntity<OkmanyDTO> re = rt.postForEntity("http://localhost:8082/", originalOkmanyDTO, OkmanyDTO.class);
    		//url should be in the properies file
    		OkmanyDTO correctedOkmanyDTO = re.getBody();
    		if(correctedOkmanyDTO.isErvenyes()) {
        		String tipus=correctedOkmanyDTO.getOkmTipus();
    			if(ervenyesTipusok.contains(tipus)) {
    				throw new IllegalArgumentException("There are more than 1 okmany with type "+tipus);
    			}
    			ervenyesTipusok.add(tipus);
    		}
    		correctedOkmLista.add(correctedOkmanyDTO);
    		System.out.println("\tisErvenyes("+originalOkmanyDTO.isErvenyes()+"->"+correctedOkmanyDTO.isErvenyes()+")");
    		System.out.println("\tgetOkmTipus("+originalOkmanyDTO.getOkmTipus()+"->"+correctedOkmanyDTO.getOkmTipus()+")");
    		System.out.println("\tgetOkmanySzam("+originalOkmanyDTO.getOkmanySzam()+"->"+correctedOkmanyDTO.getOkmanySzam()+")");
    		System.out.println("\tgetLejarDat("+originalOkmanyDTO.getLejarDat()+"->"+correctedOkmanyDTO.getLejarDat()+")");
    		System.out.println("\tgetOkmanyKep("+originalOkmanyDTO.getOkmanyKep().length+"->"+correctedOkmanyDTO.getOkmanyKep().length+")");
    	}
    	szemelyDTO.setOkmLista(correctedOkmLista);
    	System.out.println(")");
    }
    
    private static boolean isGoodNev(String nev) {
    	if(nev.length() > 80) return false;
    	if(!nev.matches("[[a-z A-Z/-]['][öÖüÜóÓőŐúÚéÉáÁűŰ][Ä]]*"))return false;
    	String[] words = nev.split("\\s+");
    	int nevCount=0;
    	for(String word: words) {
    		if(!word.matches("[dD]r[.]"))nevCount++;
    	}
    	if( nevCount < 2) return false;
    	return true;
    }
    
    private void setVisNev(String visNev) {
    	if (isGoodNev(visNev) ){
    		szemelyDTO.setVisNev(visNev);
    		System.out.println("setVisNev("+visNev+")");}
    	else {
			throw new IllegalArgumentException();
		}
    }
    
    private void setSzulNev(String szulNev) {
    	if (isGoodNev(szulNev) ){
    		szemelyDTO.setSzulNev(szulNev);
    		System.out.println("setSzulNev("+szulNev+")");}
    	else {
			throw new IllegalArgumentException();
		}
    }
    
    private void setaNev(String aNev) {
    	if (isGoodNev(aNev) ){
    		szemelyDTO.setaNev(aNev);
    		System.out.println("setaNev("+aNev+")");}
    	else {
			throw new IllegalArgumentException();
		}
    }
    
    private void setSzulDat(Date szulDat) {
    	LocalDate ago18 = new LocalDate();
    	LocalDate ago120 = new LocalDate();
    	LocalDate szulLocalDate = new LocalDate(szulDat);
    	ago18=ago18.minusYears(18);
    	ago120=ago120.minusYears(120);
    	if(szulLocalDate.isAfter(ago18))throw new IllegalArgumentException("Too young"); 
    	if(szulLocalDate.isBefore(ago120))throw new IllegalArgumentException("Too old");  
   		szemelyDTO.setSzulDat(szulDat);
		System.out.println("setSzulDat("+szulDat+")");
    }

	public Szemely(
			String neme,
			String visNev,
			String szulNev,
			String aNev,
			Date szulDat,
			ArrayList<OkmanyDTO> okmLista) {
		System.out.println("Szemely(");
		setNeme(neme);
		setOkmLista(okmLista);
		setVisNev(visNev);
		setSzulNev(szulNev);
		setaNev(aNev);
		setSzulDat(szulDat);
		System.out.println(")");
	}

}
