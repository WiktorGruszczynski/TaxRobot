package com.example.taxrobot.app.personalData;

import com.example.taxrobot.taxmanager.resources.forms.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalDataDao extends JpaRepository<PersonalData, Long> {

    @Query(value = "SELECT id FROM personal_data", nativeQuery = true)
    List<Long> getAllIds();
}
