package coffee.weneed.funkeys.rdf;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import coffee.weneed.utils.LogicUtil;

public class RDFTest {

	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
		System.out.println(RDFUtil.decode(new String(LogicUtil.downloadUrl(new File("G:\\UBFunkeys\\U.B. Funkeys 5.0 Full by Daleth\\U.B. Funkeys\\RadicaGame\\data\\system\\config.rdf").toURI().toURL()), "ISO-8859-1").toCharArray()));
		
	}
}
