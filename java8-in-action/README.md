# Java8 in action

## 基础知识

## 1 为什么要关心Java8

## 2 行为参数化传递代码

## 3 . Lambda表达式

- 基本语法
     ```$xslt
     (parameter) -> expression
    ```

    或者
    
    ```$xslt
    (parameter) -> { statements; }
    ```
- 使用Lambda

    函数式接口：只定义一个方法的接口
    
    函数描述符：函数式接口的抽象方法。其签名就是Lambda表达式的签名

- @FunctionalInterface, 函数式接口


----

## 函数式数据处理

## 4. 引入流

- 流简介

- 流与集合区别
    - 流只能遍历一次
    - 集合：外部迭代；流：内部迭代
 
 
- 流操作
    - 中间操作（返回Stream类型）
    
    | 操作 | 类型 | 返回类型 | 操作类型 | 函数描述符 |
    | ----- | ------ | ------ | ------ | ------ |
    | filter | 中间 | `Stream<T>` | `Predicate<T>` | T->Boolean |
    | map | 中间 | `Stream<R>` | `Function<T,R>` | T->R |
    | limit | 中间 | `Stream<T>` | | T->Boolean |
    | sorted | 中间 | `Stream<T>` | `Comparator<T>` | (T,T)->Integer |
    | distinct | 中间 | `Stream<T>` | | T->Boolean |

    - 终端操作（返回非Stream类型）
    
    | 操作 | 类型 | 目的 |
    | ----- | ------ | ------ |
    | forEach | 终端 | 消费流中的每个元素并对其应用 Lambda。这一操作返回 void | 
    | count | 终端 | 返回流中元素的个数。这一操作返回 long | 
    | collection | 终端 | 把流归约成一个集合，比如 List、Map 甚至是 Integer。|

## 5. 使用流

- 筛选和切片
    - filter(Predicate)
    - distinct()
    - limit()
    - skip()
    
- 映射
    - map()
    - flatMap()

- 查找和匹配

    - noneMatch() / antMatch() / allMatch()
    - findAny() / findFirst()

- 规约

    - reduce()

- 数值流

    - mapToInt()
    - rangeClosed()
    - boxed() 装箱

- 构建流

    - Stream.of() 合成流
    - Arrays.stream() 数组流
    - Files.lines() 文件流
    - Stream.iterate() 遍历的无限流
    - Stream.generate() 生成的无限流
    
## 6. 用流收集数据

- 收集器

- 规约和汇总
    
    - 最大值(maxBy)
    - 汇总(summingInt)
    - 连接字符串(joining)
    - 规约(reducing)
    
- 分组(groupingBy)

- 分区(partitioningBy)

- 收集器接口
    
    - 实现Collector<T,A,R>，重写五个方法 
    - 实现自己的Collector接口
    
Collectors类的静态工厂方法

| 工厂方法  | 返回类型 | 用于 |
| ----- | ------ | ------ | 
| toList  | `List<T>` | 把流中所有项目收集到List |
| toSet  | `Set<T>` | 把流中所有项目收集到Set，删除重复项 |
| toCollection  | `Collection<T>` | 把流中所有项目收集到给定的供应源创建的集合 |
| counting  | `Long` | 计算流中的元素个数 |
| summingInt  | `Integer` | 对流中项目的一个整数属性求和 |
| averagingInt | `Double` | 计算流中项目 Integer 属性的平均值 |
| summarizingInt | `IntSummaryStatistics` | 收集关于流中项目 Integer 属性的统计值，例如最大、最小、 总和与平均值 |
| joining | `String` | 连接对流中每个项目调用 toString 方法所生成的字符串 |
| maxBy   | `Optional<T>` | 一个包裹了流中按照给定比较器选出的最大元素的 Optional， 或如果流为空则为 Optional.empty() |
| minBy | `Optional<T>` | 一个包裹了流中按照给定比较器选出的最小元素的 Optional， 或如果流为空则为 Optional.empty() |
| reducing | `归约操作产生的类型 ` | 从一个作为累加器的初始值开始，利用 BinaryOperator 与流中的元素逐个结合，从而将流归约为单个值 |
| collectingAndThen | `转换函数返回的类型 ` | 包裹另一个收集器，对其结果应用转换函数 |
| groupingBy | `Map<K, List<T>>` | 根据项目的一个属性的值对流中的项目作问组，并将属性值作为结果 Map 的键 |
| partitioningBy| `Map<Boolean,List<T>> ` | 根据对流中每个项目应用谓词的结果来对项目进行分区 |

