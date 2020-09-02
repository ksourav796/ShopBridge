package com.hyva.Task.respositories;

import com.hyva.Task.entities.TaskMaster;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskMasterRepository extends JpaRepository<TaskMaster, Long> {
    List<TaskMaster> findByTaskNameAndTaskCategoryAndTaskIdNotIn(String name, String category, Long id);
    List<TaskMaster> findByTaskName(String name);

    List<TaskMaster> findByTaskNameAndTaskCategory(String name, String category);
    TaskMaster findFirstByTaskNameAndTaskCategoryContaining(String searchText, Sort sort);
    List<TaskMaster> findAllByTaskNameContaining(String searchText, Pageable pageable);
    TaskMaster findFirstBy(Sort sort);
    List<TaskMaster> findAllBy(Pageable pageable);
    List<TaskMaster> findAllByTaskNameContaining(String searchText);
}