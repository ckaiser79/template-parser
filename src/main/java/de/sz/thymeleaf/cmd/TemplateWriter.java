package de.sz.thymeleaf.cmd;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateWriter {

	private final String templateName;
	private final TemplateEngine templateEngine;

	/**
	 * @param templateName name of template in classpath:templates/ directory,
	 *                     must match to templateEngine settings.
	 */
	public TemplateWriter(final String templateName, final String mode, final String encoding) {
		this.templateName = templateName;
		templateEngine = createThymeleafEngine(mode, encoding);
	}

	/**
	 * 
	 * Write content of template name in <code>templateName.out.html</code>
	 * 
	 * @param data
	 * 
	 * @param out  writer to store parsed template in
	 */
	public void writeSingleFile(final PropertiesReadStrategy contextStrategy, final File data, final Writer out)
			throws IOException {

		// put variables into template
		final Context context = contextStrategy.createContext();
		templateEngine.process(templateName, context, out);

	}

	/**
	 * Look for html files in classpath:templates/
	 * 
	 * @return never null
	 */
	private TemplateEngine createThymeleafEngine(final String mode, final String encoding) {
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setPrefix("templates/");
		resolver.setCacheable(false);
		resolver.setCharacterEncoding(encoding);
		
		if("txt".equalsIgnoreCase(mode)) {
			resolver.setTemplateMode(TemplateMode.TEXT);
			resolver.setSuffix(".txt");
		}
		if("html".equalsIgnoreCase(mode)) {
			resolver.setTemplateMode(TemplateMode.HTML);
			resolver.setSuffix(".html");
		}
		if("xml".equalsIgnoreCase(mode)) {
			resolver.setTemplateMode(TemplateMode.XML);
			resolver.setSuffix(".xml");
		}

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(resolver);		
		return templateEngine;
	}

}
