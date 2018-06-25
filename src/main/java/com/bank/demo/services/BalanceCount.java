package com.bank.demo.services;

import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
//import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
	 
public class BalanceCount {
 
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
 
        try {
        final StreamsBuilder builder = new StreamsBuilder();
 
        final Serde<String> stringSerde = Serdes.String();
         
        KStream<String, String> source = builder.stream("transaction-input", Consumed.with(stringSerde, stringSerde));
        
        source.filter((key,value)-> value.contains("balance"));
        	 
        KGroupedStream<String, String> groups =source.groupByKey();
        
        KTable<String, Long> counts = groups
        		.count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
      
        /*counts.foreach(new ForeachAction<String, Long>() {
            public void apply(String key, Long value) {
                System.out.printlnbalance(key + "-" + value);
            }
         });*/
      
        counts.toStream().to("balance-output",Produced.with(Serdes.String(), Serdes.Long()));
        
        final Topology topology = builder.build();
        System.out.println(topology.describe());
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);
 
        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
 
        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
        } catch (KafkaException ex) {
        	System.out.println(ex.getMessage());
        }
    }
}