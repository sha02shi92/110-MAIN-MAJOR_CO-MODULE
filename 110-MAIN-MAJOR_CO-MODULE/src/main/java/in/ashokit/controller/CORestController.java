package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.binding.ReadingCOTrigger;
import in.ashokit.service.COServiceImpl;

@RestController
public class CORestController {

	@Autowired
	private COServiceImpl service;

	@GetMapping("/process")
	public ReadingCOTrigger processTriggers() throws Exception {
		return service.processPendingTriggers();
	}
}
