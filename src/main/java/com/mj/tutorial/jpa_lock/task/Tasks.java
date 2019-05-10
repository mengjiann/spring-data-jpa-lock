package com.mj.tutorial.jpa_lock.task;

import com.mj.tutorial.jpa_lock.entity.Record;
import com.mj.tutorial.jpa_lock.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@Transactional
public class Tasks {

    private RecordRepository recordRepository;

    public Tasks(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public void runUser1Transaction() {
        log.info(" -- User 1 reading Record entity --");

        long start = System.currentTimeMillis();
        Record record1 = null;
        try {
            record1 = recordRepository.findRecordForRead(1L);
        } catch (Exception e) {
            System.err.println("User 1 got exception while acquiring the database lock:\n " + e);
            return;
        }
        log.info("User 1 got the lock, block time was: " + (System.currentTimeMillis() - start));

        //delay for 2 secs
        ThreadSleep(3000);

        log.info("User 1 read Record: " + record1);
    }

    public void runUser2Transaction() {
        ThreadSleep(500);//let User1 acquire optimistic lock first
        log.info(" -- User 2 writing Record entity --");
        long start = System.currentTimeMillis();
        Record record2 = null;
        try {
            record2 = recordRepository.findRecordForWrite(1L);
        } catch (Exception e) {
            log.error("User 2 got exception while acquiring the database lock:\n " + e,e);
            return;
        }
        log.info("User 2 got the lock, block time was: " + (System.currentTimeMillis() - start));

        record2.setRedeemed(new Random().nextInt(50));
        record2 = recordRepository.save(record2);

        log.info("User 2 updated Record: " + record2);
    }

    private void ThreadSleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
    }
}