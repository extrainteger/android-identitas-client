# Android Oauth 2 client for Identitas: Login button library

[![](https://jitpack.io/v/extrainteger/android-identitas-client.svg)](https://jitpack.io/#extrainteger/android-identitas-client)


"Symbolic" is a project for centralizing peoples identity so people can easily use their identities to access various apps in the future. This library are part of that whole project, serves as oAuth in the project.

This library written in ``Kotlin``. Sorry for inconsistency or dirty code, please give feedback to make this library better.

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
    implementation 'com.github.extrainteger:android-identitas-client:[latest_version]'
}
```

### Usage
I created this library as close as possible with existing usage like twitter and facebook. So, you should haven't too much problem to implement this library to your project.

##### First
Put this ``element`` to your ``activity_login.xml``
```xml
    <com.extrainteger.symbolic.ui.SymbolicLoginButton
            android:id="@+id/loginButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />
```
##### Next step
Initialize a config in ``onCreate()`` method to setting up the login button in your ``LoginActivity.java``
```java
    String client_id = "your_client_id";
    String client_secret = "your_client_secret";
    String redirect_uri = "your_client_id";
    String base_url = "your_base_url"
    List<String> scopes = new ArrayList<>(); //you can leave it with empty data
    scopes.add(SymbolicScope.Companion.getPUBLIC()); // this addition just an example

    SymbolicConfig config = 
            new SymbolicConfig(context, base_url, client_id, client_secret, redirect_uri, scopes);
    
    loginButton.configure(config);
```
##### Next step
Set ``callback`` for the Login Button 
```java
    loginButton.setCallback(new Callback<AuthToken>() {
            @Override
            public void success(@NotNull Result<AuthToken> result) {
                //do some action after this login authorized and got some token
            }

            @Override
            public void failure(@NotNull SymbolicException exception) {
                //do some handle when this login got error
            }
        });
```

##### Next step
Implement ``onActivityResult()`` method in your activity, do something like written below
```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
```

### Library
[Retrofit](http://square.github.io/retrofit/)

[Gson](https://github.com/google/gson)

[Okhttp](http://square.github.io/okhttp/)

### License
    Copyright 2017 Extra Integer

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
