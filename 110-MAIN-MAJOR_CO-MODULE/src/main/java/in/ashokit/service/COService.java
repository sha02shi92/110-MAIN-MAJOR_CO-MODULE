package in.ashokit.service;

import org.springframework.web.bind.annotation.RestController;

import in.ashokit.binding.ReadingCOTrigger;


public interface COService {
	
	public ReadingCOTrigger processPendingTriggers() throws Exception;

}
