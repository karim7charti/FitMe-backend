package com.techgeeknext.repository;

import com.techgeeknext.model.Equipment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment,Long> {

    @Query("select sum(e.price*e.quantity) from Equipment e where month(e.created_at)=month(CURRENT_DATE())")
    Long getTotalSpend();

    @Query("select count(*)*e.quantity,month(e.created_at) from Equipment e where year(e.created_at)=year(CURRENT_DATE()) group by month(e.created_at)")
    List<List<Integer>> getEquipmentByMonth();

    @Query("select s.payment_date,s.price,c.firstName,c.lastName,c.NationalId from Subscription s inner join Client c on s.c.id=c.id order by s.payment_date desc ")
    List<Object> getRecentSUbcription(PageRequest pageable);

    @Query("select s.payment_date,s.price,c.firstName,c.lastName,c.NationalId from Subscription s inner join Client c on s.c.id=c.id order by s.payment_date desc ")
    List<Object> getSubscriptionsByDates();
}
