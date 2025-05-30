// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package tcconnector.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixIdentifier;
import com.mendix.webui.CustomJavaAction;
import com.teamcenter.fms.servercache.FSCException;
import com.teamcenter.fms.servercache.proxy.CommonsFSCWholeFileIOImpl;
import tcconnector.foundation.JModelObject;
import tcconnector.foundation.TcConnection;
import tcconnector.internal.foundation.Constants;
import tcconnector.internal.foundation.Messages;
import tcconnector.internal.foundation.ServiceMapper;
import tcconnector.proxies.ExpandPSOneLevel2Response;
import tcconnector.proxies.TeamcenterConfiguration;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.IMendixObjectMember;
import com.mendix.thirdparty.org.json.JSONArray;
import com.mendix.thirdparty.org.json.JSONObject;
import com.mendix.systemwideinterfaces.core.UserAction;

/**
 * SOA URL:
 * Cad-2008-06-StructureManagement/expandPSOneLevel
 * 
 * Description:
 * This actions finds the first level children of given parent bomlines.
 * InputBOMLine(ParentBOMLine) will be updated with Child Objects (BOMLine_childrenObj).
 * DownloadFiles flag if set true, will download the files attached to Datasets.
 * 
 * Returns:
 * InputBOMLine(ParentBOMLine) will be updated with Child Objects (BOMLine_childrenObj)
 * Ecah Child Object Consits of follwoing Object
 * ChildBOMLine(TcConnector.bomLine__Child)
 * ItemRevision of the ChildBOMLine (TcConnector.objectOfBOMLine)
 * NamedRefrenceList of attachedched Dataset can be retrived from ChildBOMLine (TcConnector.BOMLineAttachments)
 * Partial errors can be retrieved using association TcConnector.ResponseData/TcConnector.PartialErrors.
 */
