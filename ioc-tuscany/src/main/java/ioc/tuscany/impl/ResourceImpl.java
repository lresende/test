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

import java.net.URI;

import ioc.tuscany.Resource;
import ioc.tuscany.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.oasisopen.sca.annotation.AllowsPassByReference;
import org.oasisopen.sca.annotation.Reference;

@Path("/services/guice")
@AllowsPassByReference
public class ResourceImpl implements Resource {

    @Reference
    @AllowsPassByReference
    private Service service;

    public ResourceImpl() {

    }

    @GET
    @Path("/foo")
    @Consumes("*/*")
    public Response foo() {
        service.foo();

        return Response.ok().build();
    }
    
    @POST
    @Path("/foo")
    @Consumes(MediaType.WILDCARD)
    public Response createFoo() {
        String key = service.createFoo();
        URI location = UriBuilder.fromPath("{key}").build(key);
        
        return Response.created(location).build();
    }

}
