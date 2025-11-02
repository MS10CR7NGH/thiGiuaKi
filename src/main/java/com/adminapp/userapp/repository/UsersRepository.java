package com.adminapp.userapp.repository;

import com.adminapp.userapp.entity.UserCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<UserCollection, String> {
    UserCollection findByUsername(String username);

}
