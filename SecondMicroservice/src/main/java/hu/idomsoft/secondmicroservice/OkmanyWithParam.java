package hu.idomsoft.secondmicroservice;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class OkmanyWithParam extends Okmany {
	public OkmanyWithParam(
			String okmTipus,
			String okmanySzam,
			String lejarDat,
			MultipartFile okmanyKep) throws IOException {
		super(okmTipus, okmanySzam, Okmany.stringToDate(lejarDat), okmanyKep.getBytes());
	}
}
