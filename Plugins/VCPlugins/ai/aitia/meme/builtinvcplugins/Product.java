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
package ai.aitia.meme.builtinvcplugins;

import java.util.Collection;

import ai.aitia.meme.database.ColumnType;
import ai.aitia.meme.pluginmanager.Parameter;

public class Product extends AbstractBuiltinFn {
	
	//====================================================================================================
	// implemented interfaces

	//-------------------------------------------------------------------------
	public String getLocalizedName() { return "PRODUCT()"; }

	//-------------------------------------------------------------------------
	public ColumnType getReturnType(final IContext context) {
		Collection<? extends Parameter> pars = context.getArgumentPars();
		if (pars.isEmpty())	return null;
		for (final Parameter p : pars) {
			final Class<?> c = p.getDatatype().getJavaClass();
			if (c == Boolean.class || c == String.class) return null;
		}
		return ColumnType.DOUBLE;
	}

	//-------------------------------------------------------------------------
	public Object compute(final IContext context) {
		double prod = 1;
		for (final Object[] v : context.getArguments()) {
			for (final Object o : v) {
				if (o == null) continue;	// ignore null values
				final ColumnType.TypeAndValue tv = ColumnType.parseObject(o);
				prod *= ((Number)tv.getValue()).doubleValue();
			}
		}
		return prod;
	}
}
