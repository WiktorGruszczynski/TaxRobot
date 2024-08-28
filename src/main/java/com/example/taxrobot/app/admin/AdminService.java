package com.example.taxrobot.app.admin;

import com.example.taxrobot.app.personalData.PersonalDataDao;
import com.example.taxrobot.app.wageStatement.WageStatementDao;
import com.example.taxrobot.taxmanager.TaxManager;
import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import com.example.taxrobot.taxmanager.resources.forms.WageStatement;
import com.example.taxrobot.taxmanager.resources.tables.WageStatementTable;
import com.example.taxrobot.tools.Launcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final Launcher launcher = new Launcher();
    private final PersonalDataDao personalDataDao;
    private final WageStatementDao wageStatementDao;


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

    public void fill(Long id) {
        TaxManager taxManager = initTaxManager(id);

        assert taxManager != null;

        taxManager.fill();
    }
}
