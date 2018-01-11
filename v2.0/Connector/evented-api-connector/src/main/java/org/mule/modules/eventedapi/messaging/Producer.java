package org.mule.modules.eventedapi.messaging;

import java.util.HashMap;

import org.mule.modules.eventedapi.vo.ProducerVO;

public class Producer {
	
	private HashMap prodSubjMap=new HashMap();
	
	private ProducerVO producerVo;

	public ProducerVO getProducerVo() {
		return producerVo;
	}

	public void setProducerVo(ProducerVO producerVo) {
		this.producerVo = producerVo;
	}

	public void addSubject(String pSubjectName, ISubject pSubject)
	{
		prodSubjMap.put(pSubjectName, pSubject);
	}
	public ISubject getProdSubject(String pSubjectName)
	{
		return (ISubject) prodSubjMap.get(pSubjectName);
	}
	
	public HashMap getProdSubjMap() {
		return prodSubjMap;
	}

	public void setProdSubjMap(HashMap prodSubjMap) {
		this.prodSubjMap = prodSubjMap;
	}
	
	

}
