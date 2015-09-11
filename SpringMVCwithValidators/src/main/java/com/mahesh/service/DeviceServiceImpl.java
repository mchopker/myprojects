package com.mahesh.service;

import java.sql.Timestamp;
import java.util.Date;
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
		device.setCreationDate(new Timestamp(new Date().getTime()));
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
