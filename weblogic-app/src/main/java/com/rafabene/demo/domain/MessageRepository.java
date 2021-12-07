package com.rafabene.demo.domain;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface MessageRepository extends EntityRepository<Message, Long> {
    
    @Query("SELECT m FROM Message m ORDER BY m.timestamp DESC")
	List<Message> findAllOrdered();
}
