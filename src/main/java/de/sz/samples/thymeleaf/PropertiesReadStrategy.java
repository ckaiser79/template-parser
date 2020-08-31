package de.sz.samples.thymeleaf;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.thymeleaf.context.Context;

public class PropertiesReadStrategy implements ContextCreationStrategy {

	
	private Locale locale = Locale.ENGLISH;
	
	private final Properties p = new Properties();
	public PropertiesReadStrategy(final InputStream input) throws IOException {
		p.load(input);
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	@Override
	public Context createContext() {
		
		final Map<String, Object> variables= new HashMap<>();
		for(Map.Entry<?, ?> entry : p.entrySet()) {
			variables.put(entry.getKey().toString(), entry.getValue());
		}
		
		final Context context = new Context(locale);
		context.setVariables(variables);
		return context;
	}
	
}
