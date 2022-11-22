package demo;

import org.apache.pulsar.client.api.*;

public class AstraUtils {
    public static final String PULSAR_TOKEN = System.getenv("PULSAR_TOKEN");
    private static final String SERVICE_URL = "pulsar+ssl://pulsar-gcp-europewest1.streaming.datastax.com:6651";
    private static final Schema<CustomerPurchase> SCHEMA = Schema.AVRO(CustomerPurchase.class);
    public static final String TOPIC = System.getenv("TOPIC");
    static final String STORE_TOPIC = System.getenv("STORE_TOPIC");
    static final String BUNDLE_PATH = System.getenv("BUNDLE_PATH");
    static final String USERNAME =System.getenv("USERNAME");
    static final String PASSWORD = System.getenv("PASSWORD");

    public static PulsarClient createPulsarClient() throws PulsarClientException {
        // Create client object
        return PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .authentication(AuthenticationFactory.token(PULSAR_TOKEN))
                .build();
    }
}
