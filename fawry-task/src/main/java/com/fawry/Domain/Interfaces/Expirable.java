package com.fawry.Domain.Interfaces;

import java.time.LocalDate;

public interface Expirable {
  boolean isExpired();

  LocalDate getExpirationDate();
}