public class ExpandPSOneLevel2 extends UserAction<IMendixObject>
{
	/** @deprecated use InputEntity.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __InputEntity;
	private final tcconnector.proxies.ExpandPSOneLevel2Input InputEntity;
	private final java.lang.Boolean DownloadFiles;
	private final java.lang.String BusinessObjectMappings;
	private final java.lang.String ConfigurationName;
	private final java.lang.Boolean populateServiceDataObjects;

	public ExpandPSOneLevel2(
		IContext context,
		IMendixObject _inputEntity,
		java.lang.Boolean _downloadFiles,
		java.lang.String _businessObjectMappings,
		java.lang.String _configurationName,
		java.lang.Boolean _populateServiceDataObjects
	)
	{
		super(context);
		this.__InputEntity = _inputEntity;
		this.InputEntity = _inputEntity == null ? null : tcconnector.proxies.ExpandPSOneLevel2Input.initialize(getContext(), _inputEntity);
		this.DownloadFiles = _downloadFiles;
		this.BusinessObjectMappings = _businessObjectMappings;
		this.ConfigurationName = _configurationName;
		this.populateServiceDataObjects = _populateServiceDataObjects;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		// BEGIN USER CODE
		ExpandPSOneLevel2Response response = new ExpandPSOneLevel2Response(getContext());
		String[] fmsURLs = retrieveFMSURLs();
		CommonsFSCWholeFileIOImpl fscFileIOImpl = initializeFMS(fmsURLs);
		try {
			ServiceMapper serviceMapper = new ServiceMapper(getContext(), Constants.OPERATION_EXPANDPSONELEVEL2,
					SERVICE_OPERATION_MAP, BusinessObjectMappings, ConfigurationName);
			Map<String, List<String>> parentChildUIDMap = new HashMap<>();
			List<IMendixObject> inputBOMLineList = null;
			if(this.InputEntity != null) {
				JSONObject jsonInputObj = serviceMapper.mapInputData(InputEntity.getMendixObject());
				JSONObject jsonPolicy = serviceMapper.getObjectPropertyPolicy();
				JSONObject jsonResponseObj = TcConnection.callTeamcenterService(getContext(),
						Constants.OPERATION_EXPANDPSONELEVEL2, jsonInputObj, jsonPolicy, ConfigurationName);
				Constants.LOGGER.debug( "ExpandPSOneLevel2_1: mapOutputData Start");
				response = (ExpandPSOneLevel2Response) serviceMapper.mapOutputData(jsonResponseObj, response, populateServiceDataObjects);
				Constants.LOGGER.debug( "ExpandPSOneLevel2_1: mapOutputData End");
	
				inputBOMLineList = Core.retrieveByPath(getContext(), InputEntity.getMendixObject(),
						"TcConnector.parentBomLines__");
	
				// Constants.LOGGER.trace( "ExpandPSOneLevel2_1: jsonResponseObj:" + jsonResponseObj );

				JSONArray outputArray = jsonResponseObj.getJSONArray("output");
				JSONArray childJSONArray;
				for (int cnt = 0; cnt < outputArray.length(); ++cnt) {
					JSONObject outputJSONObj = outputArray.getJSONObject(cnt);
					JSONObject parentJSONObj = outputJSONObj.getJSONObject("parent");
					JSONObject parentBLJSONObj = parentJSONObj.getJSONObject("bomLine");
					String parentUID = parentBLJSONObj.getString("uid");
					childJSONArray = outputJSONObj.getJSONArray("children");
					String childUID;
					List<String> childList = new ArrayList<>();
					for (int childCnt = 0; childCnt < childJSONArray.length(); ++childCnt) {
						JSONObject childJSONObj = childJSONArray.getJSONObject(childCnt);
						JSONObject childBLJSONObj = childJSONObj.getJSONObject("bomLine");
						childUID = childBLJSONObj.getString("uid");
						childList.add(childUID);
	
					}
					parentChildUIDMap.put(parentUID, childList);
				}
			}
			List<IMendixObject> parentChildWrapperList = Core.retrieveByPath(getContext(), response.getMendixObject(),
					"TcConnector.output__ExpandPSOneLevel2Response");
			for (int cnt = 0; cnt < parentChildWrapperList.size(); ++cnt) {
				//Constants.LOGGER.debug( "ParentChildWrapperList.size():" + parentChildWrapperList.size() );				

				List<IMendixObject> childrenObjList = Core.retrieveByPath(getContext(), parentChildWrapperList.get(cnt),
						"TcConnector.children__ParentChildWrapper");
				Constants.LOGGER.debug( "childrenObjList.size():" + childrenObjList.size() );

				for (Map.Entry<String, List<String>> entry : parentChildUIDMap.entrySet()) {
					List<String> childUIDList = entry.getValue();
					// Constants.LOGGER.trace( "childUIDList.size():" + childUIDList.size() );
					Boolean matchfound = true;
					if (childUIDList.size() == childrenObjList.size()) {
						// Constants.LOGGER.trace( "Size Equal" );						
						for (int childCnt = 0; childCnt < childrenObjList.size(); ++childCnt) {
							// Constants.LOGGER.trace( "childrenObjList.size() in Loop:" + childrenObjList.size() );
							List<IMendixObject> childBOMLineList = Core.retrieveByPath(getContext(),
									childrenObjList.get(childCnt), "TcConnector.bomLine__Child");
							IMendixObject childBOMLineObj = childBOMLineList.get(0);
							JModelObject childBOMLineModelObj = new JModelObject(getContext(), childBOMLineObj);
							String childObjUID = childBOMLineModelObj.getUID();
							if (!childUIDList.contains(childObjUID)) {
								//Constants.LOGGER.trace( "-------------------------------" );								
								//Constants.LOGGER.trace( "Match Not Found" );
								//Constants.LOGGER.trace( "-------------------------------" );
								matchfound = false;
								break;
							}
						}
						if (Boolean.TRUE.equals(matchfound)) {
							//Constants.LOGGER.trace( "Match Found True" );
							for (int bomLineCnt = 0; (inputBOMLineList != null) && bomLineCnt < inputBOMLineList.size(); ++bomLineCnt) {
								IMendixObject inputBOMLine = inputBOMLineList.get(bomLineCnt);
								JModelObject parentBOMLineModelObj = new JModelObject(getContext(), inputBOMLine);
								String parentBOMLineUID = parentBOMLineModelObj.getUID();
								String parentUIDfromMap = entry.getKey();
								IMendixObject childBOMLine = null;
								if (parentBOMLineUID.compareTo(parentUIDfromMap) == 0) {
									//Constants.LOGGER.trace( "Set ChildObjList to Parent BOMLIne" );
									List<IMendixObject> parentObjList = Core.retrieveByPath(getContext(),
											parentChildWrapperList.get(cnt), "TcConnector.parent__ParentChildWrapper");
									parentObjList.get(0).setValue(getContext(), "TcConnector.bomLine__Parent",
											inputBOMLine.getId());
									for (int childObjCnt = 0; childObjCnt < childrenObjList.size(); ++childObjCnt) {
										IMendixObject childObj = childrenObjList.get(childObjCnt);

										childObj.setValue(getContext(), "TcConnector.BOMLine_childrenObj",
												inputBOMLine.getId());
										List<IMendixObject> childBOMLineItemRevisionList = Core
												.retrieveByPath(getContext(), childObj, "TcConnector.objectOfBOMLine");
										if (!childBOMLineItemRevisionList.isEmpty()) {
											List<IMendixObject> childBOMLineList = Core.retrieveByPath(getContext(),
													childObj, "TcConnector.bomLine__Child");
											childBOMLine = childBOMLineList.get(0);
										}
										List<IMendixObject> childBOMLineRelatedObjectsList = Core
												.retrieveByPath(getContext(), childObj, "TcConnector.relatedObjects");
										for (int relatedObjCnt = 0; relatedObjCnt < childBOMLineRelatedObjectsList
												.size(); ++relatedObjCnt) {
											List<IMendixObject> datasetList = Core.retrieveByPath(getContext(),
													childBOMLineRelatedObjectsList.get(relatedObjCnt),
													"TcConnector.relatedObject");
											if (!datasetList.isEmpty()) {
												List<IMendixObject> namedRefList = Core.retrieveByPath(getContext(),
														childBOMLineRelatedObjectsList.get(relatedObjCnt),
														"TcConnector.namedRefList");
												List<IMendixIdentifier> fileDocumentList = new ArrayList<IMendixIdentifier>();

												for (int namedRefListCnt = 0; namedRefListCnt < namedRefList
														.size(); ++namedRefListCnt) {
													IMendixObject fileDocument = Core.instantiate(getContext(),
															tcconnector.proxies.FileDocument.entityName);
													IMendixObjectMember<?> fileTicket = namedRefList
															.get(namedRefListCnt).getMember(getContext(), "fileTicket");
													fileDocumentList.add(fileDocument.getId());
													if (DownloadFiles) {

														Thread thread = new Thread() {
															public void run() {
																try {
																	downloadFiles(fileDocument, fmsURLs, fscFileIOImpl,
																			fileTicket.getValue(getContext())
																					.toString());
																} catch (Exception e) {
																	e.printStackTrace();
																	Constants.LOGGER.error(
																			Messages.ExpandPSOneLevel2Message.ExpandPSOneLevel2Error
																					+ e.getMessage());
																}
															}
														};
														thread.start();
													}
												}
												childBOMLine.setValue(getContext(), "TcConnector.BOMLineAttachments",
														fileDocumentList);
											}
										}
									}
									break;
								}
							}
						}
					}
					continue;
				}
			}
		} catch (Exception e) {
			Constants.LOGGER.error(Messages.ExpandPSOneLevel2Message.ExpandPSOneLevel2Error + e.getMessage());
			throw e;
		}
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
		return "ExpandPSOneLevel2";
	}

	// BEGIN EXTRA CODE
	private static final String SERVICE_OPERATION_MAP = "OperationMapping/Cad/2008-06/StructureManagement/ExpandPSOneLevel.json";

	private int downloadFiles(IMendixObject fileDocumentEntity, String[] fmsURLs,
			CommonsFSCWholeFileIOImpl fscFileIOImpl, String fileTicket) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		fscFileIOImpl.download("TCM", fmsURLs, fileTicket, os);
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		Core.storeFileDocumentContent(getContext(), fileDocumentEntity, is);
		Constants.LOGGER.trace( "File Download Done" );
		Constants.LOGGER.debug("ExpandPSOneLevel2_1: File Downloaded");
		os.close();
		is.close();
		return 0;
	}

	private String[] retrieveFMSURLs() {
		TeamcenterConfiguration config = tcconnector.proxies.microflows.Microflows
				.retrieveTeamcenterConifgurationByName(getContext(), ConfigurationName);
		String fmsURL = config.getFMSURL(getContext());
		return fmsURL.split(",");
	}

	private CommonsFSCWholeFileIOImpl initializeFMS(String[] fmsURLs) throws UnknownHostException, FSCException {
		CommonsFSCWholeFileIOImpl fscFileIOImpl;
		fscFileIOImpl = new CommonsFSCWholeFileIOImpl();
		final InetAddress clientIP = InetAddress.getLocalHost();
		fscFileIOImpl.init(clientIP.getHostAddress(), fmsURLs, fmsURLs);
		return fscFileIOImpl;
	}

	// END EXTRA CODE
}
