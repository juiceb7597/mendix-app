// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package teamcentertoolkit.actions;

import static java.util.Objects.requireNonNull;
import static teamcentertoolkit.impl.Utils.requireNonNullString;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import teamcentertoolkit.impl.CopyHelper;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.UserAction;

/**
 * Copies attributes from ModelObject to ReviseInput, where attributes are defined on both objects with the same type. Attributes that are not defined on ModelObject and attributes that are defined on both, but with a different attribute type, will be skipped. The JA will instantiate an object of the entity type defined.
 */
public class JA_CopyModelObjectToNewReviseInput extends UserAction<IMendixObject>
{
	/** @deprecated use source.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __source;
	private final tcconnector.proxies.ModelObject source;
	private final java.lang.String targetEntity;

	public JA_CopyModelObjectToNewReviseInput(
		IContext context,
		IMendixObject _source,
		java.lang.String _targetEntity
	)
	{
		super(context);
		this.__source = _source;
		this.source = _source == null ? null : tcconnector.proxies.ModelObject.initialize(getContext(), _source);
		this.targetEntity = _targetEntity;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		// BEGIN USER CODE
		requireNonNull(source, "Source is required");
		requireNonNullString(targetEntity, "Target is required");


		return new CopyHelper(source.getMendixObject(), getContext()).copy(targetEntity);		
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_CopyModelObjectToNewReviseInput";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
