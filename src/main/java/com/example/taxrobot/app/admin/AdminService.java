package com.example.taxrobot.app.admin;

import com.example.taxrobot.app.personalData.PersonalDataDao;
import com.example.taxrobot.app.wageStatement.WageStatementDao;
import com.example.taxrobot.taxmanager.TaxManager;
import com.example.taxrobot.taxmanager.resources.forms.WageStatement;
import com.example.taxrobot.tools.Launcher;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class AdminService {
    private final Launcher launcher = new Launcher();
    private final PersonalDataDao personalDataDao;
    private final WageStatementDao wageStatementDao;
    private final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Documents/Private Tax/Private Tax 2023/";

    public AdminService(PersonalDataDao personalDataDao, WageStatementDao wageStatementDao) {
        this.personalDataDao = personalDataDao;
        this.wageStatementDao = wageStatementDao;
    }

    private TaxManager initTaxManager(Long id){
        TaxManager taxManager = new TaxManager(launcher);

        taxManager.personalData = personalDataDao.findById(id).orElse(null);

        if (taxManager.personalData == null) return null;

        List<WageStatement> wageStatementList = wageStatementDao.findAllByPersonalDataId(id);

        taxManager.wageStatementTable.loadFromEntity(wageStatementList);

        return taxManager;
    }

    public String fill(Long id) {
        TaxManager taxManager = initTaxManager(id);

        assert taxManager != null;

        taxManager.fill();

        return taxManager.getFilename();
    }

    public ResponseEntity<Resource> downloadFile(String filename) throws FileNotFoundException {
        String path = DOWNLOAD_DIR+filename;
        File file = new File(path);

        Resource resource = new InputStreamResource(
                new FileInputStream(file)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
