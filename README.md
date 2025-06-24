# RecSup
Java 8 library for reading Java 14+ records. For JRE 14 & 15, record support
is checked with feature tests and implemented with reflection. For JRE 16+,
record support is implicit and implemented with direct access. This is done
via compilation of a small Java 16 stub located in the ``java16`` subproject.

## Quick Start
### Adding to your Build Script
#### Gradle (Kotlin)
```kotlin
dependencies {
    implementation("io.github.wasabithumb:recsup:0.1.1")
}
```

#### Gradle (Groovy)
```groovy
dependencies {
    implementation 'io.github.wasabithumb:recsup:0.1.1'
}
```

#### Maven
```xml
<dependencies>
    <dependency>
        <groupId>io.github.wasabithumb</groupId>
        <artifactId>recsup</artifactId>
        <version>0.1.1</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

### Usage
```java
if (RecordSupport.isRecord(MyRecord.class)) {
    RecordClass<?> record = RecordSupport.asRecord(MyRecord.class);
    RecordComponent[] components = record.getRecordComponents();
    Constructor<?> constructor = record.getPrimaryConstructor();
}
```
The ``RecordComponent`` interface provided by RecSup works identically
to [``java.lang.reflect.RecordComponent``](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/reflect/RecordComponent.html).

## Why?
This project is mostly a fun experiment, however it does seem about **~30% faster** than
basic reflection in some benchmarks (when testing a class for record support and 
iterating over its components).

## License
```text
Copyright 2025 Wasabi Codes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
