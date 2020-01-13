# BRVAH
http://www.recyclerview.org/  
Powerful and flexible RecyclerAdapter,
Please feel free to use this. (Welcome to **Star** and **Fork**)  


### 具体更改部分如下：
#### 1、新增onCreateBaseViewHolder方法回调 子adapter类可以重新此方法，再viewholder创建的时候做一些初始化的操作
#### 2、新增BaseItemProvider的onCreateItemProvider 子ItemProvider可以重新此方法，再viewholder创建的时候做一些初始化的操作
#### 3、新增BaseItemProvider的子view的点击事件onChildClick，子view长按事件onChildLongClick
#### 4、去除BaseQuickAdapter的bindRecycleView方法，使用传统方式setAdapter即可，BaseQuickAdapter内部提供了一个弱引用的recycleview可以拿到recycleivew
#### 5、新增自动加载更多的开关，setAutoLoadMore(boolean autoLoadMore),默认true自动加载，传递false后不自动加载需要手动点击加载更多
#### 6、BaseItemProvider可以拿到adapter对象。需要使用的时候可以调用getAdapter()方法
#### 7、更新loadMoreView一个抽象方法，子类需要重写体用 手动加载的布局


kotlin demo :[BRVAH_kotlin](https://github.com/AllenCoder/BRVAH_kotlin)

## [androidX stable version ](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/releases/tag/2.9.49-androidx)

# Document
- [English](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki)
- [中文1](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/README-cn.md)
- [中文2](http://www.jianshu.com/p/b343fcff51b0)

## [UI](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/694)
## Demo



# proguard-rules.pro
> 此资源库自带混淆规则，并且会自动导入，正常情况下无需手动导入。
> The library comes with `proguard-rules.pro` rules and is automatically imported. Normally no manual import is required.
> You can also go here to view [proguard-rules](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/library/proguard-rules.pro)


# Extension library
[PinnedSectionItemDecoration](https://github.com/oubowu/PinnedSectionItemDecoration)  
[EasyRefreshLayout](https://github.com/anzaizai/EasyRefreshLayout)  
[EasySwipeMenuLayout](https://github.com/anzaizai/EasySwipeMenuLayout)

# Thanks  
[JoanZapata / base-adapter-helper](https://github.com/JoanZapata/base-adapter-helper)

# License
```
Copyright 2016 陈宇明

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
