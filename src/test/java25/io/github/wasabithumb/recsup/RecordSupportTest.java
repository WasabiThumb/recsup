/*
 * Copyright 2026 Xavier Pedraza
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
 */

package io.github.wasabithumb.recsup;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class RecordSupportTest {

    @Test
    void identity() {
        assertFalse(RecordSupport.isRecord(Object.class));
        assertTrue(RecordSupport.isRecord(Sample1.class));
    }

    @Test
    void components() {
        RecordClass<?> cls = RecordSupport.asRecord(Sample1.class);
        assertEquals(Sample1.class, cls.handle());

        RecordComponent[] components = cls.getRecordComponents();
        assertEquals(2, components.length);
        assertNotSame(components[0], components[1]);

        assertEquals(Integer.TYPE, components[0].getType());
        assertEquals("foo", components[0].getName());

        assertEquals(String.class, components[1].getType());
        assertEquals("bar", components[1].getName());
    }

    @Test
    void primaryConstructor() {
        RecordClass<?> cls = RecordSupport.asRecord(Sample1.class);
        assertEquals(Sample1.class, cls.handle());

        Constructor<?> con = cls.getPrimaryConstructor();
        Sample1 instance = assertDoesNotThrow(() -> (Sample1) con.newInstance(42, "meaning of life"));
        assertEquals(42, instance.foo());
        assertEquals("meaning of life", instance.bar());
    }

    //

    private record Sample1(
            int foo,
            String bar
    ) { }

}