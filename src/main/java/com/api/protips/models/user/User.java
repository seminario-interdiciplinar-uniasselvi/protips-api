package com.api.protips.models.user;

import com.api.protips.models.newsletter.Newsletter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {

  @MongoId
  private String id;
  private String email;
  private String newsletterId;
}
