package br.com.efraimgentil;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Id;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.Projects;
import org.jboss.forge.addon.resource.Resource;
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
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.com.efraimgentil.builder.RepositoryBuilder;
import br.com.efraimgentil.builder.ServiceBuilder;
import br.com.efraimgentil.builder.SpecsBuilder;

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
		JavaClassSource javaType = javaResource.getJavaType();
		Type<JavaClassSource> idType = null ;
		List<FieldSource<JavaClassSource>> fields = javaType.getFields();
		for (FieldSource<JavaClassSource> fieldSource : fields) {
			if( fieldSource.hasAnnotation(Id.class) ) ;
				idType = fieldSource.getType();
		}
		 
		JavaResource repository = new RepositoryBuilder( javaResource , basePackage ).build( selectedProject );
		JavaResource specs = new SpecsBuilder( javaResource , basePackage ).build( selectedProject );
		JavaResource service = new ServiceBuilder(javaResource, basePackage, repository , specs).build(selectedProject);
		
		return Results.success( "Field: " + idType.getQualifiedName());
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
