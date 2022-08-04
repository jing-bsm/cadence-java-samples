package com.uber.cadence.samples.common;

public class Env {
  public static final String HOST = "dev-xm-use1-cadence.foresee.com";

  static {
    System.setProperty("CADENCE_SEEDS", HOST);
  }
}
