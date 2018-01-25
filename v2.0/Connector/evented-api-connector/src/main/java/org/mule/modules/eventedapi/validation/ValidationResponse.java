package org.mule.modules.eventedapi.validation;

public class ValidationResponse 
{
	
	private boolean valid;
	private String validationMessage;
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getValidationMessage() {
		return validationMessage;
	}
	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	
	
	
	
	

}
