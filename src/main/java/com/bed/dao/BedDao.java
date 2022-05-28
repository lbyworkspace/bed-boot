package com.bed.dao;

import com.bed.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedDao extends JpaRepository<Bed,Long> {

    List<Bed> findByType(@Param("type") String type);

    List<Bed> findByTypeAndAccount(@Param("type") String type,@Param("account") String account);

}
