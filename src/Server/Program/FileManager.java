package Server.Program;

import Lib.Data.Worker;
import Server.Program.CSVObjectTransform;



import java.io.*;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * Operations with file.
 */
public class FileManager {
    private String envVariable;
    private CSVObjectTransform csvog;

    public FileManager(String envVariable) {
        this.envVariable = envVariable;
        this.csvog = new CSVObjectTransform();
    }


    /**
     * Writes collection to a file.
     *
     * @param collection Collection to write.
     */
    public void writeCollection(TreeSet<Worker> collection) {
        File file = new File(System.getenv().get(envVariable));
        if (System.getenv().get(envVariable) != null) {
            if (file.canRead() && !file.isDirectory() && file.isFile()) {
                try (FileWriter out = new FileWriter(new File(System.getenv().get(envVariable)))) {
                    String content = "";
                    int count = 0;
                    for (Worker worker : collection) {
                        if (count != 0) content += "\n";
                        content += this.csvog.routeToCSVLine(worker);
                        count += 1;
                    }
                    out.write(content);
                    Console.println("Коллекция успешна сохранена в файл!");
                } catch (IOException e) {
                    Console.printerror("Загрузочный файл является директорией/не может быть открыт!");
                } catch (ParseException e) {
                    Console.printerror("Ошибка формата данных");
                }
            } else Console.printerror("Файл не читается, либо это директория!");
        } else Console.printerror("Системная переменная с загрузочным файлом не найдена!");
    }


    /**
     * Read collection from file.
     *
     * @return collection
     */
    public TreeSet<Worker> readCollection() {
        File file = new File(System.getenv().get(envVariable));
        if (System.getenv().get(envVariable) != null) {
            if (file.canRead() && !file.isDirectory() && file.isFile()) {
                try (InputStreamReader in = new InputStreamReader(new FileInputStream(System.getenv().get(envVariable)))) {
                    BufferedReader reader = new BufferedReader(in);
                    TreeSet<String> lines = new TreeSet<>();
                    String element;
                    while ((element = reader.readLine()) != null) {
                        lines.add(element);
                    }
                    TreeSet<Worker> collection = new TreeSet<>();
                    if (lines != null) for (String fileWorker : lines) {
                        Worker workerFromFile = this.csvog.csvLineToWorker(fileWorker);
                        if (workerFromFile != null) collection.add(workerFromFile);
                    }
                    Console.println("Коллекция успешна загружена!");
                    return collection;

                } catch (FileNotFoundException exception) {
                    Console.printerror("Загрузочный файл не найден!");
                } catch (NoSuchElementException exception) {
                    Console.printerror("Загрузочный файл пуст!");
                } catch (NullPointerException exception) {
                    Console.printerror("В загрузочном файле не обнаружена необходимая коллекция!");
                } catch (IllegalStateException exception) {
                    Console.printerror("Непредвиденная ошибка!");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else Console.printerror("Файл не читается, либо это директория!");
        } else Console.printerror("Системная переменная с загрузочным файлом не найдена!");
        return new TreeSet<Worker>();

    }

    @Override
    public String toString() {
        String string = "FileManager (класс для работы с загрузочным файлом)";
        return string;
    }
}
