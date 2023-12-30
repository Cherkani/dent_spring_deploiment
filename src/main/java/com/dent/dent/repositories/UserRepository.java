package com.dent.dent.repositories;

import com.dent.dent.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
    @Query("SELECT u FROM User u WHERE TYPE(u) = Professor")
    List<Professor> findAllProfessors();

    @Query("SELECT u FROM User u WHERE TYPE(u) = Student")
    List<Student> findAllStudents();

    @Query("SELECT s FROM Student s JOIN s.group g JOIN g.professor p WHERE p.id = :professorId")
    List<Student> findStudentsByProfessor(@Param("professorId") Long professorId);

    @Query("SELECT s FROM Student s WHERE s.group.id = :groupId")
    List<Student> findStudentsByGroup(@Param("groupId") Long groupId);

    @Query("SELECT s.group.pws FROM Student s WHERE s.id = :idStudent")
    List<PW> findPwByStudent(@Param("idStudent") Long studentId);





}
