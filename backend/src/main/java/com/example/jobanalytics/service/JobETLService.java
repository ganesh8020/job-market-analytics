package com.example.jobanalytics.service;

import com.example.jobanalytics.model.Job;
import com.example.jobanalytics.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobETLService {

    @Autowired
    private JobRepository repository;

    private RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 60000)
public void fetchJobs() {

    repository.deleteAllInBatch();  

    String url =  "https://mocki.io/v1/ca4a421b-0d3f-4985-8280-450373bc2371";

    Job[] jobs = restTemplate.getForObject(url, Job[].class);

    if (jobs != null) {
        for (Job job : jobs) {
            repository.save(job);

            if (!repository.existsByTitleAndCompany(

                    job.getTitle(), job.getCompany())) {

                repository.save(job);

            }
        }
    }

    System.out.println(" Job data refreshed successfully");
}
}