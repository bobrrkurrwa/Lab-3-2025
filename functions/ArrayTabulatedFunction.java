package functions;

public class ArrayTabulatedFunction implements TabulatedFunction {
    FunctionPoint array[];
    private int pointsCount;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount){
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Left border must be less than right border");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Points count must be more than 2");
        }

        this.pointsCount = pointsCount;
        this.array = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i<pointsCount; i++) {
            double x = leftX + step*i;
            array[i] = new FunctionPoint(x,0.0);
        }
    }
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Left border must be less than right border");
        }
        if (values.length < 2) {
            throw new IllegalArgumentException("Points count must be more than 2");
        }
        this.pointsCount = values.length;
        this.array = new FunctionPoint[values.length];
        double step = (rightX - leftX) / (values.length - 1);

        for (int i = 0; i < values.length; i++) {
            double x = leftX + step * i;
            array[i] = new FunctionPoint(x, values[i]);
        }
    }

    // методы
    public double getLeftDomainBorder(){
        return array[0].getX();
    }
    public double getRightDomainBorder(){
        return array[pointsCount-1].getX();
    }
    public double getFunctionValue(double x) {
        if (x< array[0].getX() || x > array[pointsCount - 1].getX()) {
            return Double.NaN;
        }

        for (int i = 0; i< pointsCount - 1; i++) {
            double x1 = array[i].getX();
            double x2 = array[i + 1].getX();

            if (x >= x1 && x <= x2) {
                if (Math.abs(x - x1) < 1e-10) {
                    return array[i].getY();
                }
                if (Math.abs(x - x2) < 1e-10) {
                    return array[i + 1].getY();
                }

                double slope = (array[i + 1].getY() - array[i].getY()) /
                        (array[i + 1].getX() - array[i].getX());
                return array[i].getY() + slope * (x - array[i].getX());
            }
        }
        return Double.NaN;
    }

    public int getPointsCount(){
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) {
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }
        return new FunctionPoint(array[index]);
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }

        if (index > 0 && point.getX() <= array[index-1].getX()) {
            throw new InappropriateFunctionPointException("X must be greater than previous point's X");
        }
        if (index < pointsCount - 1 && point.getX() >= array[index+1].getX()) {
            throw new InappropriateFunctionPointException("X must be less than next point's X");
        }

        array[index] = new FunctionPoint(point);
    }

    public void setPointX(int index, double x) {
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }

        if (index > 0 && x <= array[index-1].getX()) {
            throw new InappropriateFunctionPointException("X must be greater than previous point's X");
        }
        if (index < pointsCount - 1 && x >= array[index+1].getX()) {
            throw new InappropriateFunctionPointException("X must be less than next point's X");
        }

        array[index].setX(x);
    }

    public void setPointY(int index, double y) {
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }
        array[index].setY(y);
    }

    public double getPointX(int index) {
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }
        return array[index].getX();
    }

    public double getPointY(int index) {
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }
        return array[index].getY();
    }

    public void deletePoint(int index) {
        if (pointsCount < 3) {
            throw new IllegalStateException("Points count must be more than two");
        }
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }

        System.arraycopy(array, index+1, array, index, pointsCount-index-1);
        pointsCount--;
        array[pointsCount] = null;
    }

    public void addPoint(FunctionPoint point){
        FunctionPoint newPoint = new FunctionPoint(point);
        int newIndex = 0;
        while (newIndex<pointsCount && array[newIndex].getX()< newPoint.getX()){
            newIndex++;
        }
        if (newIndex< pointsCount && array[newIndex].getX() == newPoint.getX()){
            throw new InappropriateFunctionPointException("This X is already here");
        }
        if (pointsCount >= array.length) {
            FunctionPoint[] newArray = new FunctionPoint[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, pointsCount);
            array = newArray;
        }
        for (int i = pointsCount; i>newIndex; i--) {
            array[i] = array[i - 1];
        }
        array[newIndex] = newPoint;
        pointsCount++;
    }
}