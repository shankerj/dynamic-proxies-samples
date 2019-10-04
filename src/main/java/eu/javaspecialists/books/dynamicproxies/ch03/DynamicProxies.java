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

package eu.javaspecialists.books.dynamicproxies.ch03;

import java.lang.reflect.Proxy;

public class DynamicProxies {
    // tag::Subject[]
    public interface Subject {
        String doSomething(String str);
    }
    // end::Subject[]
    static
    // tag::RealSubject[]
    public class RealSubject implements Subject {
        @Override
        public String doSomething(String str) {
            System.out.println("str = " + str);
            return str + "_DONE";
        }
        public String toString() {
            return "RealSubject";
        }
    }
    // end::RealSubject[]
    static
    // tag::Proxies[]
    public class Proxies { // Façade Design Pattern
        private Proxies() {}
        public static <P> P makeSimpleProxy(Class<P> clazz, P p) {
            return clazz.cast(Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                (proxy, method, args) -> method.invoke(p, args)
            ));
        }
    }
    // end::Proxies[]
    static
    public class Main {
        public static void main(String... args) {
            // tag::main()[]
            Subject subject = Proxies
                .makeSimpleProxy(Subject.class, new RealSubject());
            String hello = subject.doSomething("Hello");
            System.out.println("hello = " + hello);
            System.out.println(subject);
            System.out.println(subject.getClass());

            // Output:
            //str = Hello
            //hello = Hello_DONE
            //RealSubject
            //class com.sun.proxy.$Proxy0
            // end::main()[]
        }
    }
}
