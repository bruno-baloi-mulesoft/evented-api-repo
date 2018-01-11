package org.mule.modules.eventedapi.util;

/**
 * Created by rabreu on 2017-12-02.
 */
public class SolaceUtils {

    private static String mqttURI = "tcp://mr-akw23icep.messaging.solace.cloud:20838";
    private static String amqpURI = "amqp://mr-akw23icep.messaging.solace.cloud:20844";
    private static String jmsURI = "smf://mr-akw23icep.messaging.solace.cloud:20832";
    private static String smfURI = "tcp://mr-akw23icep.messaging.solace.cloud:20832";
    private static String username = "solace-cloud-client";
    private static String password = "21cvakgpf5ccoa3oo3q51htip6";
    private static String vpnName = "msgvpn-akw23tz87";

    public static String getMqttURI() {
        return mqttURI;
    }

    public static String getAmqpURI() {
        return amqpURI;
    }

    public static String getJmsURI() {
        return jmsURI;
    }

    public static String getSmfURI() {
        return smfURI;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getVpnName() {
        return vpnName;
    }
}
