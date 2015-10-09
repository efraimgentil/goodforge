package br.com.efraimgentil;

import javax.inject.Inject;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.Projects;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

public class PrintPackageCommand extends AbstractUICommand {
	
	@Inject
	private ProjectFactory projectFactory;

	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		//Define the command metadata, this will be used to display the command for the user
		return Metadata.forCommand(PrintPackageCommand.class)
				.name("Print Base Package")
				.category(Categories.create("GoodForge"));
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		/* Should initialize the UI if needed */
	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		Project project = getSelectedProject(context.getUIContext());
		String basePackage = project.getFacet(JavaSourceFacet.class).getBasePackage();
		return Results.success("This is my base package: " + basePackage);
	}

	protected Project getSelectedProject(UIContext context) {
		return Projects.getSelectedProject(projectFactory, context);
	}

}
