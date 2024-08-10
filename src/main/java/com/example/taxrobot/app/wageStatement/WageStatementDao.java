package com.example.taxrobot.app.wageStatement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WageStatementDao extends JpaRepository <WageStatementEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM wage_statement WHERE personal_data_id = ?1")
    List<WageStatementEntity> findAllByPersonalDataId(Long id);

}
