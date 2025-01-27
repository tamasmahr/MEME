/*******************************************************************************
 * Copyright (C) 2006-2014 AITIA International, Inc.
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
package uchicago.src.reflector;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import uchicago.src.reflector.Invoker;
import uchicago.src.reflector.InvokerException;

public class FileArgInvoker extends Invoker {
	
	//====================================================================================================
	// members
		
	File value = null;
	
	//====================================================================================================
	// methods

	//----------------------------------------------------------------------------------------------------
	public FileArgInvoker(final Object o, final Method method, final String param) {
		super(o, method, param);
	}
	
	//----------------------------------------------------------------------------------------------------
	protected void check() throws InvokerException {
    	value = new File(param);
	}

	//----------------------------------------------------------------------------------------------------
	@Override
	protected void invoke() throws InvocationTargetException, IllegalAccessException {
		method.invoke(object,value);
	}
}