package de.sz.samples.thymeleaf;

import java.io.IOException;
import java.io.Writer;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateWriter {

	private final String templateName;
	private final TemplateEngine templateEngine;
	
	/**
	 * @param templateName name of template in classpath:templates/ directory,
	 *                       must match to templateEngine settings.
	 */
	public TemplateWriter(final String templateName) {
		this.templateName = templateName;
		templateEngine = createThymeleafEngine();
	}
	/**
	 * 
	 * Write content of template name in <code>templateName.out.html</code>
	 * 
	 * @param out writer to store parsed template in   
	 */
	public void writeSingleFile(final ContextCreationStrategy contextStrategy, final Writer out) throws IOException {

		// put variables into template
		final Context context = contextStrategy.createContext();
		templateEngine.process(templateName, context, out);

	}


	/**
	 * Look for html files in classpath:templates/
	 * 
	 * @return never null
	 */
	private static TemplateEngine createThymeleafEngine() {
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setPrefix("templates/");
		resolver.setTemplateMode("HTML");
		resolver.setSuffix(".html");

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(resolver);
		return templateEngine;
	}

}
