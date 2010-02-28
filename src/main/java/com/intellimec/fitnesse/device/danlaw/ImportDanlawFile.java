package com.intellimec.fitnesse.device.danlaw;

import static fitnesse.slimx.spring.Autowire.autowire;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import com.intellimec.drivesync.device.danlaw.DanlawFilename;
import com.intellimec.drivesync.device.danlaw.FileImporter;
import com.intellimec.fitnesse.services.DeviceServices;

public class ImportDanlawFile {

    private List<String> records = new ArrayList<String>();
    private int recordCount = 0;
    private int totalRecordCount;
    private final static String deviceSerialNumber = "1234";
    private DeviceServices deviceServices = new DeviceServices();

    public void setRecords(String record) {
        records.add(record);
    }

    public void table(List<List<String>> table) {
        if (deviceServices.getDeviceWithSerialNumber(deviceSerialNumber) == null)
            deviceServices.createDeviceWithSerialNumber(deviceSerialNumber);

        totalRecordCount = table.size() - 1;
    }

    public void execute() {
        recordCount++;
        if (recordCount == totalRecordCount) {
            File file = createFile();
            importFile(file);
        }
    }

    private File createFile() {
        String filename = DanlawFilename.filenameFor(deviceSerialNumber, new DateTime());
        File file = new File("target/resources", filename);

        try {
            FileUtils.writeLines(file, records);
        } catch (IOException x) {
            throw new RuntimeException("Could not create Danlaw file + " + file.getAbsolutePath(), x);
        }

        return file;
    }

    private void importFile(File file) {
        FileImporter fileImporter = new FileImporter();
        autowire(fileImporter);
        try {
            fileImporter.process(file);
        } finally {
            FileUtils.deleteQuietly(file);
        }
    }

    public void reset() {
    }
}