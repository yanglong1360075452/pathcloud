package com.lifetech.dhap.pathcloud.common;

import com.lifetech.dhap.pathcloud.common.utils.EncryptUtil;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gdw on 6/17/16.
 */
public class BaseJettyTest extends Assert {

    private final static String ENDPOINT_ADDRESS = "http://localhost:8081/api/";

    protected static Server server;

    protected static WebClient client;

    public static String username = "admin@flucloud.com.cn";
    public static String password = "chinacdc";

    @BeforeClass
    public static void setup() throws Exception {
        server = new Server(8081);

        WebAppContext webAppContext = new WebAppContext();

        webAppContext.setContextPath("/");
        webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setConfigurationDiscovered(true);
        webAppContext.setParentLoaderPriority(true);
        server.setHandler(webAppContext);

        server.start();

        List<JacksonJsonProvider> list = new ArrayList<>();
        list.add(new JacksonJsonProvider());

        client = WebClient.create(ENDPOINT_ADDRESS, list);
        client.header("Content-Type", MediaType.APPLICATION_JSON);
        client.header("Accept", MediaType.APPLICATION_JSON);
        client.acceptEncoding("UTF-8");
        client.header("Content-Encoding", "UTF-8");

        String authorizationHeader = "Basic "+ new String(EncryptUtil.encryptBASE64(username + ":" + password), "UTF-8");

        client.header("Authorization", authorizationHeader);
    }

    @AfterClass
    public static void destroy() throws Exception {
        server.stop();
        server.destroy();
    }
}
