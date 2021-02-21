package com.cxwmpt.demo.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data

public class DeviceWeightRecordsVo {

	@JsonProperty("deviceCode")
	private String deviceCode;
	@JsonProperty("weightDate")
	private String weightDate;
	@JsonProperty("workerCode")
	private String workerCode;
	@JsonProperty("workerName")
	private String workerName;
	@JsonProperty("scaleCode")
	private String scaleCode;
	@JsonProperty("scaleName")
	private String scaleName;
	@JsonProperty("tankCode")
	private String tankCode;
	@JsonProperty("targetWeight")
	private Double targetWeight;
	@JsonProperty("actualWeight")
	private Double actualWeight;
	@JsonProperty("residualWeight")
	private Double residualWeight;
	@JsonProperty("remark")
	private String remark;
}
