import functions.*;

public class Main {
    public static void main(String[] args) {
        // объявляем переменную типа интерфейса
        TabulatedFunction function;

        System.out.println("Testing ArrayTabulatedFunction");
        function = new ArrayTabulatedFunction(0, 10, 5);
        testFunction(function);

        System.out.println("\nTesting LinkedListTabulatedFunction");
        function = new LinkedListTabulatedFunction(0, 10, new double[]{1, 2, 3, 4, 5});
        testFunction(function);
    }

    public static void testFunction(TabulatedFunction function) {
        System.out.println("Domain: [" +
                function.getLeftDomainBorder() + ", " +
                function.getRightDomainBorder() + "]");
        System.out.println("Points count: " + function.getPointsCount());

        System.out.println("Value at point 5: " + function.getFunctionValue(5));



        // выход за границы области определения
        try {
            System.out.println("Value beyond left border: " + function.getFunctionValue(-1));
        } catch (Exception e) {
            System.out.println("Expected behavior: " + e.getMessage());
        }

        // неправильный индекс при получении точки
        try {
            FunctionPoint point = function.getPoint(10);
            System.out.println("Point: " + point);
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // неправильный индекс при установке точки
        try {
            function.setPoint(10, new FunctionPoint(5, 10));
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // нарушение порядка X при установке точки
        try {
            function.setPointX(2, function.getPointX(1) - 1); // X меньше предыдущего
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // нарушение порядка X при добавлении точки
        try {
            function.addPoint(new FunctionPoint(function.getPointX(1), 100)); // Дублирование X
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // удаление точки при недостаточном количестве
        try {
            // создаем функцию с минимальным количеством точек
            TabulatedFunction smallFunction;
            if (function instanceof ArrayTabulatedFunction) {
                smallFunction = new ArrayTabulatedFunction(0, 2, 2);
            } else {
                smallFunction = new LinkedListTabulatedFunction(0, 2, new double[]{1, 2});
            }
            smallFunction.deletePoint(0);
        } catch (IllegalStateException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // некорректные границы в конструкторе
        try {
            if (function instanceof ArrayTabulatedFunction) {
                new ArrayTabulatedFunction(10, 5, 3);
            } else {
                new LinkedListTabulatedFunction(10, 5, new double[]{1, 2, 3});
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Caught constructor exception: " + e.getMessage());
        }

        // недостаточное количество точек в конструкторе
        try {
            if (function instanceof ArrayTabulatedFunction) {
                new ArrayTabulatedFunction(0, 5, 1);
            } else {
                new LinkedListTabulatedFunction(0, 5, new double[]{1});
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Caught constructor exception: " + e.getMessage());
        }


        System.out.println("\nCorrect operations");
        try {
            // добавление точки в правильном порядке
            double newX = (function.getPointX(1) + function.getPointX(2)) / 2;
            function.addPoint(new FunctionPoint(newX, 50));
            System.out.println("Point added successfully");

            // изменение Y
            function.setPointY(2, 100);
            System.out.println("Y changed successfully");

            // удаление точки
            if (function.getPointsCount() > 3) {
                function.deletePoint(1);
                System.out.println("Point deleted successfully");
            }

        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
        }

        System.out.println("Final points count: " + function.getPointsCount());
    }
}