package demo;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.shade.com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static demo.AstraUtils.*;

public class DemoPurchases {
    private static final ImmutableList<String> PRODUCT_CATEGORY = ImmutableList.of("Nike Jumper", "Mac Lipstick", "Gucci Sunglasses", "Reiss Dresses", "Nike Shoes", "Adidas Jackets");
    private static int NUMBER_OF_ONLINE_PURCHASES = 10;
    private static int NUMBER_OF_STORE_PURCHASES = 10;
    private static final String ONLINE = "Online";
    private static final String STORE = "Store";


    public static void main(String[] args) throws IOException {

        //connects to pulsar
        PulsarClient client = createPulsarClient();
        ImmutableList<Customer> customers = createTestCustomers();

        // Create producer on a topic
       Producer<CustomerPurchase> producer = createProducer(client);

        // Create 10 demo orders & send to topic
        sendOnlinePurchasesToTopic(producer, customers);
        sendStorePurchasesToTopic(producer, customers);

        //Close the producer
        producer.close();
        // Close the client
        client.close();
    }

    private static Producer<CustomerPurchase> createProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.AVRO(CustomerPurchase.class))
                .topic(STORE_TOPIC)
                .create();
    }
    static void sendOnlinePurchasesToTopic(Producer<CustomerPurchase> producer, ImmutableList<Customer> customers) throws PulsarClientException {
        do {
            CustomerPurchase testPurchase = createPurchase(customers, ONLINE);
            producer.newMessage()
                    .value(testPurchase)
                    .send();
        } while (--NUMBER_OF_ONLINE_PURCHASES > 0);
    }

    static void sendStorePurchasesToTopic(Producer<CustomerPurchase> producer, ImmutableList<Customer> customers) throws PulsarClientException {
        do {
            CustomerPurchase testPurchase = createPurchase(customers, STORE);
            producer.newMessage()
                    .value(testPurchase)
                    .send();
        } while (--NUMBER_OF_STORE_PURCHASES > 0);
    }

    static CustomerPurchase createPurchase(ImmutableList<Customer> customers, String onlineOffline) {
        Customer customer = customers.get(new Random().nextInt(customers.size()));
        String email = customer.getEmail();
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String randomProduct = PRODUCT_CATEGORY.get(new Random().nextInt(PRODUCT_CATEGORY.size()));
        int randomSpend = ThreadLocalRandom.current().nextInt(50, 1000);
        return new CustomerPurchase(email, firstName, lastName, randomProduct, Instant.now(), onlineOffline, randomSpend);
    }

    private static ImmutableList<Customer> createTestCustomers() {
        Customer customer1 = new Customer("Alberto", "Bortolan", "Alberto@Datastax.com");
        Customer customer2 = new Customer("Ryan", "Kelly", "Ryan@hotmail.com");
        Customer customer3 = new Customer("Bettina", "Swynnerton", "Bettina@gmail.com");
        Customer customer4 = new Customer("Romain", "Anselin", "Romain@yahoo.com");

        return ImmutableList.of(customer1, customer2, customer3, customer4);
    }

}// create new customer class with getters