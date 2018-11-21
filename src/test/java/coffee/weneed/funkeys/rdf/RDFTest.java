package coffee.weneed.funkeys.rdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import coffee.weneed.utils.LogicUtil;

public class RDFTest {

	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
		String s = new String(LogicUtil.downloadUrl(
				new File("G:\\UBFunkeys\\U.B. Funkeys 5.0 Full by Daleth\\U.B. Funkeys\\RadicaGame\\data\\system\\config.rdf").toURI().toURL()),
				"ISO-8859-1");
		String d = RDFUtil.decode(s);
		File f = new File("G:\\UBFunkeys\\New folder\\config.txt");
		f.delete();
		try {
			FileOutputStream st = new FileOutputStream(f);
			st.write(d.getBytes(StandardCharsets.ISO_8859_1));
			st.flush();
			st.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String e1 = RDFUtil.encode(d);
		f = new File("G:\\UBFunkeys\\New folder\\config.rdf");
		f.delete();
		try {
			FileOutputStream st = new FileOutputStream(f);
			st.write(e1.getBytes(StandardCharsets.ISO_8859_1));
			st.flush();
			st.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
