package uk.ac.sussex.group6.backend.Services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Models.*;
import uk.ac.sussex.group6.backend.Repositories.PricePaidDataRepository;
import uk.ac.sussex.group6.backend.Repositories.PropertyRepository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
public class PricePaidDataService {

    @Autowired
    private PricePaidDataRepository pricePaidDataRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private String[] yearURLS = new String[8];

    @PostConstruct
    private void pullData() {

        yearURLS[0] = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2021.csv";
        yearURLS[1] = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2020.csv";
        yearURLS[2] = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2019.csv";
        yearURLS[3] = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2018.csv";
        yearURLS[4] = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2017-part2.csv";
        yearURLS[5] = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2017-part1.csv";
        yearURLS[6] = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2016-part2.csv";
        yearURLS[7] = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-2016-part1.csv";

        if (pricePaidDataRepository.findAll().isEmpty()) {
            for (String s : yearURLS) {
                convertToRecords(s);
            }
            }
    }

    @Scheduled(cron = "0 0 3 14 1/1 *")
    private void scheduledDownload() {
        System.out.println("Starting download of new monthly data");
        convertToRecords("http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-monthly-update-new-version.csv");
        System.out.println("Monthly data download complete");
    }

    private void downloadFullFile(String fileURL) {
        try {
            URL url = new URL(fileURL);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            FileOutputStream fos = new FileOutputStream("PricePaidDataComplete.csv");
            int count;
            byte[] bytes = new byte[3000000];
            while ((count = bufferedInputStream.read(bytes)) >= 0) {
                fos.write(bytes, 0, count);
            }
            fos.close();
            bufferedInputStream.close();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }

    private void convertToRecords(String url) {
        System.out.println("Started " + url);
        downloadFullFile(url);
        System.out.println("file downloaded");
        BufferedReader fileReader = null;
        CSVParser csvParser = null;
        try {
            fileReader = new BufferedReader(new FileReader("PricePaidDataComplete.csv"));
            csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT);

            for (CSVRecord csvRecord : csvParser) {
                CsvRecord record = new CsvRecord();
                record.setTransactionUniqueIdentifier(csvRecord.get(0));
                record.setPricePaid(Integer.valueOf(csvRecord.get(1)));

                String dateAsString = csvRecord.get(2);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(dateAsString, formatter);
                Date d = Date.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));
                record.setDateOfTransfer(d);

                record.setPostcode(csvRecord.get(3));

                if (csvRecord.get(4).equalsIgnoreCase("D")) {
                    record.setPropertyType(PropertyType.DETACHED);
                } else if (csvRecord.get(4).equalsIgnoreCase("S")) {
                    record.setPropertyType(PropertyType.SEMIDETACHED);
                } else if (csvRecord.get(4).equalsIgnoreCase("T")) {
                    record.setPropertyType(PropertyType.TERRACED);
                } else if (csvRecord.get(4).equalsIgnoreCase("F")) {
                    record.setPropertyType(PropertyType.FLATorMAISONETTE);
                } else {
                    record.setPropertyType(PropertyType.OTHER);
                }

                if (csvRecord.get(5).equalsIgnoreCase("Y")) {
                    record.setPropertyAge(PropertyAge.NEW);
                } else {
                    record.setPropertyAge(PropertyAge.OLD);
                }

                if (csvRecord.get(6).equalsIgnoreCase("F")) {
                    record.setPropertyDuration(PropertyDuration.FREEHOLD);
                } else {
                    record.setPropertyDuration(PropertyDuration.LEASEHOLD);
                }

                record.setPaon(csvRecord.get(7));
                record.setSaon(csvRecord.get(8));
                record.setStreet(csvRecord.get(9));
                record.setLocality(csvRecord.get(10));
                record.setTown(csvRecord.get(11));
                record.setDistrict(csvRecord.get(12));
                record.setCounty(csvRecord.get(13));

                if (csvRecord.get(14).equalsIgnoreCase("A")) {
                    record.setPropertyPPDCategory(PropertyPPDCategory.STANDARD);
                } else {
                    record.setPropertyPPDCategory(PropertyPPDCategory.ADDITIONAL);
                }
                record.setRecordStatus(RecordStatus.ADDITION);

                Property p = new Property();
                if (propertyRepository.existsByPostcodeAndStreetAndPaon(record.getPostcode(), record.getStreet(), record.getPaon())) {
                    p = propertyRepository.findByPostcodeAndStreetAndPaon(record.getPostcode(), record.getStreet(), record.getPaon()).get();
                } else {
                    p.setPaon(record.getPaon());
                    p.setSaon(record.getSaon());
                    p.setStreet(record.getStreet());
                    p.setCounty(record.getCounty());
                    p.setDistrict(record.getDistrict());
                    p.setLocality(record.getLocality());
                    p.setPostcode(record.getPostcode());
                    p.setPropertyType(record.getPropertyType());
                    p.setTown(record.getTown());
                }
                PricePaidData PPD = new PricePaidData();
                PPD.setDateOfTransfer(record.getDateOfTransfer());
                PPD.setPricePaid(record.getPricePaid());
                PPD.setPropertyAge(record.getPropertyAge());
                PPD.setPropertyDuration(record.getPropertyDuration());
                PPD.setPropertyPPDCategory(record.getPropertyPPDCategory());
                PPD.setRecordStatus(record.getRecordStatus());
                PPD.setTransactionUniqueIdentifier(record.getTransactionUniqueIdentifier());

                PPD = pricePaidDataRepository.save(PPD);

                p.addToPricePaidDataArrayList(PPD);
                propertyRepository.save(p);

            }
        } catch (Exception e) {
            System.out.println("Reading CSV Error!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvParser.close();
                new File("PricePaidDataComplete.csv").delete();
                System.out.println("Done " + url);
            } catch (IOException e) {
                System.out.println("Closing fileReader/csvParser Error!");
                e.printStackTrace();
            }
        }
    }

}
