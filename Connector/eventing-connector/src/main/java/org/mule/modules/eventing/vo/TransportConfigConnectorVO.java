package org.mule.modules.eventing.vo;

public class TransportConfigConnectorVO
{
	TransportConfigVO consumerTransport;
	TransportConfigVO producerTransport;
	public TransportConfigVO getConsumerTransport() {
		return consumerTransport;
	}
	public void setConsumerTransport(TransportConfigVO consumerTransport) {
		this.consumerTransport = consumerTransport;
	}
	public TransportConfigVO getProducerTransport() {
		return producerTransport;
	}
	public void setProducerTransport(TransportConfigVO producerTransport) {
		this.producerTransport = producerTransport;
	}
	
}
