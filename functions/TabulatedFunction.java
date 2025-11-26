// TabulatedFunction.java
package functions;

public interface TabulatedFunction {

    // получение количества точек табуляции
    int getPointsCount();

    // получение левой границы области определения
    double getLeftDomainBorder();

    // получение правой границы области определения
    double getRightDomainBorder();

    // получение значения функции в точке x
    double getFunctionValue(double x);

    // получение точки по индексу
    FunctionPoint getPoint(int index);

    // замена точки по индексу
    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    // получение координаты X точки по индексу
    double getPointX(int index);

    // установка координаты X точки по индексу
    void setPointX(int index, double x) throws InappropriateFunctionPointException;

    // получение координаты Y точки по индексу
    double getPointY(int index);

    // установка координаты Y точки по индексу
    void setPointY(int index, double y);

    // удаление точки по индексу
    void deletePoint(int index);

    // добавление точки
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
}
