package org.sct.easylib.data;

import lombok.Getter;

public enum DependenceData {

    JACKSON_ANNOTATIONS_MD5("20368d1f52e031381a510cd1ce6ea2b7"),
    JACKSON_CORE_MD5("8f84e33a1c06b8fd16b4166b9fc8331b"),
    JACKSON_DATABIND_MD5("f96c78787ea2830e8dfd3a5a66c4f664"),
    KOTLIN_STDLIB_MD5("7dea2e32e4b71f48d8863dbef721b408"),
    KOTLIN_STDLIB_ANNOTATIONS_MD5("4990efa6b740f88e0772f3b8b815ba03"),
    KOTLIN_STDLIB_COMMON_MD5("9c36600bc1179cd6b79a9eb51fefb238"),
    KOTLIN_STDLIB_JDK7_MD5("ec6da201a772809331172ed63ec0f3c0"),
    KOTLIN_STDLIB_JDK8_MD5("60ea8ff676c976e622f9ae14eac1751"),

    JACKSON_DATABIND_URL("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.10.3/jackson-databind-2.10.3.jar"),
    JACKSON_CORE_URL("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.10.3/jackson-core-2.10.3.jar"),
    JACKSON_ANNOTATIONS_URL("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.10.3/jackson-annotations-2.10.3.jar"),
    KOTLIN_STDLIB_URL("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/1.3.72/kotlin-stdlib-1.3.72.jar"),
    KOTLIN_STDLIB_ANNOTATIONS_URL("https://repo1.maven.org/maven2/org/jetbrains/annotations/19.0.0/annotations-19.0.0.jar"),
    KOTLIN_STDLIB_COMMON_URL("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-common/1.3.72/kotlin-stdlib-common-1.3.72.jar"),
    KOTLIN_STDLIB_JDK7_URL("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.3.72/kotlin-stdlib-jdk7-1.3.72.jar"),
    KOTLIN_STDLIB_JDK8_URL("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.3.72/kotlin-stdlib-jdk8-1.3.72.jar");


    @Getter
    String data;

    DependenceData(String data) {
        this.data = data;
    }


}
