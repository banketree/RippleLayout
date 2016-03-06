# RippleLayout
===============

RippleLayout implementation of Ripple effect from Material Design for almost all Android API ,it can generate ripple effect for its children or its own from Material Design,once use RippleLayout as parent layout its children will implementation of Ripple effect.

![Demo Image][1]

-------------------------
Usage
-----

Just use RippleLayout as LinearLayout because RippleLayout implement LinearLayout

```java
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rippleLayout = (RippleLayout) findViewById(R.id.rippleLayout);
        rippleLayout.setRippleColor(R.color.ripple_material_light);
    }
```

Use `View` with `RippleLayout` in your layout file:

```xml
<com.eason.ripplelayout.RippleLayout
    android:id="@+id/rippleLayout"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="150dp"
    android:layout_centerHorizontal="true"
    android:layout_marginTop="101dp"
    android:background="@color/secondary_text_default_material_dark">

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/material_deep_teal_200"
        android:text="This is button"
        android:layout_centerHorizontal="true" />


    <TextView
        android:layout_marginTop="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:clickable="true"
        android:text="This is a clickable TextView" />

    <TextView
        android:layout_marginTop="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:clickable="false"
        android:text="This is a non-clickable TextView" />
</com.eason.ripplelayout.RippleLayout>
```
Support for Android api versions <  22 
-----
Developed By
--------------------
Chen Junqian

License
-----------

```
Copyright 2015 Balys Valentukevicius

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

[1]: https://github.com/chenjunqian/RippleLayout/blob/master/readme-image/Screen%20Recording.gif
