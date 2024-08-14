package com.example.taxrobot.initializer;

import com.example.taxrobot.app.personalData.PersonalDataDao;
import com.example.taxrobot.app.wageStatement.WageStatementDao;
import com.example.taxrobot.taxmanager.TaxManager;
import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import com.example.taxrobot.taxmanager.resources.forms.WageStatement;
import com.example.taxrobot.tools.Launcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitializerService {
    private final Launcher launcher = new Launcher();
    private final TaxManager taxManager = new TaxManager(launcher);
    private final PersonalDataDao personalDataDao;
    private final WageStatementDao wageStatementDao;

    public InitializerService(PersonalDataDao personalDataDao, WageStatementDao wageStatementDao) {
        this.personalDataDao = personalDataDao;
        this.wageStatementDao = wageStatementDao;
    }

    private void setTaxmanagerData(){
        Long personalDataId = 2L;
        PersonalData personalData = personalDataDao.findById(personalDataId).orElse(null);

        if (personalData != null){
            taxManager.personalData.loadFromEntity(personalData);
        }

        List<WageStatement> wageStatementEntityList = wageStatementDao.findAllByPersonalDataId(personalDataId);


        taxManager.wageStatementTable.loadFromEntity(wageStatementEntityList);

    }

    public void initThreadFunction(){
        launcher.start();
        launcher.listen();

        setTaxmanagerData();

        taxManager.fill();
    }

    public void init(){
        new Thread(this::initThreadFunction).start();
    }

}