package com.mahesh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahesh.dao.DeviceDAO;
import com.mahesh.model.Device;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {
	
	@Autowired
	private DeviceDAO deviceDAO;

	public void addDevice(Device device) {
		deviceDAO.addDevice(device);		
	}

	public void updateDevice(Device device) {
		deviceDAO.updateDevice(device);
	}

	public Device getDevice(int id) {
		return deviceDAO.getDevice(id);
	}

	public void deleteDevice(int id) {
		deviceDAO.deleteDevice(id);
	}

	public List<Device> getDevices() {
		return deviceDAO.getDevices();
	}

}
