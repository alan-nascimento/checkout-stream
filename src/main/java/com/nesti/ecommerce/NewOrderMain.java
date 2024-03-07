package com.nesti.ecommerce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class NewOrderMain {

  public static void main(String[] args) {
    var producer = new KafkaProducer<String, String>(properties());
    var message = "Apple Watch Ultra 2,8000.00";
    var record = new ProducerRecord<>("CHECKOUT", message, message);
    try {
      producer.send(record, (data, ex) -> {
        if (ex != null) {
          ex.printStackTrace();
          return;
        }
        System.out.println("Success: " + data.topic() + " " + data.partition() + " " + data.offset());
      }).get();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Properties properties() {
    var properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return properties;
  }
}
