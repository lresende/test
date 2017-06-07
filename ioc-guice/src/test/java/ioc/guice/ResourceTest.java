/* 
 * Copyright 2013 Luciano Resende
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ioc.guice;

import static org.fest.assertions.Assertions.assertThat;
import ioc.guice.impl.GuiceServletConfig;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.servlet.GuiceFilter;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class ResourceTest {
    private static String SERVICE_ENDPOINT = "http://localhost:8080/services/guice";

    private static Server server;

    @BeforeClass
    public static void setupBeforeClass() throws Exception {
        // Create the server.
        server = new Server(8080);

        // Create a servlet context and add the jersey servlet.
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");

        // Add our Guice listener that includes our bindings
        servletContextHandler.addEventListener(new GuiceServletConfig());

        // Then add GuiceFilter and configure the server to
        // reroute all requests through this filter.
        servletContextHandler.addFilter(GuiceFilter.class, "/*", null);

        // Must add DefaultServlet for embedded Jetty.
        // Failing to do this will cause 404 errors.
        // This is not needed if web.xml is used instead.
        servletContextHandler.addServlet(DefaultServlet.class, "/");

        server.start();
        // server.join();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        server.stop();
    }

    @Test
    public void TestFoo() throws Exception {
        WebConversation wc = new WebConversation();
        WebRequest request = new GetMethodWebRequest(SERVICE_ENDPOINT + "/foo");
        WebResponse response = wc.getResource(request);

        assertThat(response.getResponseCode()).isEqualTo(200);
    }
    
    @Test
    public void TestCreateFoo() throws Exception {
        WebConversation wc = new WebConversation();
        WebRequest request = new PostMethodWebRequest(SERVICE_ENDPOINT + "/foo");
        WebResponse response = wc.getResource(request);

        System.out.println(response.getHeaderField("location"));
        assertThat(response.getResponseCode()).isEqualTo(201);
    }
}
