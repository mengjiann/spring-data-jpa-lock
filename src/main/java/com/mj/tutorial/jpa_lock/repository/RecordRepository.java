package com.mj.tutorial.jpa_lock.repository;

import com.mj.tutorial.jpa_lock.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface RecordRepository extends JpaRepository<Record,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Record r where r.id = :id")
    Record findRecordForWrite(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select r from Record r where r.id = :id")
    Record findRecordForRead(@Param("id") Long id);

}
