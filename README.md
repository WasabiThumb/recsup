# RecSup

![Maven Central Version](https://img.shields.io/maven-central/v/io.github.wasabithumb/recsup)
![License](https://img.shields.io/badge/license-apache--2.0-blue)

Java 8 library for reading Java 14+ records. Aims to be faster than
naïve reflection, minimizing perfomance penalty in modern environments.
Useful for writing serialization frameworks compatible with older
Java versions, i.e. [JToml's reflect serializer](https://github.com/WasabiThumb/jtoml/wiki/Reflect-Serializer).

## Quick Start
### Adding to your Build Script
#### Gradle (Kotlin)
```kotlin
dependencies {
    implementation("io.github.wasabithumb:recsup:VERSION")
}
```

#### Gradle (Groovy)
```groovy
dependencies {
    implementation 'io.github.wasabithumb:recsup:VERSION'
}
```

#### Maven
```xml
<dependencies>
    <dependency>
        <groupId>io.github.wasabithumb</groupId>
        <artifactId>recsup</artifactId>
        <version>VERSION</version>
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

## Technical Details
A provider is selected at initialization time based on the
[runtime feature version](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Runtime.Version.html#feature()):
- For JRE < 14, a "never" provider is used that reports all classes as non-records.
- For JRE 14-15 (or as a fallback), an "invoke" provider is used that leverages the [invoke API](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/invoke/package-summary.html) to access records only when they are available.
- For JRE 16+, record APIs are guaranteed and are called directly by classes compiled for Java 16+.

Then finally a caching wrapper is used to reduce object churn (if not disabled).
This is because the API exposes a ``getRecordComponents`` method which otherwise
would allocate arbitrarily many wrapper objects upon each invocation.

### Configuration Properties
These properties may be set before RecSup is initialized for the first time
to change provider selection behavior:
- ``io.github.wasabithumb.recsup.RecordSupport.noDirect`` - When truthy, disables the direct implementation (forced to use invoke)
- ``io.github.wasabithumb.recsup.RecordSupport.noCache`` - When truthy, disables the caching wrapper

> [!NOTE]
> Truthy in this context means present, non-empty, not "false" and not "0".
> Suggested truthy values are "true" or "1". These properties support library
> relocation and the property name must always begin with the fully qualified
> name of the ``RecordSupport`` class that you want to configure.

## Benchmark
General use case simulation iterating over a few classes for ~1 million iterations.
Executed with OpenJDK 25 using Epsilon GC. To run the benchmark yourself, use
``./gradlew :benchmark``.

| Direct + Cache | Invoke + Cache | Direct | Invoke |
|:--------------:|:--------------:|:------:|:------:|
|     ~44ms      |     ~51ms      | ~435ms | ~458ms |

## License
```text
Copyright 2026 Xavier Pedraza

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
