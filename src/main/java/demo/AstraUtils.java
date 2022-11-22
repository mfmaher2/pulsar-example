package demo;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.com.google.common.collect.ImmutableList;
//import org.example.Cus;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class AstraUtils {
    public static final String PULSAR_TOKEN = System.getenv("PULSAR_TOKEN");//"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2NjY2NDUzNjYsImlzcyI6ImRhdGFzdGF4Iiwic3ViIjoiY2xpZW50OzExMGQ1YjU0LWE2N2ItNDRhZi04YjExLTdkODMxNzc0MDZhMjtjSFZzYzJGeUxXVjRZVzF3YkdVPTsxOGRkZjRiZTViIiwidG9rZW5pZCI6IjE4ZGRmNGJlNWIifQ.S0lPC2t5owj3GPxj_678t_W6WGWDJaaHU4kK7_yEb5Bafco1inhodDG4Z0E6gI9hT3oBiQK9BJnGGYhmVFMY50hA6xJ5OH0s2qnLA_eIeHWPPoYyPrXiDlqIAcMwHt9ukrYYa5Id23WXCflfRgRjuSvuub12li2KRORvU8yueKEeSZCBt1e11Q1UUD6d_toSnzVE-XL2fgKM1MEii1CIpvd-weD5UUYUSBsAbbP0ztedTUe8hXgczyWp07xnatiyeYZjxVSrf4WylOKDOeqdZxk4aW9gtx8pcUuqZ9IC15nZMkYYTrsLVApxJL4NQW8NvHjlF6QDiqjNRvdQiD2Xyg";
    private static final String SERVICE_URL = "pulsar+ssl://pulsar-gcp-europewest1.streaming.datastax.com:6651";
    private static final Schema<CustomerPurchase> SCHEMA = Schema.AVRO(CustomerPurchase.class); //Schema.AVRO(Customer.class);
    public static final String TOPIC = System.getenv("TOPIC"); //"persistent://pulsar-example/default/purchases";
    static final String STORE_TOPIC = System.getenv("STORE_TOPIC"); //"persistent://pulsar-example/default/transactions-topic";
    static final String BUNDLE_PATH = System.getenv("BUNDLE_PATH"); //"/Users/cordell.hayes/Documents/Demo/secure-connect-customers.zip";
    static final String USERNAME =System.getenv("USERNAME"); //"CuLeAfdJLmtNrRXkaRdskhZS";
    static final String PASSWORD = System.getenv("PASSWORD"); //"G1oQ_HlzFgeYGQoL19yk1IyqOrbi.0KHcBq-WAdHi0bph32-K5EsLO9zz2u3ZU,Yr_T0xX3fLkjYgeh7USZinKv8zSOvp4YY_sPSGa18q3g3-xW1JPi3oxH_58X5sc7u";

    public static PulsarClient createPulsarClient() throws PulsarClientException {
        // Create client object
        return PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .authentication(AuthenticationFactory.token(PULSAR_TOKEN))
                .build();
    }
}
