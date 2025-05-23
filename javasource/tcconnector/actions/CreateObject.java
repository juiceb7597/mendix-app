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
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.thirdparty.org.json.JSONObject;
import com.mendix.webui.CustomJavaAction;
import tcconnector.foundation.BusinessObjectMappings;
import tcconnector.foundation.JPolicy;
import tcconnector.foundation.JServiceData;
import tcconnector.foundation.TcConnection;
import tcconnector.internal.foundation.Constants;
import tcconnector.proxies.ServiceResponse;
import com.mendix.systemwideinterfaces.core.UserAction;

/**
 * SOA URL:
 * Core-2015-07-DataManagement/createRelateAndSubmitObjects2
 * 
 * Description: 
 * This is a generic action for creating any Teamcenter business objects. This will also create any secondary (compounded) objects that need to be created, assuming the CompoundCreateInput for the secondary object is represented in the recursive CompoundCreateInput object e.g. Item is primary object that also creates Item Master and ItemRevision. ItemRevision in turn creates ItemRevision Master. The input for all these levels is passed in through the recursive CompoundCreateInput object.
 * 
 * Returns:
 * An entity of type ServiceResponse.
 * Created objects can be retrieved using TcConnector.ServiceResponse/TcConnector.ResponseData/TcConnector.Created association. Partial errors can be retrieved using association TcConnector.ResponseData/TcConnector.PartialErrors.
 */
public class CreateObject extends UserAction<IMendixObject>
{
	/** @deprecated use createInput.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __createInput;
	private final tcconnector.proxies.CreateInput createInput;
	private final java.lang.String businessObjectMapping;
	private final java.lang.String ConfigurationName;

	public CreateObject(
		IContext context,
		IMendixObject _createInput,
		java.lang.String _businessObjectMapping,
		java.lang.String _configurationName
	)
	{
		super(context);
		this.__createInput = _createInput;
		this.createInput = _createInput == null ? null : tcconnector.proxies.CreateInput.initialize(getContext(), _createInput);
		this.businessObjectMapping = _businessObjectMapping;
		this.ConfigurationName = _configurationName;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		// BEGIN USER CODE
		boolean before = true;

		try {
			// Prepare the request body
			tcconnector.internal.servicehelper.PrepareCreateObjectsReqBody createObjectsReqBody = new tcconnector.internal.servicehelper.PrepareCreateObjectsReqBody(
					getContext(), createInput.getMendixObject());
			String reqBody = createObjectsReqBody.get();
			JSONObject reqBodyJson = new JSONObject(reqBody);
			BusinessObjectMappings boMappings = new BusinessObjectMappings(businessObjectMapping, ConfigurationName);
			JPolicy policy = new JPolicy(boMappings);

			// Call the CreateObjects service
			JSONObject response = TcConnection.callTeamcenterService(getContext(), Constants.OPERATION_CREATEOBJECTS,
					reqBodyJson, policy, ConfigurationName);
			before = false;
			ServiceResponse responseObj = new ServiceResponse(getContext());

			JServiceData serviceData = (JServiceData) response.getJSONObject(RES_SERVICEDATA_KEY);
			responseObj
					.setResponseData(serviceData.instantiateServiceData(getContext(), boMappings, ConfigurationName, true));
			return responseObj.getMendixObject();
		} catch (Exception e) {
			String message = (before)
					? "Failed to marshall the the service operation " + Constants.OPERATION_CREATEOBJECTS
							+ " input argument."
					: "Failed to marshall the the service operation " + Constants.OPERATION_CREATEOBJECTS
							+ " response data.";
			Constants.LOGGER.error(message + e.getMessage());
			message += "Please contact your system administrator for further assistance.";
			throw e;
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
		return "CreateObject";
	}

	// BEGIN EXTRA CODE
	private static final String RES_SERVICEDATA_KEY = "ServiceData";
	// END EXTRA CODE
}
