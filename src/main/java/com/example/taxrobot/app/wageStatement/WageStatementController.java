package com.example.taxrobot.app.wageStatement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api/wage_statement")
@RestController
public class WageStatementController {

    private final WageStatementService wageStatementService;

    public WageStatementController(WageStatementService wageStatementService) {
        this.wageStatementService = wageStatementService;
    }


    @PostMapping
    public List<WageStatementEntity> addWageStatements(@RequestBody List<WageStatementEntity> wageStatementEntities){
        return wageStatementService.addWageStatements(wageStatementEntities);
    }

    @GetMapping(path = "/PersonalDataId")
    private List<WageStatementEntity> getWageStatemetsByPersonalDataId(@RequestParam Long id){
        return wageStatementService.getWageStatementsByPersonalDataId(id);
    }
}
