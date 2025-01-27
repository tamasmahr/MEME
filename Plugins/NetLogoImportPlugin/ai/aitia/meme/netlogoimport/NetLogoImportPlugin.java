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
package ai.aitia.meme.netlogoimport;

import java.awt.Window;
import java.io.File;

import ai.aitia.meme.pluginmanager.IImportPlugin;
import ai.aitia.meme.pluginmanager.IImportPluginContext;

public class NetLogoImportPlugin implements IImportPlugin {

	//====================================================================================================
	// implemented interfaces

	//----------------------------------------------------------------------------------------------------
	public String getLocalizedName() { return "NetLogo result file"; }
	
	//----------------------------------------------------------------------------------------------------
	public Window showDialog(IImportPluginContext ctx) {
		File f[] = null;
			
		String files = (String) ctx.get("FILES");
		if (files != null) {
			String[] fileArray = files.split(";");
			f = new File[fileArray.length];
			for (int i = 0;i < fileArray.length;++i)
				f[i] = new File(fileArray[i]);
		}
	    if (f == null){
	    	f = NetLogoImportDialog.openFileDialog(ctx.getAppWindow(),true);
	    	ctx.put("USER_SELECTED_FILES","x");
	    }
	    
	    if (f != null && f.length != 0) {
	    	if (f.length == 1) {
	    		NetLogoImportDialog d = new NetLogoImportDialog(ctx);
	    		d.start(f[0]);
	    	} else {
	    		NetLogoMultiImportDialog d = new NetLogoMultiImportDialog(ctx);
	    		d.start(f);
	    	}
	    }

		return null;
	}
}
