package edu.miu.cs489.InsightHub.repository;

import edu.miu.cs489.InsightHub.model.Category;
import edu.miu.cs489.InsightHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u WHERE u.userId = :userId")
    User findUserById(Integer userId);

    @Query(value = "SELECT u FROM User u where u.email = :email")
    User findUserByEmail(String email);
}
