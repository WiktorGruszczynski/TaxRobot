package com.example.taxrobot.initializer;

import com.example.taxrobot.app.personalData.PersonalDataDao;
import com.example.taxrobot.app.personalData.PersonalDataEntity;
import com.example.taxrobot.taxmanager.TaxManager;
import com.example.taxrobot.tools.Launcher;
import org.springframework.stereotype.Service;

@Service
public class InitializerService {
    private final Launcher launcher = new Launcher();
    private final TaxManager taxManager = new TaxManager(launcher);
    private final PersonalDataDao personalDataDao;

    public InitializerService(PersonalDataDao personalDataDao) {
        this.personalDataDao = personalDataDao;
    }

    private void main(){
        launcher.start();
        launcher.listen();

        PersonalDataEntity personalDataEntity = personalDataDao.findById(302L).orElse(null);

        if (personalDataEntity != null){
            taxManager.personalData.loadFromEntity(personalDataEntity);
        }

        taxManager.wageStatementTable.loadFromFile("wage-statement.txt");

        taxManager.fill();
    }

    public void init(){
        new Thread(this::main).start();
    }
}
