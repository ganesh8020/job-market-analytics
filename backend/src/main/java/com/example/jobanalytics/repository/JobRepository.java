package com.example.jobanalytics.repository;

import com.example.jobanalytics.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    boolean existsByTitleAndCompany(String title, String company);

    List<Job> findBySkill(String skill);

    List<Job> findByLocation(String location);
}