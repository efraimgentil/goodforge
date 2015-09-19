package br.com.efraimgentil;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.Id;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.Projects;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.scaffold.spi.ResourceCollection;
import org.jboss.forge.addon.scaffold.spi.ScaffoldGenerationContext;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.command.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UINavigationContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class CreateMultipleClasses extends AbstractUICommand implements
		UICommand, UIWizardStep {

	@Inject
	ProjectFactory projectFactory;

	@Inject
	@WithAttributes(label = "Enitity Class", type = InputType.JAVA_CLASS_PICKER, description = "Class to create entity crud")
	private UIInput<String> targetPackage;

	private JavaClassSource entitySource;

	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.forCommand(getClass()).name("Create CRUD")
				.category(Categories.create("MY CRUD"));
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {

		builder.add(targetPackage);

	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		Project selectedProject = getSelectedProject(context.getUIContext());
		String basePackage = getBasePackage(context.getUIContext());
		String value = targetPackage.getValue();

		JavaResource javaResource = getSelectedProject(context.getUIContext())
				.getFacet(JavaSourceFacet.class).getJavaResource(value);
		JavaClassSource javaType = javaResource.getJavaType();
		entitySource = javaType;
		Type<JavaClassSource> idType = null;
		List<FieldSource<JavaClassSource>> fields = javaType.getFields();
		for (FieldSource<JavaClassSource> fieldSource : fields) {
			if (fieldSource.hasAnnotation(Id.class))
				;
			idType = fieldSource.getType();
		}
		
		UIContext uiContext = context.getUIContext();
		Map<Object, Object> attributeMap = uiContext.getAttributeMap();
		ScaffoldGenerationContext genCtx = (ScaffoldGenerationContext) attributeMap.get(ScaffoldGenerationContext.class);
		genCtx.addAttribute("entity",  entitySource );
		
		//
		// JavaResource repository = new RepositoryBuilder( javaResource ,
		// basePackage ).build( selectedProject );
		// JavaResource specs = new SpecsBuilder( javaResource , basePackage
		// ).build( selectedProject );
		// JavaResource service = new ServiceBuilder(javaResource, basePackage,
		// repository , specs).build(selectedProject);
		//
		return Results.success("Field: " + idType.getQualifiedName());
	}

	protected String getBasePackage(UIContext context) {
		return getSelectedProject(context).getFacet(JavaSourceFacet.class)
				.getBasePackage();
	}

	protected Project getSelectedProject(UIContext context) {
		Project selectedProject = Projects.getSelectedProject(projectFactory,
				context);
		return selectedProject;
	}

	@Override
	public NavigationResult next(UINavigationContext context) throws Exception {
		UIContext uiContext = context.getUIContext();
		Map<Object, Object> attributeMap = uiContext.getAttributeMap();
		ResourceCollection resourceCollection = new ResourceCollection();
		Project project = getSelectedProject(uiContext);
		JavaSourceFacet javaSource = project.getFacet(JavaSourceFacet.class);
		Resource<?> resource = javaSource.getJavaResource(entitySource);
		resourceCollection.addToCollection(resource);
		attributeMap.put(ResourceCollection.class, resourceCollection);
		ScaffoldGenerationContext genCtx = (ScaffoldGenerationContext) attributeMap.get(ScaffoldGenerationContext.class);
		genCtx.addAttribute("entity",  entitySource );
		return null;
	}

}
