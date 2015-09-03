package br.com.efraimgentil;

import javax.inject.Inject;

import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;

public class CreateMultipleClasses extends AbstractUICommand {
	
	@Inject
	ProjectFactory projectFactory;
	
	@Inject
	@WithAttributes(label = "Enitity Class", type = InputType.JAVA_CLASS_PICKER, description = "Class to create entity crud")
	private UIInput<String> targetPackage;

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
