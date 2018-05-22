IRead
============

项目规范

 * 变量格式 `private View mView`;

 * `Layout` 布局文件命名，是 `Include` 的话使用 `include_` 开头。单独为一个 `View` 则以 `view_` 开头

 * `APP` 的颜色以及字体文字大小都已经在 `Values` 中有相关的规范，请选择合适的使用，方便国际化以及夜间模式切换

 * `APP` 中的 `ICON` 尽量使用 `Vector`。创建方法：右键 drawable -> New -> Vector Asset 然后选择合适的Icon，命名规则： `ic_vct_Test`。相关资料以及介绍：https://blog.csdn.net/tiankongcheng6/article/details/60966281

 * 使用MVP模式的地方，在 `Presenter` 中一定要重写 `start()` 方法，方便之后做初始化工作，同样在相应的View层中，也要调用 `getPresenter().start();`，一般都在最开始调用

 * 一般新建一个 `Activity` 或者 `Fragment`（新的视图），如果涉及到数据请求，都需要遵循MVP模式来写。否则继承 `BaseActivity / BaseFragment` 即可

 * `MVP` 中 `View层` 尽量避免处理逻辑相关问题，仅用来控制View

 * 打开一个新的 `Activity` 需要在 `AppRouter` 中配置打开方法（Intent形式，方便传参），实际可参考既存代码

 * 新加功能或者有新的想法最好先在 `Github` 上新建一个 `Issues` 方便后期跟踪

 * `Git` 提交格式： `[模块名] 描述`，如果是解决问题的话 `Github` 上如果有对应的 `Issues` 则需要加上Issues编号： `[模块名][#666] 描述`。如： https://github.com/Lrony/MRead/issues/15

 * 布局中 `View` 最好有默认的值，方便查看。使用：tools:text="Demo"，这样赋值只有在编辑器中才能看见

 * 项目中的所有 `Fragment` 全部使用 `fragmentation` 库中的 `SupportFragment`

 * 提交的代码一定要是格式化后的代码

 * 数据尽量不要重复请求，节省用户流量

 * 所有文字都需要声明到 `字符串资源` 里面，方便国际化

 * 以上都是扯淡，开心就好


### 一些干货

 * 阿里ICONFONT：http://www.iconfont.cn/

 * 给 Android 开发者的 RxJava 详解：http://gank.io/post/560e15be2dca930e00da1083

 * RxJava 与 Retrofit 结合的最佳实践：http://gank.io/post/56e80c2c677659311bed9841
