# Android Oauth 2 client for Identitas: Login button library

[![](https://jitpack.io/v/extrainteger/android-identitas-client.svg)](https://jitpack.io/#extrainteger/android-identitas-client)

"Identitas" is a project for centralizing peoples identity so people can easily use their identities to access various apps in the future. This library is part of that whole project, serves as oAuth in the project.

### Get started
Add the following code to root-level ``build.gradle`` 
```groovy
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
next, add the following code to app-level ``build.gradle`` 
```groovy
dependencies {
    compile 'com.github.extrainteger:android-identitas-client:0.1.0'
}
```

### Usage
I created this library as close as possible with existing usage like twitter and facebook. So, you should haven't too much problem to implement this library to your project.

##### First
Put this ``element`` to your ``activity_login.xml``
```xml
    <com.extrainteger.identitaslogin.ui.IdentitasLoginButton
            android:id="@+id/loginButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content">

    </com.extrainteger.identitaslogin.ui.IdentitasLoginButton>
```
##### Next
Initialize a config to setting up the button in your ``LoginActivity.java``
