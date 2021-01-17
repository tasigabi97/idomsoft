package hu.idomsoft.secondmicroservice;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.mycompany.mavenproject1.OkmanyDTO;
public class Okmany {
	private OkmanyDTO okmanyDTO = new OkmanyDTO();
	private static final Map<String, String> okmanyDict=new HashMap<>();
	private static final SimpleDateFormat sdf = new SimpleDateFormat(SecondMicroserviceApplication.dateFormat);
 	static			
	{
 		String kodszotatPath =new File("src/main/resources/kodszotar46_okmanytipus.json").getAbsolutePath();
    	try {
			FileReader reader = new FileReader(kodszotatPath);
	    	JSONParser jsonParser = new JSONParser();
	    	JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);
	        JSONArray rows=(JSONArray) jsonObject.get("rows");
	        for(Object o : rows) {
	        	JSONObject okmany = (JSONObject) o;
	        	String kod = (String) okmany.get("kod");
	        	String ertek = (String) okmany.get("ertek");
	        	okmanyDict.put(kod,ertek);	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public OkmanyDTO getOkmanyDTO() {
		return okmanyDTO;
	}

	private void setOkmTipus(String okmTipus) throws IllegalArgumentException{
		if (okmanyDict.containsKey(okmTipus)) {
			okmanyDTO.setOkmTipus(okmTipus);}
		else {
			throw new IllegalArgumentException("okmTipus "+okmTipus+" is not in "+okmanyDict.toString());
		}
    }
	
	private void setOkmanySzam(String okmanySzam) throws IllegalArgumentException{
		String okmanyName=okmanyDict.get(okmanyDTO.getOkmTipus());
		String regex;
		switch(okmanyName) {
		case "Személyazonosító igazolvány":
			regex="\\d{6}[a-zA-Z]{2}";
			break;
		case "Útlevél":
			regex="[a-zA-Z]{2}\\d{7}";
			break;
		case "Vezetői engedély":
			regex="\\d{2}[a-zA-Z]{6}";
			break;
			default:
				regex="\\w{0,10}";
		}
		if (okmanySzam.matches(regex)) {
			okmanyDTO.setOkmanySzam(okmanySzam);}
		else {
			throw new IllegalArgumentException("okmanySzam "+okmanySzam+" did not match the "+regex+" regex!");
		}		
	}
	
	private void setLejarDat(String lejarDat) {
		try {
			Date date= sdf.parse(lejarDat);
			okmanyDTO.setLejarDat(date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void setErvenyes(){
		Date lejarat= (Date) okmanyDTO.getLejarDat().clone();
		lejarat.setHours(23);
		lejarat.setMinutes(59);
		lejarat.setSeconds(59);
		okmanyDTO.setErvenyes(new Date().before(lejarat));
	}
	
	private void setOkmanyKep(byte[] okmanyKep) {
		String contentType="";
		int height=0;
		int width=0;
		try {
			InputStream in = new ByteArrayInputStream(okmanyKep);
			contentType = URLConnection.guessContentTypeFromStream(in);
			BufferedImage buf = ImageIO.read(in);
			height = buf.getHeight();
			width = buf.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!contentType.equals("image/jpeg")) {
			throw new IllegalArgumentException("Got "+contentType+". Please use image/jpeg.");
		}
		if (height != 1063 || width != 827) {
			throw new IllegalArgumentException(
					"Got "+Integer.toString(height)+"x"+Integer.toString(width)+
					". Please use 1063x827.");
		}
		okmanyDTO.setOkmanyKep(okmanyKep);		
	}
	public Okmany(
			String okmTipus,
			String okmanySzam,
			String lejarDat,
			byte[]  okmanyKep){
		setOkmTipus(okmTipus);//needed before okmany szam
		setOkmanySzam(okmanySzam);
		setLejarDat(lejarDat);//needed before ervenyes
		setErvenyes();
		setOkmanyKep(okmanyKep);
	}

}
