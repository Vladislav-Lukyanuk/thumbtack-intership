Задание 4

Распакуйте архив “Задание4.zip” в Ваш рабочий каталог.  В Вашем проекте после этого должны появиться
класс Point3D в  в каталоге main в пакете net.thumbtack.school.figures.v1
классы TestRectangle3D и TestCylinder в  в каталоге test в пакете net.thumbtack.school.figures.v1


Используя разработанные в задании 2 классы Rectangle, Circle и Triangle, а также классы Point2D и Point3D, создайте в пакете net.thumbtack.school.figures.v1 нижеследующие классы. При разработке этих классов настоятельно рекомендуется подумать, какие нижеприведенные методы должны быть в них действительно написаны, а какие можно наследовать от базового класса.
Напоминаем, что все поля классов должны иметь атрибут private.
Везде оси X и Y направлены так же, как и в задании 2, ось Z - вертикально вверх.


Rectangle3D

Прямоугольник с высотой (иными словами, параллелепипед, стоящий на плоскости OXY) 


public Rectangle3D(Point2D leftTop, Point2D rightBottom, int height)
Создает Rectangle3D по координатам углов основания (левого верхнего и правого нижнего) и высотой.

public Rectangle3D(int xLeft, int yTop, int xRight, int yBottom, int height)
Создает Rectangle3D по координатам углов основания (левого верхнего и правого нижнего) и высотой.


public Rectangle3D(int length, int width, int height)
Создает Rectangle3D, левый нижний угол которого находится в начале координат, а  длина, ширина и высота задаются.

public Rectangle3D()
Создает Rectangle3D с размерами (1, 1, 1), левый нижний угол которого находится в начале координат.

public Point2D getTopLeft()
	Возвращает левую верхнюю точку Rectangle основания.

public Point2D getBottomRight()
	Возвращает правую нижнюю точку Rectangle основания.
 
public int getHeight()
	Возвращает высоту параллелепипеда.

public void setTopLeft(Point2D topLeft)
	Устанавливает левую верхнюю точку Rectangle основания.

public void setBottomRight(Point2D bottomRight)
	Устанавливает правую нижнюю точку Rectangle основания.
 
public void setHeight(int height)
	Устанавливает высоту параллелепипеда.
 
public int getLength()
	Возвращает длину  прямоугольника основания.

public int getWidth()
	Возвращает ширину прямоугольника основания.
	
public void moveRel(int dx, int dy)
	Передвигает Rectangle3D на (dx, dy). Высота не изменяется.
 
public void enlarge(int nx, int ny)
Увеличивает стороны Rectangle основания в (nx, ny) раз при сохранении координат левой верхней вершины; высота не изменяется.

public double getArea()
Возвращает площадь прямоугольника  основания.

public double getPerimeter()
Возвращает периметр прямоугольника основания.

public double getVolume()
Возвращает объем параллелепипеда.

public boolean isInside(int x, int y) 
Определяет, лежит ли точка (x, y) внутри Rectangle основания. Если точка лежит на стороне, считается, что она лежит внутри.
 
public boolean isInside(Point2D point)
 Определяет, лежит ли точка point внутри Rectangle основания. Если точка лежит на стороне, считается, что она лежит внутри.

public boolean isInside(int x, int y, int z)
Определяет, лежит ли точка (x, y, z) внутри Rectangle3D. Если точка лежит на стороне, считается, что она лежит внутри.

public boolean isInside(Point3D point)
Определяет, лежит ли точка point внутри Rectangle3D. Если точка лежит на стороне, считается, что она лежит внутри.

public boolean isIntersects(Rectangle3D rectangle)
Определяет, пересекается  ли Rectangle3D с другим Rectangle3D. Считается, что параллелепипеды пересекаются, если у них есть хоть одна общая точка.

public boolean isInside(Rectangle3D rectangle)
Определяет, лежит ли rectangle3D целиком внутри текущего Rectangle3D. 

методы equals и hashCode.  
Не пишите эти методы сами, используйте средства IDEA.





Cylinder 

Цилиндр, стоящий на плоскости OXY.


public Cylinder(Point2D center, int radius, int height)
	Создает Cylinder по координатам центра, значению радиуса и высоте.

public Cylinder(int xCenter, int yCenter, int radius, int height)
	Создает Cylinder по координатам центра, значению радиуса и высоте.
 
public Cylinder(int radius, int height)
	Создает Cylinder  с центром в точке (0, 0) с указанными радиусом и высотой.

public Cylinder()
Создает Cylinder  с центром в точке (0, 0) с радиусом 1 и высотой 1.

public Point2D getCenter()
	Возвращает координаты центра Cylinder.
  
public int getRadius()
Возвращает радиус Cylinder.

public void setCenter(Point2D center)
	Устанавливает координаты центра Cylinder.

public void setRadius(int radius)
	Устанавливает радиус Cylinder.

public int getHeight()
	Возвращает высоту Cylinder.

public void setHeight(int height)
	Устанавливает высоту Cylinder.

public void moveRel(int dx, int dy)
	Передвигает Cylinder на (dx, dy).

public void enlarge(int n)
Увеличивает радиус Cylinder в n раз, не изменяя центра и высоты.

 public double getArea()
Возвращает площадь круга основания цилиндра.

public double getPerimeter()
 Возвращает периметр окружности основания цилиндра.

public double getVolume()
Возвращает объем цилиндра.
 
public boolean isInside(int x, int y, int z) 
Определяет, лежит ли точка (x, y, z) внутри Cylinder. Если точка лежит на поверхности, считается, что она лежит внутри.

public boolean isInside(Point3D point)
Определяет, лежит ли точка point внутри Cylinder. Если точка лежит на поверхности, считается, что она лежит внутри.

методы equals и hashCode.  
Не пишите эти методы сами, используйте средства IDEA.


	
Проверьте работу тестов в консольном окне, запишите все классы на сервер (не забудьте изменить текст сообщения в git commit!) и убедитесь, что на сервере все тесты также проходят успешно (см. Занятие 1, п.15-19)
