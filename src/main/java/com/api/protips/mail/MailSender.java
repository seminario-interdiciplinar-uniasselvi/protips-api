package com.api.protips.mail;

import com.api.protips.mail.dto.EmailDTO;
import java.util.Map;
import java.util.Objects;
import lombok.NonNull;

public interface MailSender {

  void send(@NonNull EmailDTO emailDTO, Map<String, Object> parameters);
}
