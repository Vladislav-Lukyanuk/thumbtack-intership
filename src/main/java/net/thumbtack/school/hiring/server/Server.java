package net.thumbtack.school.hiring.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hiring.Controllers.*;
import net.thumbtack.school.hiring.models.Employer;
import net.thumbtack.school.hiring.models.Skill;
import net.thumbtack.school.hiring.models.Vacancy;
import net.thumbtack.school.hiring.models.Worker;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Server {
    private static String loadPath;
    private static String savePath;

    private static Gson gson = registerTypes();

    private static Gson registerTypes() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(new TypeToken<HashMap<Employer, UUID>>() {
        }.getType(), new CustomConverterHashMapEmployerUUID());

        builder.registerTypeAdapter(new TypeToken<HashMap<Worker, UUID>>() {
        }.getType(), new CustomConverterHashMapWorkerUUID());

        builder.registerTypeAdapter(new TypeToken<HashMap<Vacancy, UUID>>() {
        }.getType(), new CustomConverterHashMapVacancyUUID());

        builder.registerTypeAdapter(new TypeToken<HashMap<Vacancy, HashMap<Skill, Boolean>>>() {
        }.getType(), new CustomConverterHashMapVacancyHashMapSkillBoolean());

        builder.registerTypeAdapter(new TypeToken<HashMap<Worker, HashMap<String, Integer>>>() {
        }.getType(), new CustomConverterHashMapWorkerHashMapStringInteger());

        return builder.create();

    }

    public static String getLoadPath() {
        return loadPath;
    }

    public static String getSavePath() {
        return savePath;
    }

    public static void main(String[] args) {
        Options options = new Options();

        Option load = new Option("l", "load", true, "load file path");
        load.setRequired(false);
        options.addOption(load);

        Option save = new Option("s", "save", true, "save file path");
        save.setRequired(false);
        options.addOption(save);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Vacancies server", options);
            System.exit(1);
            return;
        }

        loadPath = cmd.getOptionValue("load");
        savePath = cmd.getOptionValue("save");
        startServer(loadPath);
    }

    public static void startServer(String savedDataFileName) {
        if (savedDataFileName == null) {
            DataBase.getDataBase();
            return;
        }

        try (BufferedReader dr = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File(savedDataFileName))))) {

            String employers = dr.readLine();
            DataBase.getDataBase().setEmployers(
                    gson.fromJson(employers,
                            new TypeToken<HashMap<Employer, UUID>>() {
                            }.getType()));

            String workers = dr.readLine();
            DataBase.getDataBase().setWorkers(
                    gson.fromJson(workers,
                            new TypeToken<HashMap<Worker, UUID>>() {
                            }.getType()));

            String allSkills = dr.readLine();
            DataBase.getDataBase().setAllSkills(
                    gson.fromJson(allSkills,
                            new TypeToken<HashSet<Skill>>() {
                            }.getType()));

            String employersVacancies = dr.readLine();
            DataBase.getDataBase().setEmployersVacancies(
                    gson.fromJson(employersVacancies,
                            new TypeToken<HashMap<Vacancy, UUID>>() {
                            }.getType()));

            String vacancyRequirements = dr.readLine();
            DataBase.getDataBase().setVacancyRequirements(
                    gson.fromJson(vacancyRequirements,
                            new TypeToken<HashMap<Vacancy, HashMap<Skill, Boolean>>>() {
                            }.getType()));

            String workersSkills = dr.readLine();
            DataBase.getDataBase().setWorkersSkills(
                    gson.fromJson(workersSkills,
                            new TypeToken<HashMap<Worker, HashMap<String, Integer>>>() {
                            }.getType()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void stopServer(String saveDataFileName) {
        if (saveDataFileName != null) {
            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(new File(saveDataFileName))))) {

                String employers = gson.toJson(DataBase.getDataBase().getEmployers(), new TypeToken<HashMap<Employer, UUID>>() {
                }.getType());
                bw.write(employers);
                bw.newLine();

                String workers = gson.toJson(DataBase.getDataBase().getWorkers(), new TypeToken<HashMap<Worker, UUID>>() {
                }.getType());
                bw.write(workers);
                bw.newLine();

                String allSkills = gson.toJson(DataBase.getDataBase().getAllSkills());
                bw.write(allSkills);
                bw.newLine();

                String employersVacancies = gson.toJson(DataBase.getDataBase().getEmployersVacancies(), new TypeToken<HashMap<Vacancy, UUID>>() {
                }.getType());
                bw.write(employersVacancies);
                bw.newLine();

                String vacancyRequirements = gson.toJson(DataBase.getDataBase().getVacancyRequirements(), new TypeToken<HashMap<Vacancy, HashMap<Skill, Boolean>>>() {
                }.getType());
                bw.write(vacancyRequirements);
                bw.newLine();

                String workersSkills = gson.toJson(DataBase.getDataBase().getWorkersSkills(), new TypeToken<HashMap<Worker, HashMap<String, Integer>>>() {
                }.getType());
                bw.write(workersSkills);
                bw.newLine();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
