package jdk15.inheritancelimit;


/**
 * jdk15 特性
 *   限制继承, 这种限制可以有效防止子类爆炸, 用在框架开发中,
 *   有效控制派生类滥用.
 * @author xiaopantx
 */
public sealed class  Shape permits Rect, Circle, Triangle{
}

final class Rect extends Shape{}
final class Circle extends Shape{}
final class Triangle extends Shape{}
// final class Ellipse extends Shape{}
