package de.sz.thymeleaf.cmd;

import java.io.File;
import java.io.FileOutputStream;
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

	@Option(name = "--variables", required = false, usage = "properties file, where data is read from")
	private File variables;

	@Option(name = "--data", required = false, usage = "a text/csv file, where data is read from, expected tab separated with headlines")
	private File data;

	@Option(name = "--encoding", required = false, usage = "encoding for writer")
	private String encoding = "UTF-8";

	@Option(name = "--locale", required = false, usage = "template engine locale")
	private String locale = "en";

	@Option(name = "--static", required = false, usage = "directory with static resources, if set copy them next to outfile")
	private File staticResources = null;

	@Option(name = "--zip", required = false, usage = "if set, out is included with all required resources in a file <out>.zip, not build")
	private boolean zipParsedResults = false;

	public static void main(String[] args) throws IOException {

		Main main = new Main();
		final CmdLineParser parser = new CmdLineParser(main);

		try {
			parser.parseArgument(args);

			main.run();
		} catch (IOException | CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);            
		}

	}

	public Main() {
	}

	public void run() throws IOException {
		
		final ContextCreationStrategy strategy;

		PropertiesReadStrategy s = new PropertiesReadStrategy(variables, data, new Locale(locale));
		strategy = s;

		final TemplateWriter writer = new TemplateWriter(template);

		try (final OutputStream fos = new FileOutputStream(out.getAbsolutePath());
				final OutputStreamWriter outWriter = new OutputStreamWriter(fos, Charset.forName(encoding))) {
			writer.writeSingleFile(strategy, data, outWriter);
		}

		if (staticResources != null) {
			new StaticResourceDirectoryAssembler(out, staticResources).run();

			if (zipParsedResults) {
				final File directoryOutsideZip = out.getParentFile();
				final File zipFileName = new File(directoryOutsideZip, out.getName() + ".zip");
				new ZipDirectory().run(out.getParentFile(), zipFileName);
			}
		}

		LOGGER.info("Done");
	}

}
