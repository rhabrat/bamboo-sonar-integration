/*
 * Licensed to Marvelution under one or more contributor license 
 * agreements.  See the NOTICE file distributed with this work 
 * for additional information regarding copyright ownership.
 * Marvelution licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.marvelution.bamboo.plugins.sonar.web.actions.admin;

import java.util.Collection;

import com.atlassian.bamboo.configuration.GlobalAdminAction;
import com.atlassian.sal.api.message.I18nResolver;
import com.marvelution.bamboo.plugins.sonar.web.utils.PluginHelper;
import com.marvelution.gadgets.sonar.utils.SonarGadgetsUtils;

/**
 * Administrator section page to view all the available Sonar Gadgets
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark rekveld</a>
 */
public class ViewSonarGadgets extends GlobalAdminAction {

	private static final long serialVersionUID = 1L;

	private SonarGadgetsUtils gadgetsUtils;
	private I18nResolver i18nResolver;

	/**
	 * Get the atlassian plugin key
	 * 
	 * @return the atlasian plugin key
	 */
	public String getPluginKey() {
		return PluginHelper.getPluginKey();
	}

	/**
	 * Get the Supported Gadget Ids
	 * 
	 * @return {@link Collection} of the supported Gadget Ids
	 */
	public Collection<String> getGadgetIds() {
		return getGadgetsUtils().getGadgetIds();
	}

	/**
	 * Getter for gadgetsUtils
	 * 
	 * @return the gadgetsUtils
	 */
	public SonarGadgetsUtils getGadgetsUtils() {
		return gadgetsUtils;
	}

	
	/**
	 * Setter for gadgetsUtils
	 * 
	 * @param gadgetsUtils the gadgetsUtils to set
	 */
	public void setGadgetsUtils(SonarGadgetsUtils gadgetsUtils) {
		this.gadgetsUtils = gadgetsUtils;
	}

	/**
	 * Getter for i18nResolver
	 * 
	 * @return the i18nResolver
	 */
	public I18nResolver getI18nResolver() {
		return i18nResolver;
	}

	
	/**
	 * Setter for i18nResolver
	 * 
	 * @param i18nResolver the i18nResolver to set
	 */
	public void setI18nResolver(I18nResolver i18nResolver) {
		this.i18nResolver = i18nResolver;
	}

}
