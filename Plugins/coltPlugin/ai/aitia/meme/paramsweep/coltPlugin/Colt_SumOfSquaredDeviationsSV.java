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
package ai.aitia.meme.paramsweep.coltPlugin;

import java.util.Arrays;
import java.util.List;

import ai.aitia.meme.paramsweep.plugin.IStatisticsPlugin;

public class Colt_SumOfSquaredDeviationsSV implements IStatisticsPlugin {

	//=======================================================================================
	// methods
	
	//---------------------------------------------------------------------------------------
	public String getName() { return "sumOfSquaredDeviations"; }
	public String getLocalizedName() { return "Sum of squared mean deviations from size and variance"; }
	public String getFullyQualifiedName() { return "cern.jet.stat.Descriptive.sumOfSquaredDeviations"; }
	public int getNumberOfParameters() { return 2; }
	public List<String> getParameterNames() { return Arrays.asList(new String[] { "size", "variance" }); }
	public List<Class> getParameterTypes() { return Arrays.asList(new Class[] { Integer.TYPE, Double.TYPE }); }
	public Class getReturnType() { return Double.TYPE; }
	public List<String> getParameterDescriptions() { return Arrays.asList(new String[] { "Size of the data sequence", "Variance of the data sequence" }); }
	public String getDescription() { 
		return "Computes the sum of squared mean deviations of a data sequence. That is variance * (size-1) == Sum( (data[i] - mean)<sup>2</sup> ).";
	}
}
