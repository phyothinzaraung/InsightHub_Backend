package edu.miu.cs489.InsightHub.repository;

import edu.miu.cs489.InsightHub.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
