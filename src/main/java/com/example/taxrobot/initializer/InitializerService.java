package com.example.taxrobot.initializer;

import com.example.taxrobot.app.personalData.PersonalDataDao;
import com.example.taxrobot.app.personalData.PersonalDataEntity;
import com.example.taxrobot.app.wageStatement.WageStatementDao;
import com.example.taxrobot.app.wageStatement.WageStatementEntity;
import com.example.taxrobot.taxmanager.TaxManager;
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

    private void starter(){
        launcher.start();
        launcher.listen();

        Long personalDataId = 2L;

        PersonalDataEntity personalDataEntity = personalDataDao.findById(personalDataId).orElse(null);

        if (personalDataEntity != null){
            taxManager.personalData.loadFromEntity(personalDataEntity);
        }
        else {
            taxManager.personalData.loadFromFile("personal-data.txt");
        }

        List<WageStatementEntity> wageStatementEntityList = wageStatementDao.findAllByPersonalDataId(personalDataId);

        if (wageStatementEntityList.isEmpty()){
            taxManager.wageStatementTable.loadFromFile("wage-statement.txt");
        }
        else {
            taxManager.wageStatementTable.loadFromEntity(wageStatementEntityList);
        }

        System.out.println(personalDataEntity);
        taxManager.fill();


    }

    public void init(){
        new Thread(this::starter).start();
    }
}

    