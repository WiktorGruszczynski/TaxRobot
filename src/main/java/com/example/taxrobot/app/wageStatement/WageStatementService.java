package com.example.taxrobot.app.wageStatement;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WageStatementService {
    private final WageStatementDao wageStatementDao;

    public WageStatementService(WageStatementDao wageStatementDao) {
        this.wageStatementDao = wageStatementDao;
    }

    public List<WageStatementEntity> addWageStatements(List<WageStatementEntity> wageStatementEntities){
        System.out.println(wageStatementEntities);
        return wageStatementDao.saveAll(wageStatementEntities);
    }

    public List<WageStatementEntity> getWageStatementsByPersonalDataId(Long id){
        return wageStatementDao.findAllByPersonalDataId(id);
    }
}
