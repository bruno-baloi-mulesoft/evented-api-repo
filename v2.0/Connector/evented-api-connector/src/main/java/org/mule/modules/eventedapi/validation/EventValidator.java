package org.mule.modules.eventedapi.validation;

import org.mule.modules.eventedapi.messaging.Event;
import org.mule.modules.eventedapi.vo.PolicyVO;

public class EventValidator implements IValidation
{

	@Override
	public boolean validateEventStructure(Event pEvent, PolicyVO pPolicy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateEventPattern(Event pEvent, PolicyVO pPolicy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateEventShape(Event pEvent, PolicyVO pPolicy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateEventCompliance(Event pEvent, PolicyVO pPolicy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateACL(Event pEvent, PolicyVO pPolicy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateVolume(Event pEvent, PolicyVO pPolicy) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
