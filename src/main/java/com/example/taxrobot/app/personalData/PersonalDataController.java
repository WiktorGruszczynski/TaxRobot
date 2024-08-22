package com.example.taxrobot.app.personalData;


import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/personal_data")
@CrossOrigin(origins = "http://localhost:8080")
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

    @GetMapping(path = "/getAllIds")
    public List<Long> getAllIds(){
        return personalDataService.getAllIds();
    }

}
