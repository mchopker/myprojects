package com.mahesh.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mahesh.model.Device;

@Repository
public class DeviceDAOImpl implements DeviceDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void addDevice(Device device) {
		device.setCreationDate(new Timestamp(new Date().getTime()));
		getCurrentSession().save(device);
	}

	public void updateDevice(Device device) {
		Device deviceToUpdate = getDevice(device.getId());
		deviceToUpdate.setHostName(device.getHostName());
		deviceToUpdate.setIpAddress(device.getIpAddress());
		deviceToUpdate.setEnabled(device.isEnabled());
		getCurrentSession().update(deviceToUpdate);
		
	}

	public Device getDevice(int id) {
		Device device = (Device) getCurrentSession().get(Device.class, id);
		return device;
	}

	public void deleteDevice(int id) {
		Device device = getDevice(id);
		if (device != null)
			getCurrentSession().delete(device);
	}

	@SuppressWarnings("unchecked")
	public List<Device> getDevices() {
		return getCurrentSession().createQuery("from Device").list();
	}

}
