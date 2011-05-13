package sem.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class sparqlApi{
	
	final static String RDF_FILE = "http://139.18.2.172:8893/sparql";
	Context mContext;
	
	public sparqlApi(Context context) {
		mContext = context;
		 // create an empty model
		 Model model = ModelFactory.createDefaultModel();

		 // use the FileManager to find the input file
		 InputStream in = FileManager.get().open( RDF_FILE);
		 if (in == null) {
			    throw new IllegalArgumentException(
			                                 "File: " + RDF_FILE + " not found");
			}

			// read the RDF/XML file
			model.read(in, null);

			// write it to standard out
			model.write(System.out);
	}
}
