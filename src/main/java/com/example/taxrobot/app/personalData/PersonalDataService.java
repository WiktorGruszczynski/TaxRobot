package com.example.taxrobot.app.personalData;

import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalDataService {
    private final PersonalDataDao personalDataDao;

    public PersonalDataService(PersonalDataDao personalDataDao) {
        this.personalDataDao = personalDataDao;
    }

    public PersonalData addPersonalData(PersonalData personalDataEntity){
        return personalDataDao.save(personalDataEntity);
    }

    public List<PersonalData> getAllPersonalData() {
        return personalDataDao.findAll();
    }

    public List<Long> getAllIds() {
        return personalDataDao.getAllIds();
    }
}
