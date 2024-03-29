/*******************************************************************************
 * Copyright (c) 2010 Robert Munteanu
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.itsolut.mantis.ui.internal;

import org.eclipse.mylyn.tasks.core.AbstractRepositoryMigrator;
import org.eclipse.mylyn.tasks.core.TaskRepository;

import com.itsolut.mantis.core.MantisCorePlugin;
import com.itsolut.mantis.core.MantisRepositoryConfiguration;

public class MantisRepositoryMigrator extends AbstractRepositoryMigrator {

	@Override
	public String getConnectorKind() {
		
		return MantisCorePlugin.REPOSITORY_KIND;
	}
	
	@Override
	public boolean migrateRepository(TaskRepository repository) {
		
		return MantisRepositoryConfiguration.setCategoryIfNotSet(repository);
	}

}
