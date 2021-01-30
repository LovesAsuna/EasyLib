package org.sct.easylib.data;

import lombok.Getter;

public interface DependenceData {

    interface DependenceMD5 extends DependenceData {}

    interface DependenceUrl extends DependenceData {
        /**
         * 获取依赖url
         *
         * @return 数据
         **/
        String getData();
    }

    interface MavenUrl extends DependenceUrl {}

    interface LanzousUrl extends DependenceUrl {}

    enum MD5 implements DependenceMD5 {
        JACKSON_ANNOTATIONS("20368d1f52e031381a510cd1ce6ea2b7"),
        JACKSON_CORE("8f84e33a1c06b8fd16b4166b9fc8331b"),
        JACKSON_DATABIND("f96c78787ea2830e8dfd3a5a66c4f664"),
        KOTLIN_STDLIB("2598f9c67ccfec4cbf6cdc699cac5841"),
        KOTLIN_STDLIB_ANNOTATIONS("4990efa6b740f88e0772f3b8b815ba03"),
        KOTLIN_STDLIB_COMMON("3063be8b21930539e87a1b70819c46fe"),
        KOTLIN_STDLIB_JDK7("4758bbb21000a32c3b251070e32d9c6e"),
        KOTLIN_STDLIB_JDK8("6bb8118170f75f804eae16c2712009a2");

        @Getter
        String data;

        MD5(String data) {
            this.data = data;
        }
    }

    enum Maven implements MavenUrl {
        JACKSON_DATABIND("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.10.3/jackson-databind-2.10.3.jar"),
        JACKSON_CORE("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.10.3/jackson-core-2.10.3.jar"),
        JACKSON_ANNOTATIONS("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.10.3/jackson-annotations-2.10.3.jar"),
        KOTLIN_STDLIB("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/1.4.10/kotlin-stdlib-1.4.10.jar"),
        KOTLIN_STDLIB_ANNOTATIONS("https://repo1.maven.org/maven2/org/jetbrains/annotations/19.0.0/annotations-19.0.0.jar"),
        KOTLIN_STDLIB_COMMON("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-common/1.4.10/kotlin-stdlib-common-1.4.10.jar"),
        KOTLIN_STDLIB_JDK7("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.4.10/kotlin-stdlib-jdk7-1.4.10.jar"),
        KOTLIN_STDLIB_JDK8("https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.4.10/kotlin-stdlib-jdk8-1.4.10.jar");

        @Getter
        String data;

        Maven(String data) {
            this.data = data;
        }
    }

}
