package com.first.carrepairshop.repository;

import com.first.carrepairshop.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Component
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
     Employee findByName(String name) ;
     Employee findByPhoneNumber(String phoneNumber);
     List<Employee> findByRole( String role);

     void deleteByName( String name);
     @Modifying
     @Transactional
     @Query("update Employee e set e.name=:#{#employee.name},e.lastname=:#{#employee.lastname},e.age=:#{#employee.age},e.role=:#{#employee.role},e.phoneNumber=:#{#employee.phoneNumber},e.salary=:#{#employee.salary} where e.id=:id")
     Employee updateById(@Param("employee") Employee employee,@Param("id") Integer id);

}
