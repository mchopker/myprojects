package com.mahesh.service;

import java.util.List;

import com.mahesh.model.Device;

public interface DeviceService {
	
	public void addDevice(Device device);
	public void updateDevice(Device device);
	public Device getDevice(int id);
	public void deleteDevice(int id);
	public List<Device> getDevices();

}
