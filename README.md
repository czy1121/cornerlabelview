# cornerlabelview

[![](https://jitpack.io/v/czy1121/cornerlabelview.svg)](https://jitpack.io/#czy1121/cornerlabelview)

角标(corner label) 

![screenshot_corner_label_view](screenshot_corner_label_view.png)

## Gradle

``` groovy
repositories { 
    maven { url "https://jitpack.io" }
}
```  
    
``` groovy
dependencies {
    compile 'com.github.czy1121:cornerlabelview:1.0.3'
}
```
    
## Usage
    
**XML**

``` xml
<com.github.czy1121.view.CornerLabelView
    style="@style/CornerLabelView.Default"
    app:clvFillColor="#9C27B0"
    app:clvFlags="triangle"
    app:clvPaddingCenter="10dp"
    app:clvText1="五四三二"
    app:clvText2="1234" />
```
 
## License

```
Copyright 2016 czy1121

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