package coffee.weneed.funkeys.rdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RDFTest {

	/**
	 * https://stackoverflow.com/questions/2295221/java-net-url-read-stream-to-byte
	 *
	 * @author StackOverflow:ron-reiter
	 * @param toDownload the to download
	 * @return byte array
	 */
	public static byte[] downloadUrl(URL toDownload) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			byte[] chunk = new byte[4096];
			int bytesRead;

			URLConnection con = toDownload.openConnection();
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			InputStream stream = con.getInputStream();
			while ((bytesRead = stream.read(chunk)) > 0) {
				outputStream.write(chunk, 0, bytesRead);
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return outputStream.toByteArray();
	}

	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {

		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		File input = new File(s + "/input");
		File output = new File(s + "/output");
		File info = new File(s + "/Readme.txt");
		if (!input.exists()) {
			input.mkdir();
		}
		if (!output.exists()) {
			output.mkdir();
		}
		if (!info.exists()) {
			String txt = "UBFunkeys RDF Util By Daleth with plenty of help from Vincentetcarine\nhttps://github.com/WeNeedCoffee/UBFunkeysRDF\n\nUsage: \n\n1. Run once to generate the required folders\n2. Put your gamedata files in /input, put .xml files as the decoded ones you want recompiled, and .rdf for encoded ones you want to decode.\n3. Run once.\n4. ???\n5. Profit! You have decoded/encoded the rdf files!\n\nDonate to my patreon if you feel my work helped you out! https://www.patreon.com/Dalethium\n";
			try {
				FileOutputStream st = new FileOutputStream(info);
				st.write(txt.getBytes());
				st.flush();
				st.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (File f : input.listFiles()) {
			if (f.isDirectory()) {
				continue;
			}
			if (f.getName().toLowerCase().endsWith(".rdf")) {
				String d = RDFUtil.decode(new String(RDFTest.downloadUrl(f.toURI().toURL()), StandardCharsets.ISO_8859_1));
				File f1 = new File(output.getAbsolutePath().toString() + "/" + f.getName().substring(0, f.getName().length() - 4) + ".xml");
				if (f1.exists()) {
					f1.delete();
				}
				try {
					FileOutputStream st = new FileOutputStream(f1);
					st.write(d.getBytes(StandardCharsets.ISO_8859_1));
					st.flush();
					st.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (f.getName().toLowerCase().endsWith(".xml")) {
				String d = RDFUtil.encode(new String(RDFTest.downloadUrl(f.toURI().toURL()), StandardCharsets.ISO_8859_1));
				File f1 = new File(output.getAbsolutePath().toString() + "/" + f.getName().substring(0, f.getName().length() - 4) + ".rdf");
				if (f1.exists()) {
					f1.delete();
				}
				try {
					FileOutputStream st = new FileOutputStream(f1);
					st.write(d.getBytes(StandardCharsets.ISO_8859_1));
					st.flush();
					st.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
