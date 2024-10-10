package com.api.protips.repositories;

import com.api.protips.models.newsletter.Newsletter;
import com.api.protips.models.user.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsletterRepository extends MongoRepository<Newsletter, String> {

  List<Newsletter> findByUserId(String userId);
}
