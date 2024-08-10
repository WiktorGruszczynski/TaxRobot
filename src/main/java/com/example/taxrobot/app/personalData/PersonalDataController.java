package com.example.taxrobot.app.personalData;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/personal_data")
public class PersonalDataController {
    private final PersonalDataService personalDataService;

    public PersonalDataController(PersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @PostMapping
    public PersonalDataEntity addPersonalData(@RequestBody PersonalDataEntity personalDataEntity){
        return personalDataService.addPersonalData(personalDataEntity);
    }
}
