package teamcentertoolkit.impl;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.core.objectmanagement.member.MendixObjectReference;
import com.mendix.core.objectmanagement.member.MendixObjectReferenceSet;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.IMendixObjectMember;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;

public class Utils {
	public static String requireNonNullString(String value, String message) {
		if (value == null || value.isEmpty() || value.isBlank()) {
			throw new RuntimeException(message);
		}
		return value;		
	}
	
	/**
	 * Checks that an object member is an attribute i.e. not a reference or reference set
	 * 
	 * @param objectMember
	 * @return true if object member is an attribute
	 */
	public static boolean isAttribute(IMendixObjectMember<?> objectMember) {
		return !(objectMember instanceof MendixObjectReference) && !(objectMember instanceof MendixObjectReferenceSet);
	}
	
	
	/***
	 * Overload of {@link #isSubClass(IMetaObject, IMetaObject) isSubClass} Method.
	 * @param specializedMendixObject
	 * @param generalizationEntityName
	 * @throws CoreException
	 */
	public static void isSubClass(IMendixObject specializedMendixObject, String generalizationEntityName) throws CoreException {	
		isSubClass(specializedMendixObject.getMetaObject(), getMetaObject(generalizationEntityName));
	}
	
	/***
     * Overload of {@link #isSubClass(IMetaObject, IMetaObject) isSubClass} Method.
	 * @param specializationEntityName
	 * @param generalizationEntityName
	 * @throws CoreException
	 */
	public static void isSubClass(String specializationEntityName, String generalizationEntityName) throws CoreException {
		isSubClass(getMetaObject(specializationEntityName), getMetaObject(generalizationEntityName));
	}
	
	/***
	 * Overload of {@link #isSubClass(IMetaObject, IMetaObject) isSubClass} Method.
	 * @param specializationObject
	 * @param generalizationMetaObject
	 * @throws CoreException
	 */
	public static void isSubClass(IMendixObject specializationObject, IMetaObject generalizationMetaObject) throws CoreException {
		isSubClass(specializationObject.getMetaObject(), generalizationMetaObject);
	}
	
	/***
	 * Core method to check whether an entity is a specialization of another entity.
	 * Throws an error if the first argument is not a specialization of the second argument.
	 * @param specializationMetaObject MetaObject associated with the specialized Entity
	 * @param generalizationEntityName MetaObject associated with the generalized Entity
	 * @throws CoreException if the first argument is not a specialization of the second argument.
	 */
	public static void isSubClass(IMetaObject specializationMetaObject, IMetaObject generalizationMetaObject) throws CoreException {
		if (!specializationMetaObject.isSubClassOf(generalizationMetaObject)) {
			throw new CoreException(
					String.format("%s is not a specialization of %s.", specializationMetaObject.getName(), generalizationMetaObject.getName()));
		}
	}
	
	/***
	 * Retrieves the MetaObject by Entity Name
	 * @param entityName Name of the entity
	 * @return MetaObject
	 * @throws CoreException If the entity does not exist.
	 */
	private static IMetaObject getMetaObject(String entityName) throws CoreException {
		try {
			return Core.getMetaObject(entityName);
		}
		catch (Exception e) {
			throw new CoreException(String.format("%s is not an entity", entityName));
		}
	}
	
	
}
