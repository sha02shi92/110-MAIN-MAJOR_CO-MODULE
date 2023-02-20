package in.ashokit.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.ashokit.binding.ReadingCOTrigger;
import in.ashokit.entity.COTriggerEntity;
import in.ashokit.entity.CitizenAppEntity;
import in.ashokit.entity.CreateCaseEntity;
import in.ashokit.entity.EDEntity;
import in.ashokit.repository.COTriggerRepo;
import in.ashokit.repository.DataCollectionCitizenAppRepo;
import in.ashokit.repository.DataCollectionCreateCase;
import in.ashokit.repository.EDEntityRepo;
import io.lettuce.core.support.caching.RedisCache;

public class COServiceImpl implements COService {

	@Autowired
	private COTriggerRepo coTriggerRepo;

	@Autowired
	private DataCollectionCreateCase dcCaseRepo;

	@Autowired
	private DataCollectionCitizenAppRepo citizenAppRepo;

	@Autowired
	private EDEntityRepo eligRepo;

//	@Autowired
//	private EmailUtils emailUtils;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public ReadingCOTrigger processPendingTriggers() throws Exception {

		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		
		String address = (String) opsForHash.get("DHS", "DHS_OFFICE_ADDRESS");

		final Long failed = 0l;
		final Long success = 0l;

		ReadingCOTrigger response = new ReadingCOTrigger();

		// fetching all pendings Triggers

		List<COTriggerEntity> pendingTriggers = coTriggerRepo.findByTriggerStatus("Pending");

		ExecutorService service = Executors.newFixedThreadPool(10);
		for (COTriggerEntity coTriggerEntity : pendingTriggers) {

			service.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					processTrigger(response, coTriggerEntity, address);
					return null;
				}
			});

//			EDEntity edEntity = edEntityRepo.findByCaseNum(coTriggerEntity.getCaseNum());
//			Optional<CitizenAppEntity> citizenEntity = citizenAppRepo.findById(null);
//		  	CitizenAppEntity citizenAppEntity = null;
//			if (citizenEntity.isPresent()) {
//				citizenAppEntity = citizenEntity.get();
//			}

		}

		response.setTotalTriggers(Long.valueOf(pendingTriggers.size()));
		response.setSuccTriggers(success);
		response.setFailedTriggers(failed);

		return response;
	}

	private CitizenAppEntity processTrigger(ReadingCOTrigger response, COTriggerEntity coTriggerEntity,String footer)
			throws Exception {

		CitizenAppEntity appEntity = null;

		// get eligibility data based on caseNUm

		EDEntity eligible = eligRepo.findByCaseNum(coTriggerEntity.getCaseNum());

		// get citizen data based in caseNum

		Optional<CreateCaseEntity> findById = dcCaseRepo.findById(coTriggerEntity.getCaseNum());
		if (findById.isPresent()) {
			CreateCaseEntity dcCaseEntity = findById.get();
			Integer appId = dcCaseEntity.getAppId();

			Optional<CitizenAppEntity> appEntityOptional = citizenAppRepo.findById(appId);
			if (appEntityOptional.isPresent()) {
				appEntity = appEntityOptional.get();
			}

		}
		generateAndSendPdf(eligible, appEntity,footer);
		return appEntity;

	}

	private void generateAndSendPdf(EDEntity eligible, CitizenAppEntity appEntity,String footer) throws Exception {

		Document document = new Document(PageSize.A4);
		File file = new File(eligible.getCaseNum() + ".pdf");

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PdfWriter.getInstance(document, fos);
		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("Eligibility Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f, 3.0f, 1.5f, 3.0f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Citizen Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Status", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Start Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan End Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Benefit Amount", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Denial Name", font));
		table.addCell(cell);

		table.addCell(appEntity.getName());
		table.addCell(eligible.getPlanName());
		table.addCell(eligible.getPlanStatus());
		table.addCell(eligible.getStartDate() + " ");
		table.addCell(eligible.getEndDate() + " ");
		table.addCell(eligible.getBenefitAmount() + " ");
		table.addCell(eligible.getDenialReason() + " ");
		
		String [] addressTokens=footer.split("#");
		String houseNo = addressTokens[0];
		String street = addressTokens[1];
		String city = addressTokens[2];
		String phoneNumber = addressTokens[3];
		String email = addressTokens[4];
		String website = addressTokens[5];
		
		
		String footerTxt="H.N :" + houseNo + "STREET : " + street + "CITY : " + city +
				         "PHNO : " + phoneNumber + "EMAIL : " + email + "WEBSITE : " + website;
		
		Paragraph footerP=new Paragraph(footerTxt,font);
        document.add(footerP);
		document.add(table);
		document.close();

		String subject = "His Eligibility Information";
		String body = "His Eligibility Information";

		// store pdf in db
		// store pdf in aws in s3 bucket
//    emailUtils.sendEnail(appEntity.getEmail(),subject,body,file);

		updateTrigger(eligible.getCaseNum(), file);
		file.delete();

	}

	private void updateTrigger(Long caseNum, File file) throws Exception {

		COTriggerEntity coEntity = coTriggerRepo.findByCaseNum(caseNum);
		byte[] array = new byte[(byte) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read();
		coEntity.setCoPdf(array);
		coEntity.setTriggerStatus("Approved");
		coTriggerRepo.save(coEntity);
		fis.close();
	}

}
