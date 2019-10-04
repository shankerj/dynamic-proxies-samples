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

package eu.javaspecialists.books.dynamicproxies.shortcut.chap8.chap8_1.moralfibre.virtual;

import eu.javaspecialists.books.dynamicproxies.shortcut.chap8.chap8_1.moralfibre.MoralFibre;
import eu.javaspecialists.books.dynamicproxies.shortcut.chap8.chap8_1.moralfibre.MoralFibreImpl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sven Ruppert on 14.01.14.
 */
public class VirtualMoralFibreThreadSafe {
    private volatile MoralFibre realSubject;
    private final Lock initializationLock = new ReentrantLock();

    protected MoralFibre realSubject() {
        MoralFibre result = realSubject;
        if (result == null) {
            initializationLock.lock();
            try {
                result = realSubject;
                if (result == null) {
                    result = realSubject = new MoralFibreImpl();
                }
            } finally {
                initializationLock.unlock();
            }
        }
        return result;
    }
}
