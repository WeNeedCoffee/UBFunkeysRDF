package coffee.weneed.funkeys.rdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
	public static byte[] downloadUrl(final URL toDownload) {
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
		String root = currentRelativePath.toAbsolutePath().toString();
		File input = new File(root + "/input");
		File output = new File(root + "/output");
		File info = new File(root + "/Readme.txt");
		if (!input.exists()) {
			input.mkdir();
		}
		if (!output.exists()) {
			output.mkdir();
		}
		if (!info.exists()) {
			String txt = "UBFunkeys RDF Util By Daleth with plenty of help from Vincentetcarine\nhttps://github.com/WeNeedCoffee/UBFunkeysRDF\n\nUsage: \n\n1. Run once to generate the required folders\n2. Put your gamedata files in /input, put .xml files as the decoded ones you want recompiled, and .rdf for encoded ones you want to decode.\n3. Run once.\n4. ???\n5. Profit! You have decoded/encoded the rdf files!\n\nDonate to my patreon if you feel my work helped you out! https://www.patreon.com/Dalethium\n";
			try {
				OutputStream out = Files.newOutputStream(Paths.get(info.toURI()));
				out.write(txt.getBytes());
				out.flush();
				out.close();
			} catch (IOException error) {
				error.printStackTrace();
			}
		}

		for (File file : input.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			if (file.getName().toLowerCase().endsWith(".rdf")) {
				String decoded = RDFUtil.decode(new String(RDFTest.downloadUrl(file.toURI().toURL()), StandardCharsets.ISO_8859_1));
				File decodedFile = new File(output.getAbsolutePath().toString() + "/" + file.getName().substring(0, file.getName().length() - 4) + ".xml");
				if (decodedFile.exists()) {
					decodedFile.delete();
				}
				try {
					final OutputStream decodedOut = Files.newOutputStream(Paths.get(decodedFile.toURI()));
					decodedOut.write(decoded.getBytes(StandardCharsets.ISO_8859_1));
					decodedOut.flush();
					decodedOut.close();
				} catch (IOException error) {
					error.printStackTrace();
				}
			} else if (file.getName().toLowerCase().endsWith(".xml")) {
				String encoded = RDFUtil.encode(new String(RDFTest.downloadUrl(file.toURI().toURL()), StandardCharsets.ISO_8859_1));
				File encodedFile = new File(output.getAbsolutePath().toString() + "/" + file.getName().substring(0, file.getName().length() - 4) + ".rdf");
				if (encodedFile.exists()) {
					encodedFile.delete();
				}
				try {
					OutputStream encodedOut = Files.newOutputStream(Paths.get(encodedFile.toURI()));
					encodedOut.write(encoded.getBytes(StandardCharsets.ISO_8859_1));
					encodedOut.flush();
					encodedOut.close();
				} catch (IOException error) {
					error.printStackTrace();
				}
			}
		}
	}
}
