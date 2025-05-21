package teamcentertoolkit.impl;

import java.util.Map;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.IMendixObjectMember;

public class CopyHelper{
	private final IMendixObject source;
	private final IContext context;
	
	public CopyHelper(IMendixObject source, IContext context) {
		this.source = source;
		this.context = context;
	}
	
	
	public IMendixObject copy(String targetEntityName) {
		IMendixObject target = Core.instantiate(context, targetEntityName);
		copy(target);
		return target;
	}
	
	public void copy(IMendixObject target) {		
		Map<String, ? extends IMendixObjectMember<?>> sourceMemberMap = source.getMembers(context);
		Map<String, ? extends IMendixObjectMember<?>> targetMemberMap = target.getMembers(context);

		sourceMemberMap.entrySet()
			.stream()
			.filter(entry -> targetMemberMap.containsKey(entry.getKey()))
			.filter(entry -> Utils.isAttribute(entry.getValue()))
			.forEach(entry -> {
				String sourceKey = entry.getKey();
				IMendixObjectMember<?> sourceMember = entry.getValue();
				if (sourceMember.getClass().equals(targetMemberMap.get(sourceKey).getClass())) {
					target.setValue(context, sourceKey, sourceMember.getValue(context));
				}
			});
	}
}
