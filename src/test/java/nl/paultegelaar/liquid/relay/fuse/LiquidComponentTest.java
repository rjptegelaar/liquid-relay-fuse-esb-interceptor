//Copyright 2017 Paul Tegelaar
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package nl.paultegelaar.liquid.relay.fuse;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class LiquidComponentTest extends CamelTestSupport {

	
    @Test
    public void testLiquid() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);       
        
        assertMockEndpointsSatisfied();
    }

    @Test
    public void testLiquidRun() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result1");
        mock.expectedMinimumMessageCount(5);       
        
        assertMockEndpointsSatisfied();
    }
    
    @Test
    public void testLiquidConfig() throws Exception {
       
        MockEndpoint mock = getMockEndpoint("mock:result2");
        mock.expectedMinimumMessageCount(1);       
        
        assertMockEndpointsSatisfied();
        
       
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("timer://foo?repeatCount=1")
                  .to("liquid:testSimple")
                  .to("mock:result");
                
                from("timer://foo?repeatCount=5")
                .to("liquid:testRun")
                .to("mock:result1");     
                
                from("timer://foo?repeatCount=1")
                .to("liquid:testSampleConfig?queueSize=10&queueThreshold=1&hostname=localhost&destination=dummy&port=12345&enabled=true")
                .to("mock:result2");    
                
            }
        };
    }
    
    
}
