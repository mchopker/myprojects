package com.mahesh.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mahesh.model.Device;
import com.mahesh.service.DeviceService;

@Controller
@RequestMapping(value = "/device")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private Validator deviceValidator;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addDevicePage() {
		ModelAndView modelAndView = new ModelAndView("add-device-form");
		modelAndView.addObject("device", new Device());
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addingDevice(@ModelAttribute @Valid Device device,
			BindingResult result) {
		deviceValidator.validate(device, result);
		if (result.hasErrors()) {
			return new ModelAndView("add-device-form");
		}

		ModelAndView modelAndView = new ModelAndView("home");
		deviceService.addDevice(device);

		String message = "Device was successfully added.";
		modelAndView.addObject("message", message);

		return modelAndView;
	}

	@RequestMapping(value = "/list")
	public ModelAndView listOfDevices() {
		ModelAndView modelAndView = new ModelAndView("list-of-devices");

		List<Device> devices = deviceService.getDevices();
		modelAndView.addObject("devices", devices);

		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editDevicePage(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("edit-device-form");
		Device device = deviceService.getDevice(id);
		modelAndView.addObject("device", device);
		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView edditingDevice(@ModelAttribute @Valid Device device,
			BindingResult result, @PathVariable Integer id) {

		if (result.hasErrors()) {
			return new ModelAndView("add-device-form");
		}

		ModelAndView modelAndView = new ModelAndView("home");
		deviceService.updateDevice(device);
		String message = "Device was successfully edited.";
		modelAndView.addObject("message", message);

		return modelAndView;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteDevice(@PathVariable Integer id) {
		ModelAndView modelAndView = new ModelAndView("home");
		deviceService.deleteDevice(id);
		String message = "Device was successfully deleted.";
		modelAndView.addObject("message", message);
		return modelAndView;
	}

}
