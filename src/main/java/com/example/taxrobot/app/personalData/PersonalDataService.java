package com.example.taxrobot.app.personalData;

import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {
    private final PersonalDataDao personalDataDao;

    public PersonalDataService(PersonalDataDao personalDataDao) {
        this.personalDataDao = personalDataDao;
    }

    public boolean addPersonalData(PersonalDataEntity personalDataEntity){
        PersonalDataEntity savedPersonalData = personalDataDao.save(personalDataEntity);
        return personalDataDao.existsById(savedPersonalData.getId());
    }
}
