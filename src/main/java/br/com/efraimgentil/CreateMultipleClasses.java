package br.com.efraimgentil;

import javax.inject.Inject;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.Projects;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.roaster.model.JavaType;

import br.com.efraimgentil.crud.RepositoryBuilder;

public class CreateMultipleClasses extends AbstractUICommand {

	@Inject
	ProjectFactory projectFactory;

	@Inject
	@WithAttributes(label = "Enitity Class", type = InputType.JAVA_CLASS_PICKER, description = "Class to create entity crud")
	private UIInput<String> targetPackage;
	
	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.forCommand(getClass()).name("Create CRUD").category( Categories.create("MY CRUD") );
	}
	
	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		
		
		builder.add(targetPackage);

	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		Project selectedProject = getSelectedProject(context.getUIContext() );
		String basePackage = getBasePackage(context.getUIContext());
		String value = targetPackage.getValue();
		
		JavaResource javaResource = getSelectedProject(context.getUIContext()).getFacet(JavaSourceFacet.class).getJavaResource(value);
		
		JavaType<?> javaType = javaResource.getJavaType();
		
		new RepositoryBuilder( javaResource , basePackage ).build( selectedProject );
		
		return Results.success( "" + javaType );
	}

	
	
	protected String getBasePackage(UIContext context){
		return getSelectedProject(context).getFacet(JavaSourceFacet.class).getBasePackage();
	}
	
	protected Project getSelectedProject(UIContext context) {
		Project selectedProject = Projects.getSelectedProject(projectFactory,
				context);
		return selectedProject;
	}

}
