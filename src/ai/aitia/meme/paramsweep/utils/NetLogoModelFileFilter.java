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
package ai.aitia.meme.paramsweep.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class NetLogoModelFileFilter extends FileFilter {

	//====================================================================================================
	// methods

	//----------------------------------------------------------------------------------------------------
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) return true;
		if (f.isFile() && (f.getName().toLowerCase().endsWith(".nlogo") || f.isFile() || f.getName().toLowerCase().endsWith(".nlogo3d"))) return true;
		return false;
	}

	//----------------------------------------------------------------------------------------------------
	@Override public String getDescription() { return "NetLogo model files (*.nlogo, *.nlogo3d)"; }
}
