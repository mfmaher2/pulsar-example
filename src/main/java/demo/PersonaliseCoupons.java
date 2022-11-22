package demo;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.apache.pulsar.shade.com.google.common.collect.ImmutableList;

import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static demo.AstraUtils.*;

public class PersonaliseCoupons {

    private static final List<String> BRAND_ON_PROMO = ImmutableList.of("Nike", "Adidas", "Reiss", "Mac");
    private static final Pattern EMAIL_REGEX = Pattern.compile("\\[email:'(?<email>[A-z].*@.*)'");
    private static final Pattern FIRST_NAME_REGEX = Pattern.compile("first_name:'(?<firstName>[A-z].*)'");
    private static final Pattern BRAND_REGEX = Pattern.compile("product:'(?<brand>.*)\\s.*'");

    public static void main(String[] args) {
        // Create the CqlSession object:
        Map<String, String> customersWithCoupons = new HashMap<>();

        try (CqlSession session = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(BUNDLE_PATH))
                .withAuthCredentials(USERNAME,PASSWORD).build()) {

            ResultSet transactionRows = session.execute("select * from stores.transactions");
            for (Row transaction : transactionRows) {
                // Create coupon
                createPersonalisedCouponSudo(customersWithCoupons, transaction);
            }
            System.out.println("Send notification to customers:");
            System.out.println(customersWithCoupons);
        }
        System.exit(0);
    }

    private static void createPersonalisedCouponSudo(Map<String, String> customersWithCoupons, Row transaction) {
        String[] parsedTransaction = transaction.getFormattedContents().trim().split(",");
        Matcher emailMatcher = EMAIL_REGEX.matcher(parsedTransaction[0]);
        Matcher nameMatcher = FIRST_NAME_REGEX.matcher(parsedTransaction[2]);
        Matcher brandMatcher = BRAND_REGEX.matcher(parsedTransaction[5]);
        matchRegex(emailMatcher, nameMatcher, brandMatcher);
        String email = emailMatcher.group("email");
        String name = nameMatcher.group("firstName");
        String brand = brandMatcher.group("brand");

        // If their fav brand has a promotion, send one.
        if (BRAND_ON_PROMO.contains(brand)) {
            customersWithCoupons.put(email, assignCouponCode(brand, name));
        }
    }

    private static void matchRegex(Matcher emailMatcher, Matcher nameMatcher, Matcher brandMatcher) {
        //Code error-prone, just showing how regex can be used
        //it should do a check before finding match e.g. if matcher.find()
        emailMatcher.find();
        nameMatcher.find();
        brandMatcher.find();
    }

    private static String assignCouponCode(String brand, String name) {
        Map<String, String> coupon = new HashMap<>();
        coupon.put("Nike", name + "-LovesNike10");
        coupon.put("Adidas", name + "-LovesAdidas20");
        coupon.put("Reiss", name + "-LovesReiss15");
        coupon.put("Mac", name + "-LovesMac30");
        return coupon.get(brand);
    }
}