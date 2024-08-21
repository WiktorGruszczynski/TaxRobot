package com.example.taxrobot.app.wageStatement;

import com.example.taxrobot.taxmanager.resources.forms.WageStatement;
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
    public List<WageStatement> addWageStatements(@RequestBody List<WageStatement> wageStatements){
        return wageStatementService.addWageStatements(wageStatements);
    }

    @GetMapping(path = "/getById/{id}")
    private List<WageStatement> getWageStatemetsByPersonalDataId(@PathVariable("id") Long id){
        return wageStatementService.getWageStatementsByPersonalDataId(id);
    }
}
