package com.bow.forest.backend.dao.mongo;

import com.bow.forest.backend.entity.Person;
import com.mongodb.Mongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author vv
 * @since 2017/1/22.
 */
public class MongoApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoApp.class);

    public static void main(String[] args) {
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new Mongo("127.0.0.1", 27017), "test"));
        mongoOps.insert(new Person("Joe", 34));
        Person person = mongoOps.findOne(new Query(where("name").is("Joe")), Person.class);
        LOGGER.info(String.valueOf(person.getAge()));
        mongoOps.dropCollection("person");
    }
}
