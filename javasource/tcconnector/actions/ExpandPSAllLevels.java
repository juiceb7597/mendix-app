// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package tcconnector.actions;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import tcconnector.foundation.TcConnection;
import tcconnector.internal.foundation.Constants;
import tcconnector.proxies.ExpandPSAllLevelsResponse;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.UserAction;

/**
 * SOA URL:
 * Cad-2007-01-StructureManagement/expandPSAllLevels
 * 
 * Description:
 * This actions finds the chilren at all levels given parent bomline. 
 * 
 * Returns:
 * An entity of type ExpandPSAllLevelsResponse. Expanded child bomlines with respective parents can be retrieved using association TcConnector.ExpandPSAllLevelsOutput. Partial errors can be retrieved using association TcConnector.ResponseData/TcConnector.PartialErrors.
 */
public class ExpandPSAllLevels extends UserAction<IMendixObject>
{
	/** @deprecated use InputEntity.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __InputEntity;
	private final tcconnector.proxies.ExpandPSOneLevelInput InputEntity;
	private final java.lang.String BusinessObjectMappings;
	private final java.lang.String ConfigurationName;
	private final java.lang.Boolean populateServiceDataObjects;

	public ExpandPSAllLevels(
		IContext context,
		IMendixObject _inputEntity,
		java.lang.String _businessObjectMappings,
		java.lang.String _configurationName,
		java.lang.Boolean _populateServiceDataObjects
	)
	{
		super(context);
		this.__InputEntity = _inputEntity;
		this.InputEntity = _inputEntity == null ? null : tcconnector.proxies.ExpandPSOneLevelInput.initialize(getContext(), _inputEntity);
		this.BusinessObjectMappings = _businessObjectMappings;
		this.ConfigurationName = _configurationName;
		this.populateServiceDataObjects = _populateServiceDataObjects;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		// BEGIN USER CODE
		ExpandPSAllLevelsResponse response = new ExpandPSAllLevelsResponse(getContext());
		response = (ExpandPSAllLevelsResponse) TcConnection.callTeamcenterService(getContext(),
				Constants.OPERATION_EXPANDPSALLLEVELS, InputEntity.getMendixObject(), response, SERVICE_OPERATION_MAP,
				BusinessObjectMappings, ConfigurationName, populateServiceDataObjects);
		return response.getMendixObject();
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "ExpandPSAllLevels";
	}

	// BEGIN EXTRA CODE
	private static final String SERVICE_OPERATION_MAP = "OperationMapping/Cad/2007-01/StructureManagement/expandPSAllLevels.json";
	// END EXTRA CODE
}
