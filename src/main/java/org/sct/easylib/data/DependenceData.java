package org.sct.easylib.data;

import lombok.Getter;

public interface DependenceData {

    enum MD5 implements DependenceData {
        JACKSON_ANNOTATIONS("20368d1f52e031381a510cd1ce6ea2b7"),
        JACKSON_CORE("8f84e33a1c06b8fd16b4166b9fc8331b"),
        JACKSON_DATABIND("f96c78787ea2830e8dfd3a5a66c4f664"),
        KOTLIN_STDLIB("7dea2e32e4b71f48d8863dbef721b408"),
        KOTLIN_STDLIB_ANNOTATIONS("4990efa6b740f88e0772f3b8b815ba03"),
        KOTLIN_STDLIB_COMMON("9c36600bc1179cd6b79a9eb51fefb238"),
        KOTLIN_STDLIB_JDK7("ec6da201a772809331172ed63ec0f3c0"),
        KOTLIN_STDLIB_JDK8("60ea8ff676c976e622f9ae14eac1751");
        
        @Getter
        String data;

        MD5(String data) {
            this.data = data;
        }
    }
    
    enum URL implements DependenceData {
        JACKSON_DATABIND("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.10.3/jackson-databind-2.10.3.jar"),
        JACKSON_CORE("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.10.3/jackson-core-2.10.3.jar"),
        JACKSON_ANNOTATIONS("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.10.3/jackson-annotations-2.10.3.jar"),
        KOTLIN_STDLIB("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/1.3.72/kotlin-stdlib-1.3.72.jar"),
        KOTLIN_STDLIB_ANNOTATIONS("https://repo1.maven.org/maven2/org/jetbrains/annotations/19.0.0/annotations-19.0.0.jar"),
        KOTLIN_STDLIB_COMMON("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-common/1.3.72/kotlin-stdlib-common-1.3.72.jar"),
        KOTLIN_STDLIB_JDK7("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.3.72/kotlin-stdlib-jdk7-1.3.72.jar"),
        KOTLIN_STDLIB_JDK8("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.3.72/kotlin-stdlib-jdk8-1.3.72.jar");

        @Getter
        String data;

        URL(String data) {
            this.data = data;
        }
    }
    
}
