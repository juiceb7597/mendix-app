// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package tcconnector.actions;

import java.util.List;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.thirdparty.org.json.JSONArray;
import com.mendix.thirdparty.org.json.JSONObject;
import com.mendix.webui.CustomJavaAction;
import tcconnector.foundation.BusinessObjectMappings;
import tcconnector.foundation.JModelObject;
import tcconnector.foundation.TcConnection;
import tcconnector.internal.foundation.Constants;
import tcconnector.internal.foundation.Messages;
import tcconnector.internal.foundation.ServiceMapper;
import tcconnector.proxies.ItemRevision;
import tcconnector.proxies.WhereUsedResponseInfo;
import com.mendix.systemwideinterfaces.core.UserAction;

/**
 * SOA URL:
 * Core-2007-01-DataManagement/whereUsed
 * 
 * Description:
 * This action returns all the parent Item and ItemRevision objects in the structure where the input Item or ItemRevision is used
 * 
 * Returns:
 * Input Object will be updated with WhereUsedResponseInfo objects which consits of level and parentItemRev details.
 * WhereUsedResponseInfo can be retrived using TcConnector.WhereUsedResponseInfo_ModelObject association
 */
public class WhereUsed extends UserAction<java.lang.Boolean>
{
	/** @deprecated use InputEntity.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __InputEntity;
	private final tcconnector.proxies.WhereUsedInput InputEntity;
	private final java.lang.String BusinessObjectMappings;
	private final java.lang.String ConfigurationName;

	public WhereUsed(
		IContext context,
		IMendixObject _inputEntity,
		java.lang.String _businessObjectMappings,
		java.lang.String _configurationName
	)
	{
		super(context);
		this.__InputEntity = _inputEntity;
		this.InputEntity = _inputEntity == null ? null : tcconnector.proxies.WhereUsedInput.initialize(getContext(), _inputEntity);
		this.BusinessObjectMappings = _businessObjectMappings;
		this.ConfigurationName = _configurationName;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE

		boolean isWhereUsedSuccess = true;
		try {
			BusinessObjectMappings boMappings = new BusinessObjectMappings(BusinessObjectMappings, ConfigurationName);
			ServiceMapper serviceMapper = new ServiceMapper(getContext(), Constants.OPERATION_WHERE_USED,
					SERVICE_OPERATION_MAP, BusinessObjectMappings, ConfigurationName);
			JSONObject jsonInputObj = serviceMapper.mapInputData(InputEntity.getMendixObject());
			JSONObject jsonPolicy = serviceMapper.getObjectPropertyPolicy();
			JSONObject jsonResponseObj = TcConnection.callTeamcenterService(getContext(),
					Constants.OPERATION_WHERE_USED, jsonInputObj, jsonPolicy, ConfigurationName);
			System.out.println(jsonResponseObj);

			List<IMendixObject> inputModelObjectList = Core.retrieveByPath(getContext(), InputEntity.getMendixObject(),
					"TcConnector.objects");

			JSONArray outputArray = jsonResponseObj.getJSONArray("output");
			for (int cnt = 0; cnt < outputArray.length(); ++cnt) {
				JSONObject outputJSONObj = outputArray.getJSONObject(cnt);
				JSONArray infoJSONArray = outputJSONObj.getJSONArray("info");

				IMendixObject modelObj = inputModelObjectList.get(cnt);
				for (int infoCnt = 0; infoCnt < infoJSONArray.length(); ++infoCnt) {
					WhereUsedResponseInfo whereUsedResponseInfo = new WhereUsedResponseInfo(getContext());
					JSONObject infoJSONObj = infoJSONArray.optJSONObject(infoCnt);
					JSONObject parentItemRevJSONObj = infoJSONObj.getJSONObject("parentItemRev");
					int level = infoJSONObj.getInt("level");
					ItemRevision parentItemRevMxObj = new ItemRevision(getContext());
					JModelObject parentItemRevjModelObj = new JModelObject(parentItemRevJSONObj);
					parentItemRevjModelObj.initializeEntity(getContext(), parentItemRevMxObj.getMendixObject(),
							boMappings, ConfigurationName);
					whereUsedResponseInfo.setparentItemRev(getContext(), parentItemRevMxObj);
					whereUsedResponseInfo.setlevel(getContext(), level);
					whereUsedResponseInfo.getMendixObject().setValue(getContext(),
							"TcConnector.WhereUsedResponseInfo_ModelObject", modelObj.getId());
				}
			}
		} catch (Exception e) {
			Constants.LOGGER.error(Messages.WhereUsedMessage.WhereUsedMessageError + e.getMessage());
			isWhereUsedSuccess = false;
			throw e;
		}
		return isWhereUsedSuccess;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "WhereUsed";
	}

	// BEGIN EXTRA CODE
	private static final String SERVICE_OPERATION_MAP = "OperationMapping/Core/2007-01/DataManagement/whereUsed.json";
	// END EXTRA CODE
}
