package com.api.protips.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDTO {

  private String[] to;
  private String subject;
  private String body;
  private Template template;


  public EmailDTO(String[] to, String subject, String body) {
    this.to = to;
    this.subject = subject;
    this.body = body;
  }

  public EmailDTO(String[] to, Template template) {
    this.to = to;
    this.template = template;
    this.subject = template.getSubject();
  }

  public void addTo(String to) {
    if (this.to == null) {
      this.to = new String[0];
    }
    String[] newTo = new String[this.to.length + 1];
    System.arraycopy(this.to, 0, newTo, 0, this.to.length);
    newTo[this.to.length] = to;
    this.to = newTo;
  }
}
