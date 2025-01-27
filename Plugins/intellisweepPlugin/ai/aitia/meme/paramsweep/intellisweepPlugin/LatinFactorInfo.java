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
package ai.aitia.meme.paramsweep.intellisweepPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ai.aitia.meme.paramsweep.gui.info.MemberInfo;
import ai.aitia.meme.paramsweep.gui.info.ParameterInfo;

/**
 * @author Ferschl
 *
 */
public class LatinFactorInfo extends ParameterInfo {
	
	public static final String LFI_ELEMENT = "LatinFactorInfo";
	public static final String PRIMARY = "primary";
	public static final String NUISANCE = "nuisance";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String DEF_VALUE = "defValue";
	public static final String LEVEL = "level";
	public static final String LEVEL_INDEX = "index";
	public static final String LEVEL_VALUE = "value";
	public static final String NUISANCE_INDEX = "nuisanceIndex";

    
	Vector<Object> levels = null;
    boolean selectedForInspection = false;
    private static final long serialVersionUID = 6405160480423275864L;
    private ILatinCubePluginInterface parent = null;
	
    public LatinFactorInfo(String name, String type, Class<?> javaType, ILatinCubePluginInterface parent) {
    	super(name, type, javaType);
    	this.parent = parent;
    	levels = new Vector<Object>();
    	if(parent != null){
    		for (int i = 0; i < parent.getMaxFactorLevelsCount(); i++) {
    			levels.add("");
    		}
    	}
    	selectedForInspection = false;
    }
    
    public void appendValues(List<Object> toAppend){
    	values.addAll(toAppend);
    }
    
    @Override
	public String toString(){
    	StringBuffer ret = new StringBuffer();
    	ret.append("[");
    	ret.append(this.name);
    	ret.append(" (");
    	ret.append(this.type);
    	ret.append(")");

    	if(selectedForInspection){
    		if(levels.size()>0){
    			ret.append(", levels: ");
    			for (int i = 0; i < parent.getFactorLevelsCount() - 1; i++) {
    				if(levels.get(i) != null){
    					ret.append(levels.get(i).toString());
    				} else{
    					ret.append("null");
    				}
    				ret.append(", ");
    			}
    			ret.append(levels.get(parent.getFactorLevelsCount() - 1).toString());
    		} else{
    			ret.append(", no levels defined yet");
    		}
    	}else{
    		ret.append(", default value: ");
    		if(this.getValue() != null)
    			ret.append(this.getValue().toString());
    		else
    			ret.append("null");
    	}
    	ret.append("]");
    	return ret.toString();
    }

    public boolean isSelectedForInspection() { return selectedForInspection; }
    public void setSelectedForInspection(boolean selectedForInspection) { this.selectedForInspection = selectedForInspection; }
	public void setLevels(Vector<Object> newLevels){ levels = newLevels; }
	public Vector<Object> getLevels() { return levels; }
	public Element getLatinFactorInfoElement(boolean primary, int nuisIdx, Document doc){
		Element lfiElem = doc.createElement(LFI_ELEMENT);
		lfiElem.setAttribute(PRIMARY, String.valueOf(primary));
		lfiElem.setAttribute(NUISANCE, String.valueOf(selectedForInspection));
		lfiElem.setAttribute(NUISANCE_INDEX, String.valueOf(nuisIdx));
		lfiElem.setAttribute(NAME, this.name);
		lfiElem.setAttribute(TYPE, this.type);
		lfiElem.setAttribute(DEF_VALUE, getValue() != null ? getValue().toString() : "null");
		
		int k = parent.getFactorLevelsCount();
		for (int i = 0; i < k; i++) {
	        Element levelElem = doc.createElement(LEVEL);
	        levelElem.setAttribute(LEVEL_INDEX, String.valueOf(i));
	        levelElem.setAttribute(LEVEL_VALUE, levels.get(i).toString());
	        lfiElem.appendChild(levelElem);
        }
		return lfiElem;
	}

	public void load(Element lfiElem) {
		setSelectedForInspection(Boolean.parseBoolean(lfiElem.getAttribute(NUISANCE)));
		setValue(getValue(lfiElem.getAttribute(DEF_VALUE),this.type));
		NodeList nl = lfiElem.getElementsByTagName(LEVEL);
		if (nl != null && nl.getLength() > 0){
			for (int i = 0; i < nl.getLength(); i++) {
				Element levelElem = (Element) nl.item(i);
				int lvlIdx = Integer.parseInt(levelElem.getAttribute(LEVEL_INDEX));
				String lvlValue = levelElem.getAttribute(LEVEL_VALUE);
				if(isValid(lvlValue, this.type)){
					while (lvlIdx >= levels.size()) levels.add(new String(""));
					levels.set(lvlIdx, getValue(lvlValue, this.type));
				}
            }
		}
    }
	
