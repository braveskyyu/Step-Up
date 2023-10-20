package test;

class Foo {
    int i = 1;

    Foo() {
        System.out.println("parent foo 构造函数");
        System.out.println(i);
        int x = getValue();
        System.out.println(x);
    }

    {
        System.out.println("parent foo 实例代码段");
        i = 2;
    }

    protected int getValue() {
        System.out.println("parent foo getValue");
        return i;
    }
}

class Bar extends Foo {
    int j = 1;
    Bar() {
        System.out.println("child bar 构造函数");
        j = 2;
    }

    {
        System.out.println("child bar 实例代码段");
        j = 3;
    }

    @Override
    protected int getValue() {
        System.out.println("child bar getValue");
        return j;
    }
}

public class ConstructorExample {
    public static void main(String... args) {
        Bar bar = new Bar();
        System.out.println(bar.getValue());
    }
}
