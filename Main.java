import functions.*;

public class Main {
    public static void main(String[] args) {
        // объявляем переменную типа интерфейса
        TabulatedFunction function;

        System.out.println("testing ArrayTabulatedFunction");
        function = new ArrayTabulatedFunction(0, 10, 5);
        testFunction(function);

        System.out.println("\ntesting LinkedListTabulatedFunction");
        function = new LinkedListTabulatedFunction(0, 10, new double[]{1, 2, 3, 4, 5});
        testFunction(function);
    }

    // функция для вывода всех точек
    public static void printAllPoints(TabulatedFunction function, String message) {
        System.out.println(message);
        System.out.println("current points (" + function.getPointsCount() + "):");
        for (int i = 0; i < function.getPointsCount(); i++) {
            FunctionPoint point = function.getPoint(i);
            System.out.printf("  [%d] x=%.2f, y=%.2f\n", i, point.getX(), point.getY());
        }
        System.out.println();
    }

    public static void testFunction(TabulatedFunction function) {
        System.out.println("domain: [" +
                function.getLeftDomainBorder() + ", " +
                function.getRightDomainBorder() + "]");
        System.out.println("pointscount: " + function.getPointsCount());

        printAllPoints(function, "initial state:");

        System.out.println("value at point 5: " + function.getFunctionValue(5));

        // выход за границы области определения
        try {
            System.out.println("value beyond left border: " + function.getFunctionValue(-1));
        } catch (Exception e) {
            System.out.println("expected behavior: " + e.getMessage());
        }

        // неправильный индекс при получении точки
        try {
            FunctionPoint point = function.getPoint(10);
            System.out.println("point: " + point);
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("caught exception: " + e.getMessage());
        }

        // неправильный индекс при установке точки
        try {
            function.setPoint(10, new FunctionPoint(5, 10));
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("caught exception: " + e.getMessage());
        }

        // нарушение порядка X при установке точки
        try {
            function.setPointX(2, function.getPointX(1) - 1); // X меньше предыдущего
        } catch (InappropriateFunctionPointException e) {
            System.out.println("caught exception: " + e.getMessage());
        }

        // нарушение порядка X при добавлении точки
        try {
            function.addPoint(new FunctionPoint(function.getPointX(1), 100)); // Дублирование X
        } catch (InappropriateFunctionPointException e) {
            System.out.println("caught exception: " + e.getMessage());
        }

        System.out.println("\ncorrect operations");

        printAllPoints(function, "before correct operations:");

        try {
            // добавление точки в правильном порядке
            double newX = (function.getPointX(1) + function.getPointX(2)) / 2;
            System.out.println("adding point at x=" + newX);
            function.addPoint(new FunctionPoint(newX, 50));
            System.out.println("point added successfully");

            printAllPoints(function, "after adding point:");

            // изменение Y
            System.out.println("changing Y at index 2 to 100");
            function.setPointY(2, 100);
            System.out.println("y changed successfully");

            printAllPoints(function, "after changing y:");

            // удаление точки
            if (function.getPointsCount() > 3) {
                System.out.println("deleting point at index 1");
                function.deletePoint(1);
                System.out.println("point deleted successfully");

                printAllPoints(function, "final state after deletion:");
            }

        } catch (Exception e) {
            System.out.println("unexpected exception: " + e.getMessage());
        }

        try {
            System.out.println("testing valid X change");
            // Находим безопасное значение X для изменения
            if (function.getPointsCount() > 2) {
                double safeX = (function.getPointX(1) + function.getPointX(2)) / 2;
                System.out.println("changing X at index 1 to " + safeX);
                function.setPointX(1, safeX);
                System.out.println("x changed successfully");

                printAllPoints(function, "after valid x change:");
            }
        } catch (Exception e) {
            System.out.println("unexpected exception during X change: " + e.getMessage());
        }

        System.out.println("final points count: " + function.getPointsCount());

        // удаление точки при недостаточном количестве
        try {
            // создаем функцию с минимальным количеством точек
            TabulatedFunction smallFunction;
            if (function instanceof ArrayTabulatedFunction) {
                smallFunction = new ArrayTabulatedFunction(0, 2, 2);
            } else {
                smallFunction = new LinkedListTabulatedFunction(0, 2, new double[]{1, 2});
            }

            printAllPoints(smallFunction, "small function before deletion attempt:");

            smallFunction.deletePoint(0);
        } catch (IllegalStateException e) {
            System.out.println("caught exception: " + e.getMessage());
        }

        // некорректные границы в конструкторе
        try {
            if (function instanceof ArrayTabulatedFunction) {
                new ArrayTabulatedFunction(10, 5, 3);
            } else {
                new LinkedListTabulatedFunction(10, 5, new double[]{1, 2, 3});
            }
        } catch (IllegalArgumentException e) {
            System.out.println("caught constructor exception: " + e.getMessage());
        }

        // недостаточное количество точек в конструкторе
        try {
            if (function instanceof ArrayTabulatedFunction) {
                new ArrayTabulatedFunction(0, 5, 1);
            } else {
                new LinkedListTabulatedFunction(0, 5, new double[]{1});
            }
        } catch (IllegalArgumentException e) {
            System.out.println("caught constructor exception: " + e.getMessage());
        }
    }
}