package com.swagger;

import io.swagger.annotations.ApiModelProperty;

public class MetaData {
	@ApiModelProperty(position = 2, required = true, value = "brief description of the property :description ")
	private String description;
	@ApiModelProperty(position = 1, required = true, value = "brief description of the property :responseId ")
	private String responseId;
	@ApiModelProperty(position = 3, required = true, value = "brief description of the property :success ")
	private boolean success;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResponseId() {
		return responseId;
	}
	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
