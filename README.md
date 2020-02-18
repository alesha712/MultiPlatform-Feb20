# MultiPlatformKotlin-Feb20
 
https://medium.com/dev-machina/multiplatform-programming-using-kotlin-native-a-mobile-developers-quest-part-2-c903fda4f23

__*Kotlin Multi platform - Jetbrains - main*__
>https://www.jetbrains.com/lp/mobilecrossplatform/

## Ktor
*releases:*
> https://github.com/ktorio/ktor/releases

## Known Issues
- Ktor version 1.2.4 works with Kotlin version 1.3.50 and with coroutines version 1.3.0 and gradle 5.4.1 - this works with Xcode 10.3
- Changing versions results in `./gradlew` build failling.
- Upgrading versions requires Xcode 11
- The error: 
 > More than one file was found with OS independent path 'META-INF/ktor-http.kotlin_module'

   can be fixed by adding this code to app build.gradle:
~~~
packagingOptions {
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/license.txt'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/notice.txt'
        exclude 'META-INF/ASL2.0'
        exclude("META-INF/*.kotlin_module")
}
~~~

- Xcode script:
~~~
cd $SRCROOT/../../SharedModule/build/xcode-frameworks
./gradlew :SharedModule:packForXCode -PXCODE_CONFIGURATION=${CONFIGURATION}
~~~

