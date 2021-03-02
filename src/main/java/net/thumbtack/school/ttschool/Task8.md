Задание 8

Распакуйте архив “Задание 8.zip” в Ваш рабочий каталог.  В Вашем проекте после этого должен появиться класс TestTrainee  в каталоге  test в пакете net.thumbtack.school.ttschool.

На этом занятии мы создадим класс Trainee, экземпляры которого будут хранить информацию об учащихся Школы, класс исключений TrainingException и enum ошибок TrainingErrorCode. Разместим эти классы в пакете net.thumbtack.school.ttschool. Для каждого учащегося будем хранить фамилию, имя и одну оценку. Имя и фамилия не могут быть null или пустой строкой. Оценка должна быть в интервале от 1 до 5.

Класс должен содержать следующие конструкторы и методы


public Trainee(String firstName, String lastName, int rating)
Создает Trainee с указанными значениями полей. Для недопустимых значений входных параметров выбрасывает TrainingException с соответствующим TrainingErrorCode

public String getFirstName()
		Возвращает имя учащегося

public void setFirstName(String firstName)
Устанавливает имя учащегося. Для недопустимого значения входного параметров выбрасывает TrainingException с TrainingErrorCode.TRAINEE_WRONG_FIRSTNAME

public String getLastName()
		Возвращает фамилию учащегося

public void setLastName(String lastName)
Устанавливает фамилию учащегося. Для недопустимого значения входного параметров выбрасывает TrainingException с TrainingErrorCode.TRAINEE_WRONG_LASTNAME

public int getRating()
		Возвращает оценку учащегося

public void setRating(int rating)
Устанавливает оценку учащегося. Для недопустимого значения входного параметров выбрасывает TrainingException с TrainingErrorCode.TRAINEE_WRONG_RATING

public String getFullName()
Возвращает полное имя учащегося (имя и фамилию, разделенные пробелом)

Методы equals и hashCode
	Не пишите эти методы сами, используйте средства IDEA. 


Проверьте работу тестов в консольном окне, запишите все классы на сервер (не забудьте изменить текст сообщения в git commit!) и убедитесь, что на сервере все тесты также проходят успешно (см. Занятие 1, п.15-19)
