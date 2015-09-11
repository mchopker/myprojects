package com.mahesh.controller;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.mahesh.model.Device;

@Component
public class DeviceValidator implements
		org.springframework.validation.Validator {

	private Pattern pattern;

	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	public DeviceValidator() {
		pattern = Pattern.compile(IPADDRESS_PATTERN);
	}

	@Override
	public boolean supports(Class<?> paramClass) {
		return Device.class.isAssignableFrom(paramClass);
	}

	@Override
	public void validate(Object paramObject, Errors paramErrors) {
		Device device = (Device) paramObject;
		if (!pattern.matcher(device.getIpAddress()).matches()) {
			paramErrors.rejectValue("ipAddress", "Device.ipAddress",
					"Not a valid ip address");
		}
	}

}
