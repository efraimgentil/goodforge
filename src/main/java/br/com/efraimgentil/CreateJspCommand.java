package br.com.efraimgentil;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.Projects;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.scaffold.util.ScaffoldUtil;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.facets.TemplateFacet;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class CreateJspCommand extends AbstractUICommand {

	Project project;

	@Inject
	private FacetFactory facetFactory;

	@Inject
	ProjectFactory projectFactory;

	@Inject
	TemplateFactory templateFactory;
	@Inject
	ResourceFactory resourceFactory;

	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.forCommand(getClass()).name("Create JSP")
				.category(Categories.create("MY CRUD"));
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		project = getSelectedProject(builder.getUIContext());
		if (!project.hasFacet(TemplateFacet.class)) {
			facetFactory.install(project, TemplateFacet.class);
		}

//		TemplateFacet templates = project.getFacet(TemplateFacet.class);
//		// Obtain a reference to the scaffold directory in the classpath
//		URL resource = getClass().getClassLoader().getResource("goodforge");
//		if (resource != null && resource.getProtocol().equals("jar")) {
//			try {
//				// Obtain a reference to the JAR containing the scaffold
//				// directory
//				JarURLConnection connection = (JarURLConnection) resource
//						.openConnection();
//				JarFile jarFile = connection.getJarFile();
//				Enumeration<JarEntry> entries = jarFile.entries();
//				// Iterate through the JAR entries and copy files to the
//				// template directory. Only files ending with .ftl,
//				// and
//				// present in the scaffold/ directory are copied.
//				while (entries.hasMoreElements()) {
//					JarEntry jarEntry = entries.nextElement();
//					String entryName = jarEntry.getName();
//					if ( entryName.endsWith(".ftl") ) {
//						String relativeFilename = entryName
//								.substring("scaffold/".length());
//						InputStream is = jarFile.getInputStream(jarEntry);
//						// Copy the file into a sub-directory under
//						// src/main/templates named after the scaffold provider.
//						Resource<File> templateResource = resourceFactory
//								.create(new File(relativeFilename));
//						FileResource<?> fileResource = templateResource
//								.reify(FileResource.class);
//						fileResource.setContents(is);
//					}
//				}
//			} catch (IOException ioEx) {
//				throw new RuntimeException(ioEx);
//			}
//		}
	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		Project project = getSelectedProject(context.getUIContext());
		WebResourcesFacet webFacet = project.getFacet(WebResourcesFacet.class);

		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("name", "Test HEHE");
		dataModel.put("age", 99);

//		TemplateFacet facet = project.getFacet(TemplateFacet.class);
//		FileResource<?> resource = facet.getResource("/template/template.js.ftl");
		// Resource<?> resource =
		// resourceFactory.create(getClass().getResource("/template/template.js.ftl"));
		
		Configuration freemarkerConfig = new freemarker.template.Configuration();
	    freemarkerConfig.setClassForTemplateLoading( this.getClass() ,  "/template/" );
	    freemarkerConfig.setObjectWrapper( new DefaultObjectWrapper() );
	    
	    freemarker.template.Template template = freemarkerConfig.getTemplate("template.ftl");
	    try( StringWriter writer = new StringWriter() ){
	    	template.process(dataModel, writer);
	    	Resource<?> createOrOverwrite = ScaffoldUtil.createOrOverwrite(
					webFacet.getWebResource("/js/teste.js"), writer.toString() );
	    }
	    
	    
//		Template template = templateFactory.create(resource, FreemarkerTemplate.class);
//		String content = template.process(dataModel);

		
		return Results.success(" This is my base package ");
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

}