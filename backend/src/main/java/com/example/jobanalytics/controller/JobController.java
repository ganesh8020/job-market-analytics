package com.example.jobanalytics.controller;

import com.example.jobanalytics.model.Job;
import com.example.jobanalytics.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository repository;

    // Get all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return repository.findAll();
    }

    // Filter by skill
   @GetMapping("/skill/{skill}")
public List<Job> getBySkill(@PathVariable String skill) {
    return repository.findBySkill(skill);
}

    // Filter by location
    @GetMapping("/location/{location}")
public List<Job> getByLocation(@PathVariable String location) {
    return repository.findByLocation(location);
}

@GetMapping("/high-paying")
public List<Job> getHighPayingJobs() {
    return repository.findAll()
            .stream()
            .filter(job -> job.getSalary() > 110000)
            .toList();
}

  @GetMapping("/average-salary")

    public Double getAverageSalary() {

        return repository.findAll()

                .stream()

                .mapToDouble(Job::getSalary)

                .average()

                .orElse(0.0);
    }
    
    @GetMapping("/top-skills")

    public Map<String, Long> getTopSkills() {

        return repository.findAll()

                .stream()

                .collect(Collectors.groupingBy(

                        Job::getSkill,

                        Collectors.counting()

                ));

    }

}