package hu.idomsoft.secondmicroservice;

import java.io.File;
import com.mycompany.mavenproject1.OkmanyDTO;
public class Okmany {
	private OkmanyDTO okmanyDTO = new OkmanyDTO();
	private static String path =new File("src/main/resources/kodszotar46_okmanytipus.json").getAbsolutePath();
					
	public OkmanyDTO getOkmanyDTO() {
		return okmanyDTO;
	}


	public void setOkmTipus(String okmTipus) {
        this.okmanyDTO.setOkmTipus(okmTipus);
    }
	

	
    public void setOkmanySzam(String okmanySzam) {
     
        this.okmanyDTO.setOkmanySzam(okmanySzam);
    }

}
