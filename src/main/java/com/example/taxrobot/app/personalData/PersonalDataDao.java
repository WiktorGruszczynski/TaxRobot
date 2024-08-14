package com.example.taxrobot.app.personalData;

import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDataDao extends JpaRepository<PersonalData, Long> {

}
