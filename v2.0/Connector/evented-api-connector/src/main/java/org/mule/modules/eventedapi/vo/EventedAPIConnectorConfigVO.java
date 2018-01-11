package org.mule.modules.eventedapi.vo;

public class EventedAPIConnectorConfigVO 
{
	String persona;
	ProducerVO producer;
	ConsumerVO consumer;
	
	
	public String getPersona() {
		return persona;
	}
	public void setPersona(String persona) {
		this.persona = persona;
	}
	public ProducerVO getProducer() {
		return producer;
	}
	public void setProducer(ProducerVO producer) {
		this.producer = producer;
	}
	public ConsumerVO getConsumer() {
		return consumer;
	}
	public void setConsumer(ConsumerVO consumer) {
		this.consumer = consumer;
	}
	
	

}
