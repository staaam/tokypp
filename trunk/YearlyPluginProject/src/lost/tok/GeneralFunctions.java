package lost.tok;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;

public class GeneralFunctions {
	
	public static Document readFromXML(IFile file) {
		return readFromXML(file.getFullPath().toOSString());
	}

	public static Document readFromXML(String path) {
		try {
			return readFromXML(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static Document readFromXML(InputStream inputStream) {
		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		try {
			BufferedReader rdr = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			doc = reader.read(rdr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static void writeToXml(IFile file, Document document) {
		writeToXml(file.getFullPath().toOSString(), document);		
	}
	
	public static void writeToXml(String path, Document doc) {
		try {
			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setEncoding("UTF-8");

			BufferedWriter wrtr = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path), "UTF-8"));

			XMLWriter writer = new XMLWriter(wrtr, outformat);
			writer.write(doc);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
