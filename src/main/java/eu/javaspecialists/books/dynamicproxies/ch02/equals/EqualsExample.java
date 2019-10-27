/*
 * Copyright (C) 2000-2019 Heinz Max Kabutz
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  Heinz Max Kabutz licenses
 * this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.javaspecialists.books.dynamicproxies.ch02.equals;

import eu.javaspecialists.books.dynamicproxies.ch02.equals.FixedEqualsTest.CustomHashMap;
import eu.javaspecialists.books.dynamicproxies.ch02.equals.FixedEqualsWithHandwrittenProxyTest.*;
import eu.javaspecialists.books.dynamicproxies.ch02.virtual.*;

import static org.junit.Assert.*;

// tag::listing[]
public class EqualsExample {
    public static void main(String... args) {
        CustomMap<Integer, Integer> real = new CustomHashMap<>();
        for (int i = 0; i < 10; i++) real.put(i, i * i);
        CustomMap<Integer, Integer> proxy =
                new UnmodifiableCustomMap<>(real);
        CustomMap<Integer, Integer> empty = new CustomHashMap<>();

        // reflexive
        assertEquals(real, real);
        assertEquals(proxy, proxy);

        // symmetric
        assertEquals(real, proxy);
        assertEquals(proxy, real);

        // transitive
        assertTrue((real.equals(empty) && empty.equals(proxy))
                           == real.equals(proxy));
    }
}
// end::listing[]