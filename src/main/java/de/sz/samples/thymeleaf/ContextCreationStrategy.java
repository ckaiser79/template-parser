package de.sz.samples.thymeleaf;

import java.io.IOException;

import org.thymeleaf.context.Context;

public interface ContextCreationStrategy {
	
	public Context createContext() throws IOException;
	
}
