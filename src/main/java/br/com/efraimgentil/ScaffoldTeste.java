package br.com.efraimgentil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.javaee.servlet.ServletFacet;
import org.jboss.forge.addon.javaee.servlet.ServletFacet_3_0;
import org.jboss.forge.addon.javaee.servlet.ServletFacet_3_1;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.scaffold.spi.AccessStrategy;
import org.jboss.forge.addon.scaffold.spi.ScaffoldGenerationContext;
import org.jboss.forge.addon.scaffold.spi.ScaffoldProvider;
import org.jboss.forge.addon.scaffold.spi.ScaffoldSetupContext;
import org.jboss.forge.addon.scaffold.util.ScaffoldUtil;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.navigation.NavigationResultBuilder;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.shrinkwrap.descriptor.api.webapp30.WebAppDescriptor;

import br.com.efraimgentil.freemarker.FreemarkerTemplateProcessor;
import freemarker.template.Template;

public class ScaffoldTeste implements ScaffoldProvider {

	private static final String INDEX_HTML = "/index.html";

	Project project;
	WebResourcesFacet webFacet;

	@Inject
	private FacetFactory facetFactory;
	@Inject
	private CreateMultipleClasses command; 

	protected Template backingBeanTemplate;

	@Override
	public List<Resource<?>> generateFrom(ScaffoldGenerationContext generationContext) {
		  project = generationContext.getProject();
	      List<Resource<?>> generatedResources = new ArrayList<Resource<?>>();
	      Collection<?> resources = generationContext.getResources();
//		 for (Object resource : resources)
//	      {
//	         JavaSource<?> javaSource = null;
//	         if (resource instanceof JavaResource)
//	         {
//	            JavaResource javaResource = (JavaResource) resource;
//	            try
//	            {
//	               javaSource = javaResource.getJavaType();
//	            }
//	            catch (FileNotFoundException fileEx)
//	            {
//	               throw new IllegalStateException(fileEx);
//	            }
//	         }
//	         else
//	         {
//	            continue;
//	         }
//
	      	JavaClassSource entitySource =	(JavaClassSource) generationContext.getAttribute("entity");
//	         JavaClassSource entity = (JavaClassSource) javaSource;
	         String targetDir = generationContext.getTargetDirectory();
	         targetDir = (targetDir == null) ? "" : targetDir;
//	         getConfig().setProperty(FacesScaffoldProvider.class.getName() + "_targetDir", targetDir);
//	         Resource<?> template = (Resource<?>) generationContext.getAttribute("pageTemplate");
//	         List<Resource<?>> generatedResourcesForEntity = this.generateFromEntity(targetDir, template, entity);

	         WebResourcesFacet web = this.project.getFacet(WebResourcesFacet.class);
	         
	         loadTemplates();
	         
	         HashMap<Object, Object> hashMap = new HashMap<>();
	         hashMap.put("name", entitySource.getName() );
	         hashMap.put("age", "AAA" );
	         generatedResources.add( 
	        		  ScaffoldUtil.createOrOverwrite(
	                  web.getWebResource(targetDir  + "/search.xhtml"),
	                  FreemarkerTemplateProcessor.processTemplate( hashMap , this.backingBeanTemplate )));
	         
	         // TODO give plugins a chance to react to generated resources, use event bus?
	         // if (!generatedResources.isEmpty())
	         // {
	         // generatedEvent.fire(new ScaffoldGeneratedResources(provider, prepareResources(generatedResources)));
	         // }
//	         generatedResources.addAll(generatedResourcesForEntity);
//	      }
	      return generatedResources;
	}

	@Override
	public AccessStrategy getAccessStrategy() {
		return new FacesAccessStrategy(project);
	}

	@Override
	public NavigationResult getGenerationFlow(ScaffoldGenerationContext arg0) {
		NavigationResultBuilder builder = NavigationResultBuilder.create();
//		List<Class<? extends UICommand>> setupCommands = new ArrayList<>();
//		setupCommands.add(CreateMultipleClasses.class);
//		Metadata compositeSetupMetadata = Metadata
//				.forCommand(ScaffoldSetupWizard.class)
//				.name("Setup Facets")
//				.description(
//						"Setup all dependent facets for the Faces scaffold.");
//		builder.add(compositeSetupMetadata, setupCommands);
		builder.add( command ) ;
		return builder.build();
	}

	@Override
	public String getName() {
		return "Scaffold TESTE";
	}

	@Override
	public String getDescription() {
		return "Test to see how to scaffold";
	}

	@Override
	public NavigationResult getSetupFlow(ScaffoldSetupContext arg0) {
//		NavigationResultBuilder builder = NavigationResultBuilder.create();
//		List<Class<? extends UICommand>> setupCommands = new ArrayList<>();
//		setupCommands.add(CreateMultipleClasses.class);
//		Metadata compositeSetupMetadata = Metadata
//				.forCommand(ScaffoldSetupWizard.class)
//				.name("Setup Facets")
//				.description(
//						"Setup all dependent facets for the Faces scaffold.");
//		builder.add(compositeSetupMetadata, setupCommands);
//		builder.add( command ) ;
//		return builder.build();
		return null;
	}

	@Override
	public boolean isSetup(ScaffoldSetupContext arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<Resource<?>> setup(ScaffoldSetupContext setupContext) {
		project = setupContext.getProject();
		ProjectFacet projectFacet = project.getFacet(ProjectFacet.class);
		if (!project.hasFacet(WebResourcesFacet.class)) {
			facetFactory.install(project, WebResourcesFacet.class);
		}
		String targetDir = setupContext.getTargetDirectory();
	    Resource<?> template = null;
	    List<Resource<?>> resources = generateIndex(targetDir, template);
		return resources;
	}

	protected List<Resource<?>> generateIndex(String targetDir,
			final Resource<?> template) {
		List<Resource<?>> result = new ArrayList<Resource<?>>();
		WebResourcesFacet web = this.project.getFacet(WebResourcesFacet.class);

//		ServletFacet servlet = this.project.getFacet(ServletFacet.class);
//		if (servlet instanceof ServletFacet_3_0) {
//			WebAppDescriptor servletConfig = (WebAppDescriptor) servlet
//					.getConfig();
//			servletConfig.getOrCreateWelcomeFileList().welcomeFile(INDEX_HTML);
//		} else if (servlet instanceof ServletFacet_3_1) {
//			org.jboss.shrinkwrap.descriptor.api.webapp31.WebAppDescriptor servletConfig = (org.jboss.shrinkwrap.descriptor.api.webapp31.WebAppDescriptor) servlet
//					.getConfig();
//			servletConfig.getOrCreateWelcomeFileList().welcomeFile(INDEX_HTML);
//		}
//		loadTemplates();

		// generateTemplates(targetDir);
		// HashMap<Object, Object> context = getTemplateContext(targetDir,
		// template);
		// Basic pages

//		result.add(ScaffoldUtil.createOrOverwrite(
//				web.getWebResource(targetDir + INDEX_HTML),
//				this.backingBeanTemplate.toString()));

		return result;
	}

	protected void loadTemplates() {
		if (this.backingBeanTemplate == null) {
			this.backingBeanTemplate = FreemarkerTemplateProcessor
					.getTemplate("/template/te.ftl");
			String template = this.backingBeanTemplate.toString();
		}
	}

}
