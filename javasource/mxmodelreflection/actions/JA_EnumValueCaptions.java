// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package mxmodelreflection.actions;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import mxmodelreflection.proxies.MxObjectEnumCaptions;
import java.util.stream.Collectors;
import com.mendix.systemwideinterfaces.core.UserAction;

public class JA_EnumValueCaptions extends UserAction<java.lang.String>
{
	/** @deprecated use objectEnumValue.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __objectEnumValue;
	private final mxmodelreflection.proxies.MxObjectEnumValue objectEnumValue;

	public JA_EnumValueCaptions(
		IContext context,
		IMendixObject _objectEnumValue
	)
	{
		super(context);
		this.__objectEnumValue = _objectEnumValue;
		this.objectEnumValue = _objectEnumValue == null ? null : mxmodelreflection.proxies.MxObjectEnumValue.initialize(getContext(), _objectEnumValue);
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		// BEGIN USER CODE
		if (objectEnumValue == null) {
			return null;
		} else {
			return objectEnumValue.getCaptions().stream().map(MxObjectEnumCaptions::getCaption).collect(Collectors.joining(", "));
		}
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_EnumValueCaptions";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
