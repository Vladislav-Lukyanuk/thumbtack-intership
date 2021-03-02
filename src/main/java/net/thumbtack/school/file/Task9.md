Задание 9

Распакуйте архив “Задание 9.zip” в Ваш рабочий каталог.  В Вашем проекте после этого должен появиться класс TestFileService в каталоге test в пакете net.thumbtack.school.file.

На этом занятии мы создадим сервисный класс FileService для операций ввода-вывода, с помощью которого будем записывать различные данные в двоичные и текстовые файлы. Разместим этот класс в пакете net.thumbtack.school.file (создайте такой пакет). Этот класс не будет иметь своих данных и экземпляры этого класса нам не понадобятся, поэтому все методы этого класса будут иметь атрибут static. В случае каких-либо ошибок методы будут выбрасывать IOException (класс из стандартной библиотеки).
Для сериализации и десериализации в/из Json нам понадобится библиотека Gson (https://github.com/google/gson/blob/master/UserGuide.md). Для того, чтобы мы могли ее использовать, необходимо добавить в pom.xml следующую зависимость (в раздел <dependencies>)

		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.8.5</version>
		</dependency>

Постарайтесь при написании методов не дублировать код - вызывайте уже написанный метод, если это возможно.


public static void  writeByteArrayToBinaryFile(String fileName, byte[] array);
Записывает массив байтов в двоичный файл, имя файла задается текстовой строкой.

public static void  writeByteArrayToBinaryFile(File file, byte[] array);
Записывает массив байтов в двоичный файл, имя файла задается  экземпляром класса File.

public static byte[]  readByteArrayFromBinaryFile(String fileName);
Читает массив байтов из двоичного файла, имя файла задается текстовой строкой.

public static byte[]  readByteArrayFromBinaryFile(File file);
Читает массив байтов из двоичного файла, имя файла задается экземпляром класса File.

public static byte[]  writeAndReadByteArrayUsingByteStream( byte[] array);
Записывает массив байтов в ByteArrayOutputStream, создает на основе данных в полученном  ByteArrayOutputStream экземпляр ByteArrayInputStream и читает из ByteArrayInputStream байты с четными номерами. Возвращает массив прочитанных байтов.

public static void  writeByteArrayToBinaryFileBuffered(String fileName, byte[] array);
Записывает массив байтов в двоичный файл, используя буферизованный вывод, имя файла задается текстовой строкой.

public static void  writeByteArrayToBinaryFileBuffered(File file, byte[] array);
Записывает массив байтов в двоичный файл, используя буферизованный вывод, имя файла задается экземпляром класса File.

public static byte[] readByteArrayFromBinaryFileBuffered(String fileName);
Читает массив байтов из двоичного файла, используя буферизованный ввод, имя файла задается текстовой строкой.

public static byte[] readByteArrayFromBinaryFileBuffered(File file);
Читает массив байтов из двоичного файла, используя буферизованный ввод, имя файла задается экземпляром класса File.

public static void  writeRectangleToBinaryFile(File file, Rectangle rect);
Записывает Rectangle в двоичный файл, имя файла задается экземпляром класса File. Поле цвета не записывать.

public static Rectangle  readRectangleFromBinaryFile(File file);
Читает данные для Rectangle из двоичного файла и создает на их основе экземпляр Rectangle, имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. Поскольку файл не содержит цвета, установить в Rectangle цвет “RED”.

public static void  writeRectangleArrayToBinaryFile(File file, Rectangle[] rects );
Записывает массив из Rectangle в двоичный файл, имя файла задается экземпляром класса File. Поле цвета не записывать.

public static Rectangle[]  readRectangleArrayFromBinaryFileReverse(File file);
Предполагается, что данные в файл записаны в формате предыдущего упражнения. Метод читает вначале данные о последнем записанном в файл Rectangle и создает на их основе экземпляр Rectangle, затем читает данные следующего с конца Rectangle и создает на их основе экземпляр Rectangle и т.д.  вплоть до данных для самого первого записанного в файл Rectangle. Из созданных таким образом экземпляров Rectangle создается массив, который и возвращает метод. Поскольку файл не содержит цвета, установить во всех Rectangle цвет “RED”.  

public static void  writeRectangleToTextFileOneLine(File file, Rectangle rect);
Записывает Rectangle в текстовый файл в одну строку, имя файла задается экземпляром класса File. Поля в файле разделяются пробелами. Поле цвета не записывать.

public static Rectangle  readRectangleFromTextFileOneLine(File file);
Читает данные для Rectangle из текстового файла и создает на их основе экземпляр Rectangle, имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. Поскольку файл не содержит цвета, установить в Rectangle цвет “RED”

public static void  writeRectangleToTextFileFourLines(File file, Rectangle rect);
Записывает Rectangle в текстовый файл, каждое число в отдельной строке , имя файла задается экземпляром класса File. Поле цвета не записывать.

public static Rectangle  readRectangleFromTextFileFourLines(File file);
Читает данные для Rectangle из текстового файла и создает на их основе экземпляр Rectangle, имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. Поскольку файл не содержит цвета, установить в Rectangle цвет “RED”

public static void  writeTraineeToTextFileOneLine(File file, Trainee trainee);
Записывает Trainee в текстовый файл в одну строку в кодировке UTF-8, имя файла задается экземпляром класса File. Имя, фамилия и оценка в файле разделяются пробелами. 

public static Trainee  readTraineeFromTextFileOneLine(File file);
Читает данные для Trainee из текстового файла и создает на их основе экземпляр Trainee, имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. 

public static void  writeTraineeToTextFileThreeLines(File file, Trainee trainee);
Записывает Trainee в текстовый файл в кодировке UTF-8, каждое поле в отдельной строке, имя 	файла задается экземпляром класса File. 

public static Trainee  readTraineeFromTextFileThreeLines(File file);
Читает данные для Trainee из текстового файла и создает на их основе экземпляр Trainee, имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. 

public static void  serializeTraineeToBinaryFile(File file, Trainee trainee);
Сериализует Trainee в двоичный файл, имя файла задается экземпляром класса File. 

public static Trainee  deserializeTraineeFromBinaryFile(File file);
Десериализует Trainee из двоичного файла, имя файла задается экземпляром класса File. Предполагается, что данные в файл записаны в формате предыдущего упражнения. 
 
public static String  serializeTraineeToJsonString(Trainee trainee);
Сериализует Trainee в формате Json в текстовую строку. 

public static Trainee  deserializeTraineeFromJsonString(String json);
Десериализует Trainee из текстовой строки с Json-представлением Trainee.

 public static void  serializeTraineeToJsonFile(File file, Trainee trainee);
Сериализует Trainee в формате Json в файл, имя файла задается экземпляром класса File. 

public static Trainee  deserializeTraineeFromJsonFile(File file);
Десериализует Trainee из файла с Json-представлением Trainee, имя файла задается экземпляром класса File.


Проверьте работу тестов в консольном окне, запишите все классы и измененный pom.xml на сервер (не забудьте изменить текст сообщения в git commit!) и убедитесь, что на сервере все тесты также проходят успешно (см. Занятие 1, п.15-19)

