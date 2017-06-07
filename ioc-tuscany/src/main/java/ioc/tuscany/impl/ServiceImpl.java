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
package ioc.tuscany.impl;

import java.util.UUID;

import ioc.tuscany.Service;

public class ServiceImpl implements Service {

    public ServiceImpl() {

    }

    public void foo() {
        System.out.println(">>> Guice service impl : FOO");
    }

    public String createFoo() {
        String key = UUID.randomUUID().toString();
        
        System.out.println(">>> Guice service impl : CreateFOO : " + key);
        return key;
    }
}
