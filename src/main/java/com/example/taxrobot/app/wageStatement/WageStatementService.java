package com.example.taxrobot.app.wageStatement;

import com.example.taxrobot.taxmanager.resources.forms.WageStatement;
import com.example.taxrobot.taxmanager.schemas.Form;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WageStatementService {
    private final WageStatementDao wageStatementDao;

    public WageStatementService(WageStatementDao wageStatementDao) {
        this.wageStatementDao = wageStatementDao;
    }

    public List<WageStatement> addWageStatements(List<WageStatement> wageStatements){
        Long personalDataId = wageStatements.get(0).getPersonalDataId();

        for (WageStatement wageStatement: wageStatements){
            if (!wageStatement.isValid()){
                return wageStatements.stream().filter(Form::isValid).toList();
            }
        }

        wageStatementDao.deleteAllByPersonalDataId(personalDataId);

        return wageStatementDao.saveAll(wageStatements);
    }

    public List<WageStatement> getWageStatementsByPersonalDataId(Long id){
        return wageStatementDao.findAllByPersonalDataId(id);
    }
}
