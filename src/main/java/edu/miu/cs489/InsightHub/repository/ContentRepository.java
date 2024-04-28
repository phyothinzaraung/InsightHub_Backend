package edu.miu.cs489.InsightHub.repository;

import edu.miu.cs489.InsightHub.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {
}