## 7. 并行数据处理与性能

- 并行流

    - 顺序流转换成并行流(parallel())
    - 性能
    - 正确使用并行流（小心并发问题）
    - 高效使用并行流
        - 并行流不一定一直比顺序流快
        - 留意装箱拆箱，使用原始类型流（LongStream、IntStream...）
        - 有些操作在并行流上就是比顺序流慢
        - 考虑流的操作流水线总计算成本
        - 较小数据量，不要使用并行流
        - 考虑流背后的数据结构，要适合分解，如ArrayList、IntStream.range()，HashSet、TreeSet
        - 考虑流水线中操作修改流的方式，会改变分解的性能
        - 考虑终端合并的开销
        
- fork-join框架

    - RecursiveTask
    - fork-join最佳用法
        - join会阻塞调用方，所以要在两个子任务都开始再调用join
        - RecursiveTask内部不应该用forkJoinPool.invoke()，应该直接调用compute或者fork。只有顺序执行的时候再调用invoke()
        - 对子任务调用fork方法可以把它排进ForkJoinPool
        - 调试fork-join框架的并行计算可能比较棘手，调用compute的线程并不是概念上的调用方，后者是调用fork的那个
        - 与并行流一样，不一定说使用fork-join就比顺序计算速度快
        
    - 工作窃取（更好的完成工作线程的负载均衡）

- Spliterator
    - 拆分
    - 实现自己的Spliterator
    
----

## 高效Java8编程

## 8. 重构、测试和调试

- 改善可读性/灵活性，重构代码
    - 改善可读性
    - 匿名类 -> Lambda表达式
    - Lambda表达式 -> 方法引用
    - 命令式 -> Stream
    - 增加灵活性
        - 采用函数接口
        - 有条件的延迟执行
        - 环绕执行

- 使用Lambda重构面向对象的设计模式
    - 策略模式
    - 模板方法
    - 观察者方法
    - 责任链模式
    - 工厂方法
    
- 测试Lambda表达式

- 调试
    - StackTrace
    - Log(使用peek())


## 9. 默认方法

- 不断演进的API

- 默认方法的使用(接口方法加上default)

- 解决冲突的三条规则

    1. 类中的方法优先级最高
    2. 上面无法断定的话，则子接口的优先级最高
    3. 如果上面都无法断定，继承了多个接口的类必须显示覆盖和调用默认方法
    
 ## 10. 用Optional取代null
 
 - 在域模型中使用Optional，无法序列化
 
 - 基础类型数据不推荐使用Optional（无法使用filter/map）

 ## 11.CompletableFuture 组合式异步编程

- Future接口局限性
    
    - 两个并行计算结果合并成一个
    - 等待所有Future接口全部完成
    - 仅等待最快的Future接口完成，并返回值
    - 编程方式完成一个Future任务的执行（*）
    - 应对Future的完成事件

- 同步API与异步API
    
    - 同步API（阻塞式调用）
        
    - 异步API（非阻塞式调用）
        - 将代码转换成异步代码(CompletableFuture)
        - 错误处理(CompletableFuture.supplyAsync)
    
- 免受阻塞
    
    使用并行流和CompletableFuture都可以实现并发操作，该如何使用：
    
    - 并行流（CPU密集型，编码简单，高效）
    - CompletableFuture（适合I/O密集型，更灵活）
    
- 异步流水操作
    
    - 如果后面的任务依赖于前面任务的运算，使用`thenAsync`/`thenCompose`
    - 如果两个任务分别可以独立运行，再对结果进行合并，使用`thenCombine`,`thenCombineAsync`
    
- CompletableFuture的completion事件

    - 使用`thenAccept`对已完成的事件，进行处理
    
 ## 12. 新的日期和时间API
 
 - 日期，时间，周期（LocalDate, LocalTime, Period）
 - 操作，解析，格式化日期
 - 时区，历法
 
 ----
 
 ## 超越Java8