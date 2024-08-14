package com.example.taxrobot.app.personalData;


import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/personal_data")
public class PersonalDataController {
    private final PersonalDataService personalDataService;

    public PersonalDataController(PersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @PostMapping
    public PersonalData addPersonalData(@RequestBody PersonalData personalData){
        return personalDataService.addPersonalData(personalData);
    }

    @GetMapping(path = "/getAll")
    public List<PersonalData> getAllPersonalData(){
        return personalDataService.getAllPersonalData();
    }
}
