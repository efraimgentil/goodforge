package br.com.efraimgentil;

import javax.inject.Inject;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.Projects;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;
import org.springframework.util.StringUtils;

import br.com.efraimgentil.builder.view.SimplePageBuilder;

public class CreateSimplePageCommand extends AbstractUICommand{
	
	@Inject
	ProjectFactory projectFactory;
	
	@Inject
	@WithAttributes(label = "File name", type = InputType.TEXTBOX, description = "Target package")
	private UIInput<String> fileNameInput;
	
	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.forCommand(getClass())
				.name("Create Simple Page")
				.description("Will be create a file in the WEB_ROOT_DIR")
				.category(Categories.create("GoodForge"));
	}
	
	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		builder.add(fileNameInput);
	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		Project project = getSelectedProject(context.getUIContext());
		
		WebResourcesFacet facet = project.getFacet(WebResourcesFacet.class );
		DirectoryResource webRootDirectory = facet.getWebRootDirectory();
		
		new SimplePageBuilder(project).build( fileNameInput.getValue() );
		
		
		return Results.success( webRootDirectory.getFullyQualifiedName() );
	}
	
	@Override
	public void validate(UIValidationContext validator) {
		Project project = getSelectedProject(validator.getUIContext());
		if(!project.hasFacet(WebResourcesFacet.class ))
			validator.addValidationError(fileNameInput, "Project has no WebResourceFacet ( Probably selected project is not a web project ) ");
		
		String value = fileNameInput.getValue();
		if(!StringUtils.hasText(value))
			validator.addValidationError(fileNameInput, "File name is required");
		else{
			String[] split = value.split("\\.");
			if(split.length == 1)
				validator.addValidationWarning(fileNameInput, "If the file name has no extesion '.jsp' will be used");
		}
		super.validate(validator);
	}
	
	protected Project getSelectedProject(UIContext context){
		return Projects.getSelectedProject(projectFactory, context);
	}
	
}
