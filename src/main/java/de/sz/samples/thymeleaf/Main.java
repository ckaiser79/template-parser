package de.sz.samples.thymeleaf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Locale;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	@Option(name = "--template", required = true, usage = "name of template from classpath to use")
	private String template;

	@Option(name = "--out", required = true, usage = "write into this file")
	private File out;

	@Option(name = "--data", required = true, usage = "properties file, where data is read from")
	private File input;

	@Option(name = "--encoding", required = false, usage = "encoding for writer")
	private String encoding = "UTF-8";

	@Option(name = "--locale", required = false, usage = "template engine locale")
	private String locale = "en";

	@Option(name = "--static", required = false, usage = "directory with static resources, if set copy them next to outfile")
	private File staticResources = null;

	@Option(name = "--zip", required = false, usage = "if set, out is included with all required resources in a file <out>.zip, not build")
	private boolean zipParsedResults = false;

	public static void main(String[] args) throws IOException, CmdLineException {

		Main main = new Main();
		final CmdLineParser parser = new CmdLineParser(main);

		parser.parseArgument(args);

		main.run();
	}

	public Main() {
	}

	public void run() throws IOException {
		final ContextCreationStrategy strategy;

		if (input.getName().endsWith(".properties")) {
			try (final FileInputStream fis = new FileInputStream(input)) {
				PropertiesReadStrategy s = new PropertiesReadStrategy(fis);
				s.setLocale(new Locale(locale));
				strategy = s;

			}
		} else {
			throw new IllegalArgumentException("Unknown file type " + input.getName());
		}

		final TemplateWriter writer = new TemplateWriter(template);
		
		try (final OutputStream fos = new FileOutputStream(out.getAbsolutePath());
				final OutputStreamWriter outWriter = new OutputStreamWriter(fos, Charset.forName(encoding))) {
			writer.writeSingleFile(strategy, outWriter);
		}

		LOGGER.info("Done");
	}

}
