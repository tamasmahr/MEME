/*******************************************************************************
 * Copyright (C) 2006-2013 AITIA International, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package ai.aitia.meme.paramsweep.intellisweepPlugin.utils;

import javax.swing.tree.DefaultMutableTreeNode;

import ai.aitia.meme.paramsweep.plugin.IntelliSweepPluginDescriptor;

/**
 * An abstract class that categorizes the plugins.
 * 
 * 
 */
public abstract class MethodCategorization {
	//=========================================================================
	//members
	protected DefaultMutableTreeNode root = new DefaultMutableTreeNode("Methods");

	//=========================================================================
	//public methods
	public DefaultMutableTreeNode getCategorizationRoot(){
		return root;
	}
	
	public abstract void putMethodToCategorization( IntelliSweepPluginDescriptor desc );
}
