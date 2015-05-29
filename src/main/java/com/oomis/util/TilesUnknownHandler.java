package com.oomis.util;

import javax.servlet.ServletContext;

import org.apache.struts2.convention.ConventionUnknownHandler;
import org.apache.struts2.views.tiles.TilesResult;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

public class TilesUnknownHandler extends ConventionUnknownHandler {
	
	@Inject
	public TilesUnknownHandler(
			Configuration configuration,
			ObjectFactory objectFactory,
			ServletContext servletContext,
			Container container,
			@Inject("struts.convention.default.parent.package") String defaultParentPackageName,
			@Inject("struts.convention.redirect.to.slash") String redirectToSlash,
			@Inject("struts.convention.action.name.separator") String nameSeparator) {
		super(configuration, objectFactory, servletContext, container,
				defaultParentPackageName, redirectToSlash, nameSeparator);
		System.out.println("TilesHandler");
	}

	@Override
	public Result handleUnknownResult(ActionContext actionContext,
			String actionName, ActionConfig actionConfig, String resultCode)
			throws XWorkException {

		StringBuilder location = new StringBuilder();
		String packageName = removeActionName(getPackageName(actionConfig.getClassName()));
		location.append(packageName);

		if (location.length() > 0)
			location.append(".");

		location.append(actionConfig.getName());

		if (!resultCode.equals("success")) {
			location.append(".").append(resultCode);
		}

		TilesResult result = new TilesResult(location.toString());
		System.out.println(location.toString());
		return result;
	}

	private String removeActionName(String packageName) {
		if(packageName==null||packageName.length()==0){
			return null;
		}
		int actionNameIndex = packageName.lastIndexOf("Action");
		if(actionNameIndex == -1){
			return packageName;
		}
		int packageIndex = packageName.lastIndexOf(".", actionNameIndex);
		if(packageIndex == -1){
			return "";
		}
		return packageName.substring(0, packageIndex);
	}

	private String getPackageName(String className) {
		int basePackageIndex = className.indexOf("actions.");
		if (basePackageIndex == -1)
			basePackageIndex = className.indexOf("action.");

		if (basePackageIndex != -1) {
			return className.substring(findPackageEndIndex(className,
					basePackageIndex) + 1);
		}
		return null;
	}

	private int findPackageEndIndex(String className, int packageIndex) {
		return className.indexOf(".", packageIndex);
	}
}