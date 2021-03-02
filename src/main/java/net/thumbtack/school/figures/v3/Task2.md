Задание 2

Распакуйте архив “Задание 2.zip“ в Ваш рабочий каталог.  В Вашем проекте после этого должны появиться
класс Point2D в каталоге main  в пакете net.thumbtack.school.figures.v1
классы TestRectangle, TestCircle и TestTriangle в каталоге test  в пакете net.thumbtack.school.figures.v1

Используя класс Point2D, создайте в пакете net.thumbtack.school.figures.v1 следующие классы  и методы в них. Напоминаем, что на плоскости OXY ось X направлена вправо, ось Y - вниз. 
Обращаем Ваше внимание, что все поля классов должны иметь атрибут private.


Rectangle 

Прямоугольник с горизонтальными и вертикальными сторонами, координаты вершин целые. 
Предполагается, что будут передаваться только правильные значения координат, то есть левая верхняя
точка всегда будет левее и выше правой нижней.


public Rectangle(Point2D leftTop, Point2D rightBottom)
Создает Rectangle по координатам углов - левого верхнего и правого нижнего.

public Rectangle(int xLeft, int yTop, int xRight, int yBottom)
Создает Rectangle по координатам углов - левого верхнего и правого нижнего.

public Rectangle(int length, int width)
Создает Rectangle, левый нижний угол которого находится в начале координат, а  длина (по оси X) и ширина (по оси Y) задаются.

public Rectangle()
Создает Rectangle с размерами (1,1), левый нижний угол которого находится в начале координат.

public Point2D getTopLeft()
	Возвращает левую верхнюю точку Rectangle.

public Point2D getBottomRight()
	Возвращает правую нижнюю точку Rectangle.

public void setTopLeft(Point2D topLeft)
	Устанавливает левую верхнюю точку Rectangle.

public void setBottomRight(Point2D bottomRight)
	Устанавливает правую нижнюю точку Rectangle.

public int getLength()
	Возвращает длину прямоугольника.

public int getWidth()
	Возвращает ширину прямоугольника.
	
public void moveRel(int dx, int dy)
	Передвигает Rectangle на (dx, dy).

public void enlarge(int nx, int ny)
Увеличивает стороны Rectangle в (nx, ny) раз при сохранении координат левой верхней вершины.

public double getArea()
Возвращает площадь прямоугольника. 

public double getPerimeter()
Возвращает периметр прямоугольника.

public boolean isInside(int x, int y) 
Определяет, лежит ли точка (x, y) внутри Rectangle. Если точка лежит на стороне, считается, что она лежит внутри.

public boolean isInside(Point2D point)
Определяет, лежит ли точка point внутри Rectangle. Если точка лежит на стороне, считается, что она лежит внутри.

public boolean isIntersects(Rectangle rectangle)
Определяет, пересекается  ли Rectangle с другим Rectangle. Считается, что прямоугольники пересекаются, если у них есть хоть одна общая точка.

public boolean isInside(Rectangle rectangle)
Определяет, лежит ли rectangle целиком внутри текущего Rectangle. 

методы equals и hashCode.  
Не пишите эти методы сами, используйте средства IDEA. 




Circle 

Круг (окружность). Координаты центра и радиус целые.


public Circle(Point2D center, int radius)
	Создает Circle по координатам центра и значению радиуса.

public Circle(int xCenter, int yCenter, int radius)
	Создает Circle по координатам центра и значению радиуса.
 
public Circle(int radius)
	Создает Circle с центром в точке (0,0) указанного радиуса.

public Circle()
Создает Circle с центром в точке (0,0) с радиусом 1.

public Point2D getCenter()
	Возвращает центр Circle.
  
public int getRadius()
Возвращает радиус Circle.

public void setCenter(Point2D center)
	Устанавливает центр Circle.

public void setRadius(int radius)
	Устанавливает радиус Circle.

public void moveRel(int dx, int dy)
	Передвигает Circle на (dx, dy).

public void enlarge(int n)
Увеличивает радиус Circle в n раз, не изменяя центра.

public double getArea()
Возвращает площадь круга.

public double getPerimeter()
Возвращает периметр окружности (длину окружности).
 
 public boolean isInside(int x, int y) 
Определяет, лежит ли точка (x, y) внутри Circle. Если точка лежит на окружности, считается, что она лежит внутри.

 public boolean isInside(Point2D point)
Определяет, лежит ли точка point внутри Circle. Если точка лежит на окружности, считается, что она лежит внутри.

 методы equals и hashCode.  
Не пишите эти методы сами, используйте средства IDEA.


	
  
Triangle 

Треугольник. Координаты углов целые. Предполагается, что будут передаваться только допустимые значения координат, то есть три точки не лежат на одной прямой


public Triangle(Point2D point1, Point2D point2, Point2D point3)
Создает Triangle по координатам трех точек. 
 
public Point2D getPoint1()
	Возвращает точку 1 треугольника.

public Point2D getPoint2()
	Возвращает точку 2 треугольника.

public Point2D getPoint3()
	Возвращает точку 3 треугольника.

public void setPoint1(Point2D point)
	Устанавливает точку 1 треугольника.

public void setPoint2(Point2D point)
	Устанавливает точку 2 треугольника.

public void setPoint3(Point2D point)
Устанавливает точку 3 треугольника. 

public double getSide12()
	Возвращает длину стороны 1-2.

public double getSide13()
Возвращает длину стороны 1-3.

public double getSide23()
Возвращает длину стороны 2-3.

public void moveRel(int dx, int dy)
	Передвигает Triangle на (dx, dy).

public double getArea()
Возвращает площадь треугольника.

public double getPerimeter()
Возвращает периметр треугольника.  

public boolean isInside(int x, int y) 
Определяет, лежит ли точка (x, y) внутри Triangle. Если точка лежит на стороне треугольника, считается, что она лежит внутри.

public boolean isInside(Point2D point)
Определяет, лежит ли точка point внутри Triangle. Если точка лежит на стороне треугольника, считается, что она лежит внутри.

методы equals и hashCode.  
Не пишите эти методы сами, используйте средства IDEA. Обращаем внимание на то, что треугольники считаются равными только если попарно совпадают точки 1, 2 и 3 для обоих треугольников. Треугольники, созданные на основе тех же точек, но в ином порядке, равными не считаются.



CircleFactory 

Класс, создающий окружности (фабрика окружностей)

public static Circle createCircle(Point2D center, int radius)
Создает Circle по координатам центра и значению радиуса. 

public static int getCircleCount()
	Возвращает количество Circle, созданных с помощью метода createCircle.

public static void reset()
Устанавливает количество Circle, созданных с помощью метода createCircle, равным 0 (иными словами, реинициализирует фабрику).



Проверьте работу тестов в консольном окне, запишите все классы на сервер (не забудьте изменить текст сообщения в git commit!) и убедитесь, что на сервере все тесты также проходят успешно (см. Занятие 1, п.15-19)