	public void fillInLatinSquareValues(boolean isPrimary, int nuisanceIndex){
		int nuisanceNumber = parent.getNuisanceNumber();
		int k = parent.getFactorLevelsCount();
		this.setDefinitionType(LIST_DEF);
		this.setRuns(1);
		values.clear();
		if(!isPrimary){
	        if(nuisanceIndex<2){
	        	for (int i = 0; i < k; i++) {
	                for (int j = 0; j < k; j++) {
	                    values.add(levels.get(nuisanceIndex == 0 ? i : j));
	                }
	            }
	        } else{
	        	switch (nuisanceIndex) {
                case 2:
	                if(nuisanceNumber == 3){
	                	switch (k) {
                        case 3:
    	                	setValues(createValuesFromIndexTextOffset1("1 2 3 2 3 1 3 1 2"));
	                        break;
                        case 4:
    	                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 2 1 4 3 3 4 1 2 4 3 2 1"));
	                        break;
                        case 5:
    	                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 5 2 3 4 5 1 3 4 5 1 2 4 5 1 2 3 5 1 2 3 4"));
	                        break;
                        default:
	                        break;
                        }
	                } else{
	                	switch (k) {
                        case 4:
    	                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 4 3 2 1 2 1 4 3 3 4 1 2"));
	                        break;
                        case 5:
    	                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 5 2 3 4 5 1 3 4 5 1 2 4 5 1 2 3 5 1 2 3 4"));
	                        break;
                        default:
	                        break;
	                	}
	                }
	                break;

                case 3:
                	switch (k) {
                    case 4:
	                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 2 1 4 3 3 4 1 2 4 3 2 1"));
                        break;
                    case 5:
	                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 5 3 4 5 1 2 5 1 2 3 4 2 3 4 5 1 4 5 1 2 3"));
                        break;
                    default:
                        break;
                	}
	                break;
                default:
	                break;
                }
	        }
        } else{
        	//this is the primary factor
        	switch (nuisanceNumber) {
            case 2:
            	switch (k) {
                case 3:
                	setValues(createValuesFromIndexTextOffset1("1 2 3 3 1 2 2 3 1"));
	                break;
                case 4:
                	setValues(createValuesFromIndexTextOffset1("1 2 4 3 4 3 1 2 2 4 3 1 3 1 2 4"));
	                break;
                case 5:
                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 5 3 4 5 1 2 5 1 2 3 4 2 3 4 5 1 4 5 1 2 3"));
	                break;
                default:
	                break;
                }
	            break;
            case 3:
            	switch (k) {
                case 3:
                	setValues(createValuesFromIndexTextOffset1("1 2 3 3 1 2 2 3 1"));
	                break;
                case 4:
                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 4 3 2 1 2 1 4 3 3 4 1 2"));
	                break;
                case 5:
                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 5 3 4 5 1 2 5 1 2 3 4 2 3 4 5 1 4 5 1 2 3"));
	                break;
                default:
	                break;
                }
	            break;
            case 4:
            	switch (k) {
                case 4:
                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 3 4 1 2 4 3 2 1 2 1 4 3"));
	                break;
                case 5:
                	setValues(createValuesFromIndexTextOffset1("1 2 3 4 5 4 5 1 2 3 2 3 4 5 1 5 1 2 3 4 3 4 5 1 2"));
	                break;
                default:
	                break;
                }
	            break;
            default:
	            break;
            }
        }
	}

	public List<Object> createValuesFromIndexTextOffset1(String text){
		//text looks like this: "1 2 3 4 4 3 2 1..."
		return createValuesFromIndexTextOffset(text, 1);
	}

	public List<Object> createValuesFromIndexTextOffset0(String text){
		//text looks like this: "0 1 2 3 4 3 2 1..."
		return createValuesFromIndexTextOffset(text, 0);
	}

	private List<Object> createValuesFromIndexTextOffset(String text, int offset){
		ArrayList<Object> ret = new ArrayList<Object>();
		String[] tokens = text.trim().split(" ");
		for (int i = 0; i < tokens.length; i++) {
	        ret.add(levels.get(Integer.parseInt(tokens[i])-offset));
        }
		return ret;
	}
	
	public static <T extends MemberInfo> int findInfo(List<T> infos, String name){
		int idx = -1;
		for (int i = 0; i < infos.size() && idx == -1; i++) {
	        if(infos.get(i).getName().equals(name)){
	        	idx = i;
	        }
        }
		return idx;
	}

	public static Number getNumberValue(String value, String type) {
		if ("byte".equals(type) || "Byte".equals(type))
			return getByteValue(value.trim());
		if ("short".equals(type) || "Short".equals(type))
			return getShortValue(value.trim());
		if ("int".equals(type) || "Integer".equals(type))
			return getIntegerValue(value.trim());
		if ("long".equals(type) || "Long".equals(type))
			return getLongValue(value.trim());
		if ("float".equals(type) || "Float".equals(type))
			return getFloatValue(value.trim());
		if ("double".equals(type) || "Double".equals(type))
			return getDoubleValue(value.trim());
		if ("boolean".equals(type) || "Boolean".equals(type))
			return value.trim().toLowerCase().charAt(0) == 't' ? new Double(1):new Double(0);
		if ("String".equals(type))
			return new Double(0);
		return null;
	}
	//-------------------------------------------------------------------------------
	/** Returns <code>value</code> as a <code>Byte</code> value. */
	private static Byte getByteValue(String value) {
		try {
			return new Byte(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	//-------------------------------------------------------------------------------
	/** Returns <code>value</code> as a <code>Short</code> value. */
	private static Short getShortValue(String value) {
		try {
			return new Short(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	//-------------------------------------------------------------------------------
	/** Returns <code>value</code> as an <code>Integer</code> value. */
	private static Integer getIntegerValue(String value) {
		try {
			return new Integer(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	//-------------------------------------------------------------------------------
	/** Returns <code>value</code> as a <code>Long</code> value. */
	private static Long getLongValue(String value) {
		try {
			return new Long(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	//-------------------------------------------------------------------------------
	/** Returns <code>value</code> as a <code>Float</code> value. */
	private static Float getFloatValue(String value) {
		try {
			return new Float(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	//-------------------------------------------------------------------------------
	/** Returns <code>value</code> as a <code>Double</code> value. */
	private static Double getDoubleValue(String value) {
		try {
			return new Double(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	

}
