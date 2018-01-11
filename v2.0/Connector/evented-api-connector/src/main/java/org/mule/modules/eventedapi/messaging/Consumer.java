package org.mule.modules.eventedapi.messaging;

import java.util.HashMap;

import org.mule.modules.eventedapi.vo.ConsumerVO;

public class Consumer {
	
	private ConsumerVO consumerVo;
	
	private HashMap consumerSubjMap=null;
	

	public ConsumerVO getConsumerVo() {
		return consumerVo;
	}

	public void setConsumerVo(ConsumerVO consumerVo) {
		this.consumerVo = consumerVo;
	}

	
	public void addSubject(String pSubjectName, ISubject pSubject)
	{
		consumerSubjMap.put(pSubjectName, pSubject);
	}
	
	public HashMap getConsumerSubjMap() {
		return consumerSubjMap;
	}
	public ISubject getConsumerSubject(String pSubjectName)
	{
		return (ISubject) consumerSubjMap.get(pSubjectName);
	}

	public void setConsumerSubjMap(HashMap prodSubjMap) {
		this.consumerSubjMap = prodSubjMap;
	}
	
	

}
