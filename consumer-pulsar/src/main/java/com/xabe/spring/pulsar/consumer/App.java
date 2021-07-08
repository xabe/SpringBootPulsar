package com.xabe.spring.pulsar.consumer;

import java.util.logging.Level;
import java.util.logging.LogManager;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

  public static void main(final String[] args) {
    LogManager.getLogManager().reset();
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    java.util.logging.Logger.getLogger("global").setLevel(Level.ALL);
    SpringApplication.run(App.class, args);
  }

}
