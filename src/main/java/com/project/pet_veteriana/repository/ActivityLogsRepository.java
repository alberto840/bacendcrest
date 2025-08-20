package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.ActivityLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, Integer> {
}
