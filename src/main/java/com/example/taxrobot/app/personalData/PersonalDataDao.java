package com.example.taxrobot.app.personalData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDataDao extends JpaRepository<PersonalDataEntity, Long> {

}
