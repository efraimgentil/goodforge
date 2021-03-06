package br.com.efraimgentil;

import javax.inject.Inject;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.Projects;
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

import br.com.efraimgentil.builder.SimpleServiceBuilder;

public class CreateSimpleServiceCommand extends AbstractUICommand {

	@Inject
	ProjectFactory projectFactory;
	
	@Inject
	@WithAttributes(label = "Package", type = InputType.JAVA_PACKAGE_PICKER, description = "Target package")
	private UIInput<String> packageInput;
	
	@Inject
	@WithAttributes(label = "Service Name", type = InputType.TEXTBOX , description = "Service name")
	private UIInput<String> serviceNameInput;
	

	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.forCommand(getClass())
				.name("Create Simple Service")
				.description("Create a simple service with spring")
				.category(Categories.create("GoodForge"));
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		builder.add(packageInput);
		builder.add(serviceNameInput);
	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		Project project = getSelectedProject(context.getUIContext());
		
		new SimpleServiceBuilder(project).buildFor( packageInput.getValue() ,  serviceNameInput.getValue() );
		
		return Results.success( " created with success");
	}
	
	@Override
	public void validate(UIValidationContext validator) {
		super.validate(validator);
		String serviceName = serviceNameInput.getValue();
		
		if(serviceName == null || "".equals( serviceName.trim() ) )
			validator.addValidationError( serviceNameInput ,  serviceNameInput.getLabel() + " is required");
		
		String targetPackage = packageInput.getValue();
		if(targetPackage == null || "".equals( targetPackage.trim() ) )
			validator.addValidationError( packageInput ,  packageInput.getLabel() + " is required");
		
	}
	
	protected Project getSelectedProject(UIContext context) {
		return Projects.getSelectedProject(projectFactory, context);
	}

}
