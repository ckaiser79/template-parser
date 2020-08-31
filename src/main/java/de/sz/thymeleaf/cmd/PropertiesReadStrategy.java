package de.sz.thymeleaf.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.thymeleaf.context.Context;

/**
 * tab in properties file is \u0009
 * 
 * @author Christian
 *
 */
public class PropertiesReadStrategy implements ContextCreationStrategy {

	private final Locale locale;

	private Charset encoding = StandardCharsets.UTF_8;

	private File variablesFile;
	private File tsvDataFile;

	public PropertiesReadStrategy(
			final File variablesFile,
			final File tsvDataFile,
			final Locale locale) {
		this.locale = locale;
		this.tsvDataFile = tsvDataFile;
		this.variablesFile = variablesFile;

	}

	@Override
	public Context createContext() throws IOException {

		final Context context = new Context(locale);

		final Map<String, Object> variables;
		if (variablesFile != null) {
			variables = createVariablesFromPropertiesFile();
		} else {
			variables = Collections.emptyMap();
		}
		context.setVariables(variables);

		if (tsvDataFile != null) {
			final Iterable<CSVRecord> dataRecords = createCsvRecordsIterable(variables);
			final String name = (String) variables.getOrDefault("CSVCONFIG.variable_name", "DATA");
			context.setVariable(name, dataRecords);
		}

		return context;
	}

	private Map<String, Object> createVariablesFromPropertiesFile() throws IOException, FileNotFoundException {

		final Properties properties = new Properties();

		try (final InputStream fis = new FileInputStream(variablesFile.getAbsolutePath());
				final InputStreamReader reader = new InputStreamReader(fis, encoding)) {

			properties.load(reader);
		}

		final Map<String, Object> variables = new HashMap<>();

		for (Map.Entry<?, ?> entry : properties.entrySet()) {
			variables.put(entry.getKey().toString(), entry.getValue());
		}
		return variables;
	}

	private Iterable<CSVRecord> createCsvRecordsIterable(final Map<String, Object> variables)
			throws FileNotFoundException, IOException {
		final Iterable<CSVRecord> records;

		final String delimiter = (String) variables.getOrDefault("CSVCONFIG.delimiter", ",");

		// keep open to be available in template engine
		final InputStream fis = new FileInputStream(tsvDataFile.getAbsolutePath());
		final InputStreamReader reader = new InputStreamReader(fis, encoding);

		records = CSVFormat.DEFAULT
				.withAllowDuplicateHeaderNames(false)
				.withFirstRecordAsHeader()
				.withDelimiter(delimiter.charAt(0))
				.parse(reader);

		return records;
	}

}
