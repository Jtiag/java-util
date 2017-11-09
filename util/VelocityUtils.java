
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class VelocityUtils {
	public static void export(Resource templateFile, Map parameters,
			Writer writer) throws Exception {
		export(templateFile.getFile(), parameters, writer);
	}

	public static void export(File file, Map parameters, Writer writer)
			throws Exception {
		VelocityEngine ve = new VelocityEngine();
		String folder = file.getParent();
		String fileName = file.getName();
		Properties properties = new Properties();
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, folder);
		properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");

		try {
			ve.init(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Template t = ve.getTemplate(fileName, "UTF-8");

		VelocityContext context = new VelocityContext();
		for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Entry entry = (Entry) iter.next();
			context.put(entry.getKey().toString(), entry.getValue());
		}

		t.merge(context, writer);
	}

	public static String getFileContent(String filePath, Map data) {
		try {
			File file=File.createTempFile("tmp_","");
			FileUtil.readClassPathResourceToFile(filePath, file);
			VelocityEngine ve = new VelocityEngine();
			String folder = file.getParent();
			String fileName = file.getName();
			Properties properties = new Properties();
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, folder);
			properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
			properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
			properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
			
			ve.init(properties);
			Template t = ve.getTemplate(fileName, "UTF-8");
			
			VelocityContext context = new VelocityContext();
			for (Iterator iter = data.entrySet().iterator(); iter.hasNext();) {
				Entry entry = (Entry) iter.next();
				context.put(entry.getKey().toString(), entry.getValue());
			}
			StringWriter sw = new StringWriter();
			t.merge(context, sw);
			if(file.exists()){
				file.delete();
			}
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
