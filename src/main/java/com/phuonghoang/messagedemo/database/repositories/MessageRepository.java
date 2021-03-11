package com.phuonghoang.messagedemo.database.repositories;

import com.phuonghoang.messagedemo.database.models.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, String> {}
