package net.thumbtack.school.file;

import com.google.gson.Gson;
import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;

import java.io.*;

public class FileService {

    //1
    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(new File(fileName), array);
    }

    //2
    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(array);
        }
    }

    //3
    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    //4
    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] byteFromAFile = new byte[(int)file.length()];
            fileInputStream.read(byteFromAFile);
            return byteFromAFile;
        }
    }

    //5
    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byteArrayOutputStream.write(array);
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray())) {
                int size = byteArrayInputStream.available();
                byte[] evenIndexes = new byte[size % 2 + size / 2];
                for (int index = 0; index < evenIndexes.length; index++) {
                    evenIndexes[index] = (byte) byteArrayInputStream.read();
                    byteArrayInputStream.skip(1);
                }
                return evenIndexes;
            }
        }
    }

    //6
    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFileBuffered(new File(fileName), array);
    }

    //7
    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            bufferedOutputStream.write(array, 0, array.length);
        }
    }

    //8
    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        return readByteArrayFromBinaryFileBuffered(new File(fileName));
    }

    //9
    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            byte[] byteFromAFile = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(byteFromAFile);
            return byteFromAFile;
        }
    }

    //10
    public static void writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
//        byte[] coordinates = ByteBuffer
//                .allocate(16)
//                .putInt(rect.getTopLeft().getX())
//                .putInt(rect.getTopLeft().getY())
//                .putInt(rect.getBottomRight().getX())
//                .putInt(rect.getBottomRight().getY())
//                .array();
//        writeByteArrayToBinaryFileBuffered(file, coordinates);
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))) {
            dataOutputStream.writeInt(rect.getTopLeft().getX());
            dataOutputStream.writeInt(rect.getTopLeft().getY());
            dataOutputStream.writeInt(rect.getBottomRight().getX());
            dataOutputStream.writeInt(rect.getBottomRight().getY());
        }
    }

    //11
    public static Rectangle readRectangleFromBinaryFile(File file) throws IOException, ColorException {
//        IntBuffer intBuffer = ByteBuffer
//                .wrap(readByteArrayFromBinaryFileBuffered(file))
//                .asIntBuffer();
//        int[] coordinates = new int[intBuffer.remaining()];
//        intBuffer.get(coordinates);
//        return  new Rectangle(coordinates[0],coordinates[1], coordinates[2],coordinates[3], Color.RED);
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            return new Rectangle(dataInputStream.readInt(),
                    dataInputStream.readInt(),
                    dataInputStream.readInt(),
                    dataInputStream.readInt(),
                    Color.RED);
        }
    }

    //12
    public static void writeRectangleArrayToBinaryFile(File file, Rectangle[] rects) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
            for (Rectangle rect : rects) {
                randomAccessFile.writeInt(rect.getTopLeft().getX());
                randomAccessFile.writeInt(rect.getTopLeft().getY());
                randomAccessFile.writeInt(rect.getBottomRight().getX());
                randomAccessFile.writeInt(rect.getBottomRight().getY());
            }
        }
    }

    //13
    public static Rectangle[] readRectangleArrayFromBinaryFileReverse(File file) throws IOException, ColorException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            int length = (int) randomAccessFile.length() / 16;
            Rectangle[] rectangles = new Rectangle[length];
            for (int index = 0; index < length; index++) {
                randomAccessFile.seek(randomAccessFile.length() - 16 * (index + 1));
                rectangles[index] = new Rectangle(randomAccessFile.readInt(),
                        randomAccessFile.readInt(),
                        randomAccessFile.readInt(),
                        randomAccessFile.readInt(),
                        Color.RED);
            }
            return rectangles;
        }
    }

    //14
    public static void writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException {
        writeRectangleToTextFileUniversal(file, rect, ' ');
    }

    //15
    public static Rectangle readRectangleFromTextFileOneLine(File file) throws IOException, ColorException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String[] coordinates = bufferedReader.readLine().split(" ");
            return new Rectangle(Integer.valueOf(coordinates[0]),
                    Integer.valueOf(coordinates[1]),
                    Integer.valueOf(coordinates[2]),
                    Integer.valueOf(coordinates[3]),
                    Color.RED);
        }

    }

    //16
    public static void writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException {
        writeRectangleToTextFileUniversal(file, rect, '\n');
    }

    //17
    public static Rectangle readRectangleFromTextFileFourLines(File file) throws IOException, ColorException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            return new Rectangle(Integer.valueOf(bufferedReader.readLine()),
                    Integer.valueOf(bufferedReader.readLine()),
                    Integer.valueOf(bufferedReader.readLine()),
                    Integer.valueOf(bufferedReader.readLine()),
                    Color.RED);
        }

    }

    //18
    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        writeTraineeToTextFileUniversal(file, trainee, ' ');
    }

    //19
    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException, TrainingException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String[] data = bufferedReader.readLine().split(" ");
            return new Trainee(data[0], data[1], Integer.valueOf(data[2]));
        }
    }

    //20
    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        writeTraineeToTextFileUniversal(file, trainee, '\n');
    }

    //21
    public static Trainee readTraineeFromTextFileThreeLines(File file) throws IOException, TrainingException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            return new Trainee(bufferedReader.readLine(),
                    bufferedReader.readLine(),
                    Integer.valueOf(bufferedReader.readLine()));
        }
    }

    //22
    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(trainee);
        }
    }

    //23
    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            return (Trainee) objectInputStream.readObject();
        }
    }

    //24
    public static String serializeTraineeToJsonString(Trainee trainee) {
        return new Gson().toJson(trainee);
    }

    //25
    public static Trainee deserializeTraineeFromJsonString(String json) {
        return new Gson().fromJson(json, Trainee.class);
    }

    //26
    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            new Gson().toJson(trainee, bufferedWriter);
        }
    }

    //27
    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return new Gson().fromJson(bufferedReader, Trainee.class);
        }
    }

    private static void writeRectangleToTextFileUniversal(File file, Rectangle rect, char separate) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"))) {
            bufferedWriter.write(String.format("%d%c%d%c%d%c%d", rect.getTopLeft().getX(),
                    separate,
                    rect.getTopLeft().getY(),
                    separate,
                    rect.getBottomRight().getX(),
                    separate,
                    rect.getBottomRight().getY()));
        }
    }

    private static void writeTraineeToTextFileUniversal(File file, Trainee trainee, char separate) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            bufferedWriter.write(String.format("%s%c%s%c%d", trainee.getFirstName(),
                    separate,
                    trainee.getLastName(),
                    separate,
                    trainee.getRating()));
        }
    }

}
