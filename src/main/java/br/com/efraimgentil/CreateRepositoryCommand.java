package br.com.efraimgentil;

import javax.inject.Inject;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.parser.java.ui.AbstractJavaSourceCommand;
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
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

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
		JavaClassSource entityJavaType = entityJavaResource.getJavaType();
		String entityName = entityJavaType.getName();

		JavaInterfaceSource source = Roaster.create(JavaInterfaceSource.class);
		
		source.setPackage( javaSourceFacet.getBasePackage() + ".repositories" );
		source.setName(entityName + "Repository");

		source.addImport(Lazy.class);
		source.addAnnotation(Lazy.class);
		AnnotationSource<JavaInterfaceSource> annotation = source.getAnnotation( Lazy.class);
		annotation.setLiteralValue("true");
		
		source.addImport(Repository.class);
		source.addAnnotation(Repository.class);

		javaSourceFacet.saveJavaSource(source);

		return Results.success(entityName + "Repository created with success");
	}

	protected Project getSelectedProject(UIContext context) {
		return Projects.getSelectedProject(projectFactory, context);
	}

}
