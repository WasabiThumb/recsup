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

package io.github.wasabithumb.recsup.benchmark;

import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordComponent;
import io.github.wasabithumb.recsup.RecordSupport;
import io.github.wasabithumb.recsup.RecordSupportInstance;
import org.jspecify.annotations.NullMarked;

import java.util.Random;

@NullMarked
public final class Benchmark {

    static void main() {
        final RecordSupportInstance rsi = RecordSupport.instance();
        final Random r = new Random(-1860091982656245217L);
        Class<?> next;
        int h = 7;

        // Warmup iterations
        for (int i = 0; i < 0x100; i++) {
            next = select(r);
            h = churn(rsi, next, h);
        }

        // Test iterations
        final long start = System.nanoTime();
        for (int i = 0; i < 0x100000; i++) {
            next = select(r);
            h = churn(rsi, next, h);
        }
        final long elapsed = System.nanoTime() - start;

        // Output
        System.out.println("Hash: " + h);
        System.out.println("Elapsed: " + formatNanos(elapsed));
    }

    private static String formatNanos(long nanos) {
        if (nanos < 10_000L) return nanos + "ns";
        if (nanos < 10_000_000L) return (nanos / 1_000L) + "μs";
        if (nanos < 10_000_000_000L) return (nanos / 1_000_000L) + "ms";
        return (nanos / 1_000_000_000L) + "s";
    }

    private static int churn(RecordSupportInstance rsi, Class<?> cls, int h) {
        RecordClass<?> record = rsi.whenRecord(cls);
        if (record == null) return h;
        for (RecordComponent rc : record.getRecordComponents()) {
            h = 31 * h + rc.getName().hashCode();
        }
        return h;
    }

    private static Class<?> select(Random r) {
        return switch (r.nextInt() & 0x7) {
            case 0x0 -> RecordOne.class;
            case 0x1 -> RecordTwo.class;
            case 0x2 -> RecordThree.class;
            case 0x3 -> RecordFour.class;
            default -> Pojo.class;
        };
    }

    //

    private static final class Pojo {
        Pojo() { }
    }

    private record RecordOne(
            int a
    ) { }

    private record RecordTwo(
            int a,
            int b
    ) { }

    private record RecordThree(
            int a,
            int b,
            int c
    ) { }

    private record RecordFour(
            int a,
            int b,
            int c,
            int d
    ) { }

}
