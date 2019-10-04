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

package eu.javaspecialists.books.dynamicproxies.shortcut.chap3.chap_3_2;

/**
 * Created by sven on 20.01.15.
 */

import org.junit.Test;

import static org.junit.Assert.*;

public class EqualsTest {
    private static final boolean USE_DYNAMIC_PROXIES = false;

    private A[] create() {
        A temp;
        return new A[]{
            temp = new B(42),
            makeProxy(temp),
            temp = makeProxy(temp),
            temp = makeProxy(temp),
            makeProxy(temp),
            temp = new B(57),
            makeProxy(temp),
            makeProxy(temp),
        };
    }

    @Test
    public void reflexive() {
        for (A x : create()) {
            assertTrue(x.equals(x));
        }
    }

    @Test
    public void symmetric() {
        A[] objects = create();

        for (int i = 0; i < objects.length - 1; i++) {
            for (int j = i + 1; j < objects.length; j++) {
                A x = objects[i];
                A z = objects[j];
                testSymmetry(i + "," + j, x, z);
            }
        }
    }

    private void testSymmetry(String description, A x, A z) {
        assertSame(description, x.equals(z), z.equals(x));
    }

    @Test
    public void transitive() {
        A[] objects = create();

        for (int i = 0; i < objects.length; i++) {
            A x = objects[i];
            for (int j = 0; j < objects.length; j++) {
                A y = objects[j];
                for (int k = 0; k < objects.length; k++) {
                    A z = objects[k];
                    testTransitivity(i + "," + j + "," + k, x, y, z);
                }
            }
        }
    }

    private void testTransitivity(String description, A x, A y, A z) {
        if (x.equals(y) && y.equals(z))
            assertTrue(description, x.equals(z));
    }

    @Test
    public void nullEquals() {
        for (A x : create()) {
            assertFalse(x.equals(null));
        }
    }


    @Test
    public void proxyEqualsProxy() {
        A real = new B(42);
        A proxy1 = makeProxy(real);
        A proxy2 = makeProxy(real);
        assertTrue(proxy1.equals(proxy2));
        assertTrue(proxy2.equals(proxy1));
        assertTrue(proxy1.equals(proxy1));
        assertTrue(proxy2.equals(proxy2));
    }

    private A makeProxy(A a) {
        return USE_DYNAMIC_PROXIES ? Proxies.makeSimpleProxy(A.class, a) :
            new C(a);
    }

    @Test
    public void proxyEqualsReal() {
        B b = new B(42);
        A c1 = makeProxy(b);
        assertTrue(c1.equals(b));
    }

    @Test
    public void realEqualsProxy() {
        A real = new B(42);
        A proxy = makeProxy(real);
        assertTrue(real.equals(proxy));
        assertTrue(real.equals(real));
    }

    @Test
    public void cascadingProxyEquals() {
        A real = new B(42);
        A proxy1 = makeProxy(real);
        A proxy2 = makeProxy(proxy1);
        A proxy3 = makeProxy(proxy2);
        assertTrue(real.equals(proxy1));
        assertTrue(real.equals(proxy2));
        assertTrue(real.equals(proxy3));
        assertTrue(proxy1.equals(real));
        assertTrue(proxy2.equals(real));
        assertTrue(proxy3.equals(real));
        assertTrue(proxy1.equals(proxy2));
        assertTrue(proxy1.equals(proxy3));
        assertTrue(proxy2.equals(proxy1));
        assertTrue(proxy2.equals(proxy3));
        assertTrue(proxy3.equals(proxy1));
        assertTrue(proxy3.equals(proxy2));
        assertTrue(proxy1.equals(proxy1));
        assertTrue(proxy2.equals(proxy2));
        assertTrue(proxy3.equals(proxy3));
    }

    @Test
    public void cascadingProxyEqualsWithD() {
        A real = new B(42);
        A proxy1 = makeProxy(real);
        A proxy2 = new D(proxy1);
        A proxy3 = makeProxy(proxy2);
        assertTrue(real.equals(proxy1));
        assertTrue(real.equals(proxy2));
        assertTrue(real.equals(proxy3));
        assertTrue(proxy1.equals(real));
        assertTrue(proxy2.equals(real));
        assertTrue(proxy3.equals(real));
        assertTrue(proxy1.equals(proxy2));
        assertTrue(proxy1.equals(proxy3));
        assertTrue(proxy2.equals(proxy1));
        assertTrue(proxy2.equals(proxy3));
        assertTrue(proxy3.equals(proxy1));
        assertTrue(proxy3.equals(proxy2));
        assertTrue(proxy1.equals(proxy1));
        assertTrue(proxy2.equals(proxy2));
        assertTrue(proxy3.equals(proxy3));
    }

}
