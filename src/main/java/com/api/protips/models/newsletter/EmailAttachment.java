package com.api.protips.models.newsletter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailAttachment {

  private String filename;
  private String url;
}
