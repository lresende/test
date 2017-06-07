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
package ioc.tuscany;

import static org.fest.assertions.Assertions.assertThat;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.ContributionLocationHelper;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class ResourceTest {
    private static String SERVICE_ENDPOINT = "http://localhost:8080/services/tuscany";

    private static Node node;

    @BeforeClass
    public static void init() throws Exception {
        try {
            String contribution = ContributionLocationHelper.getContributionLocation("application.composite");
            node = NodeFactory.newInstance().createNode("application.composite",
                    new Contribution("ioc-application", contribution));
            node.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
