package in.ashokit.binding;

import javax.persistence.Lob;

import lombok.Data;

@Data
public class ReadingCOTrigger {

	private Integer coTriggerId;
	private Long caseNum;

	@Lob
	private byte[] coPdf;
	private String triggerStatus;

	private Long totalTriggers;

	private Long succTriggers;

	private Long failedTriggers;

}
