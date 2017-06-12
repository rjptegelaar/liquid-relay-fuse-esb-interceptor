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

import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

/**
 * The Liquid consumer.
 */
public class LiquidConsumer extends DefaultConsumer {
    private final LiquidEndpoint endpoint;

    public LiquidConsumer(LiquidEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

   
}
