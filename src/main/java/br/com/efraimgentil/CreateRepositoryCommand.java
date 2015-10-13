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

import br.com.efraimgentil.builder.JPARepositoryBuilder;

public class CreateRepositoryCommand extends AbstractUICommand {

	@Inject
	ProjectFactory projectFactory;

	@Inject
	@WithAttributes(label = "Enitity Class", type = InputType.JAVA_CLASS_PICKER, description = "Entity to create a repository to crud")
	private UIInput<String> entityPicker;

	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.forCommand(getClass())
				.name("Create Repository for Entity")
				.category(Categories.create("GoodForge"));
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		builder.add(entityPicker);
	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		Project project = getSelectedProject(context.getUIContext());
		JavaSourceFacet javaSourceFacet = project
				.getFacet(JavaSourceFacet.class);
		
		String value = entityPicker.getValue();
		JavaResource entityJavaResource = javaSourceFacet .getJavaResource(entityPicker.getValue());
		JavaResource jpaRepository = new JPARepositoryBuilder(project ).buildFor( entityJavaResource );
		
		return Results.success( jpaRepository.getName() + " created with success");
	}
	

	protected Project getSelectedProject(UIContext context) {
		return Projects.getSelectedProject(projectFactory, context);
	}

}
