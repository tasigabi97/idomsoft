package hu.idomsoft.firstmicroservice;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    	RestTemplate rt = new RestTemplate();
    	for(OkmanyDTO originalOkmanyDTO:okmLista) {
    		ResponseEntity<OkmanyDTO> re = rt.postForEntity("http://localhost:8082/", originalOkmanyDTO, OkmanyDTO.class);
    		//url should be in the properies file
    		OkmanyDTO correctedOkmanyDTO = re.getBody();
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

	public Szemely(
			String neme,
			ArrayList<OkmanyDTO> okmLista) {
		System.out.println("Szemely(");
		setNeme(neme);
		setOkmLista(okmLista);
		System.out.println(")");
	}

}
