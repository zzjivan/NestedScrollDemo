遇到的难点：
1、让view滚动起来，没弄懂时，第一步就是最难的。
2、view交界处滑动时跳变，造成view间有缝隙
3、canScroll的判断

在到达顶部或者底部时，直接setTranslation(0 或者 max)，可解决2中的问题。

效果图：
![Demo](https://github.com/zzjivan/NestedScrollDemo/raw/master/demo.gif “demo”) 
