package com.example.taxrobot.app.wageStatement;

import com.example.taxrobot.taxmanager.resources.forms.WageStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WageStatementDao extends JpaRepository <WageStatement, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM wage_statement WHERE personal_data_id = ?1")
    List<WageStatement> findAllByPersonalDataId(Long id);

}
