package com.techgeeknext.repository;

import com.techgeeknext.model.Client;
import com.techgeeknext.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("select D from Client C inner join Subscription D on D.c.id=C.id where D.payment_date=(select max (s.payment_date) from Subscription s where s.c.id=C.id)")
    List<Object> getAllClient();

    @Query("select D from Client C inner join Subscription D on D.c.id=C.id where C.id=?1 and D.payment_date=(select max (s.payment_date) from Subscription s where s.c.id=C.id) ")
    List<Object> getOneClient(Long id);


    @Query("select count(*) from Client C inner join Subscription S on S.c.id=C.id and S.experation_date=(select max(ss.experation_date) from Subscription ss where  ss.c.id=C.id) and S.experation_date<CURRENT_DATE()")
    long getInactiveClients();


    @Query("select sum (s.price) from Subscription s  where month(s.payment_date)=month(CURRENT_DATE())")
    long getMonthIncomes();

    @Query("select count (*) ,month(c.created_at) from Client c where year(c.created_at)=year(CURRENT_DATE()) group by month(c.created_at) ")
    List<List<Integer>> getNumberClientPerMonth();

    @Query("select sum(s.price),month(s.payment_date) from Subscription s where year(s.payment_date)=year(CURRENT_DATE()) group by month(s.payment_date)")
    List<List<Double>> getIncomesPerMonth();
    @Query("select  datediff(CURRENT_DATE(),max(s.payment_date)) from Subscription s where  s.c.id=?1")
    Object getLastPayment(long id);

    @Query("select s.experation_date,s.payment_date,s.price from Subscription s where s.c.id=?1 and s.experation_date>CURRENT_DATE() order by s.payment_date desc")
    List<Object> getCurrentSubscreption(long id);

    @Query("select s.experation_date,s.payment_date,s.price from Subscription s where s.c.id=?1 and s.experation_date<CURRENT_DATE() order by s.payment_date desc")
    List<Object> getExpiredSubscreption(long id);





}
