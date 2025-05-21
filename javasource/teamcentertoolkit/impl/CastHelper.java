package teamcentertoolkit.impl;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;

import tcconnector.proxies.ModelObject;

import java.util.ArrayList;
import java.util.List;

public class CastHelper {
	private final IMetaObject OutputMetaObject;
	private final boolean raiseError;
	
	public CastHelper(String modelObjectType, boolean raisesError) {
		this.OutputMetaObject = getOutputModelObject(modelObjectType);
		this.raiseError = raisesError;
	}
	
	private IMetaObject getOutputModelObject(String modelObjectOutputType) {
		try {
			IMetaObject outputMetaObject = Core.getMetaObject(modelObjectOutputType);
			
			if (!outputMetaObject.isSubClassOf(tcconnector.proxies.ModelObject.entityName)) {
				throw new IllegalArgumentException(modelObjectOutputType + " is not a specialization of ModelObject and can therefore not be used to objects cast to a Model Object");
			}
			
			return outputMetaObject;
		} catch (Exception e) {
			throw new IllegalArgumentException(modelObjectOutputType + " is not an Entity");
		}
	}

	/**
	 * Casts a list of (specializations of) TcConnector.ModelObjects to specializations of
	 * the object
	 * 
	 * @param inputModelObjectList (List<TcConnector.ModelObject>) a list of objects to cast
	 * @return List of IMendixObjects
	 * @throws Exception
	 */
	public List<IMendixObject> cast(List<ModelObject> inputModelObjectList) throws CoreException {
		List<IMendixObject> castedList = new ArrayList<>();
		for (ModelObject inputModelObject : inputModelObjectList) {
			IMendixObject castedObject = cast(inputModelObject);
			if(castedObject != null)
				castedList.add(castedObject);
		}
		return castedList;
	}

	/**
	 * Casts a (specialization of a) TcConnector.ModelObject to a specialization of
	 * the object
	 * 
	 * @param inputModelObject (TcConnector.ModelObject) the object to cast
	 * @return IMendixObject
	 * @throws Exception
	 */
	public IMendixObject cast(ModelObject inputModelObject) throws CoreException {
		IMendixObject inputMendixObject = inputModelObject.getMendixObject();

		IMetaObject inputMetaObject = inputMendixObject.getMetaObject();

		if (inputMetaObject.isSubClassOf(OutputMetaObject)) {
			return inputMendixObject;
		}
		if (raiseError) {
			throw new CoreException(
					"Cannot cast Mendix object( " + inputMendixObject.getId() + " ) to " + OutputMetaObject.getName());
		}
		return null;
	}
}
