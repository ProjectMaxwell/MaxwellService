package com.projectmaxwell.service.dao.impl.sendloop;

import org.codehaus.jackson.annotate.JsonProperty;

public class SendloopImportResponse extends SendloopResponse{


	private int totalImported;
	private int totalDuplicate;
	private int totalFailed;

	@JsonProperty("TotalImported")
	public int getTotalImported() {
		return totalImported;
	}

	@JsonProperty("TotalImported")
	public void setTotalImported(int totalImported) {
		this.totalImported = totalImported;
	}

	@JsonProperty("TotalDuplicate")
	public int getTotalDuplicate() {
		return totalDuplicate;
	}

	@JsonProperty("TotalDuplicate")
	public void setTotalDuplicate(int totalDuplicate) {
		this.totalDuplicate = totalDuplicate;
	}

	@JsonProperty("TotalFailed")
	public int getTotalFailed() {
		return totalFailed;
	}

	@JsonProperty("TotalFailed")
	public void setTotalFailed(int totalFailed) {
		this.totalFailed = totalFailed;
	}
}
