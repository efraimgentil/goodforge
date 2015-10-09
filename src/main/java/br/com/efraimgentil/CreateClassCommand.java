package br.com.efraimgentil;

import javax.inject.Inject;

import org.jboss.forge.addon.parser.java.ui.AbstractJavaSourceCommand;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class CreateClassCommand extends
		AbstractJavaSourceCommand<JavaClassSource> {
	
	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.forCommand(getClass()).name("Create Class").category( Categories.create("GoodForge") );
	}

	@Override
	protected Class<JavaClassSource> getSourceType() {
		return JavaClassSource.class;
	}
	
	@Override
	public JavaClassSource decorateSource(UIExecutionContext context,
			Project project, JavaClassSource source) throws Exception {
//		source.addImport("spring.com.teste.HadukenRyu");
//		source.setSuperType("TesteClr");
		
		return super.decorateSource(context, project, source);
	}
	
	@Override
	protected String getType() {
		return "Simple Class";
	}

}
