package net.thumbtack.school.hiring.Controllers;

import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.errors.ServerException;
import net.thumbtack.school.hiring.models.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DataBase {
    private HashMap<Employer, UUID> employers;
    private HashMap<Worker, UUID> workers;
    //когда-либо добавленные скилы
    private HashSet<Skill> allSkills;
    private HashMap<Vacancy, UUID> employersVacancies;
    private HashMap<Vacancy, HashMap<Skill, Boolean>> vacancyRequirements;
    private HashMap<Worker, HashMap<String, Integer>> workersSkills;

    private static DataBase dataBase;

    private DataBase() {
        employers = new HashMap<>();
        workers = new HashMap<>();
        allSkills = new HashSet<>();
        vacancyRequirements = new HashMap<>();
        workersSkills = new HashMap<>();

        employersVacancies = new HashMap<>();
    }

    public static DataBase getDataBase() {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }

    //для тестов
    public void resetDataBase() {
        employers.clear();
        workers.clear();
        allSkills.clear();
        vacancyRequirements.clear();
        workersSkills.clear();
        employersVacancies.clear();
    }

    //загрузка базы данных
    /////////////////////////////////////////////////////////////////////////////////////////

    public HashMap<Employer, UUID> getEmployers() {
        return employers;
    }

    public void setEmployers(HashMap<Employer, UUID> employers) {
        this.employers = employers;
    }

    public HashMap<Worker, UUID> getWorkers() {
        return workers;
    }

    public void setWorkers(HashMap<Worker, UUID> workers) {
        this.workers = workers;
    }

    public HashSet<Skill> getAllSkills() {
        return allSkills;
    }

    public void setAllSkills(HashSet<Skill> allSkills) {
        this.allSkills = allSkills;
    }

    public HashMap<Vacancy, UUID> getEmployersVacancies() {
        return employersVacancies;
    }

    public void setEmployersVacancies(HashMap<Vacancy, UUID> employersVacancies) {
        this.employersVacancies = employersVacancies;
    }

    public HashMap<Vacancy, HashMap<Skill, Boolean>> getVacancyRequirements() {
        return vacancyRequirements;
    }

    public void setVacancyRequirements(HashMap<Vacancy, HashMap<Skill, Boolean>> vacancyRequirements) {
        this.vacancyRequirements = vacancyRequirements;
    }

    public HashMap<Worker, HashMap<String, Integer>> getWorkersSkills() {
        return workersSkills;
    }

    public void setWorkersSkills(HashMap<Worker, HashMap<String, Integer>> workersSkills) {
        this.workersSkills = workersSkills;
    }


    /////////////////////////////////////////////////////////////////////////////////////////

    HashMap<String, Integer> getWorkerViewSkills(String login, UUID token) throws ServerException {
        User user = getUser(token);
        if (!user.getClass().getName().equals(Employer.class.getName()))
            throw new ServerException(ServerError.USER_IS_NOT_A_EMPLOYER);

        Worker worker = getWorkerByLogin(login);
        if (worker == null)
            throw new ServerException(ServerError.USER_NOT_EXIST);

        return workersSkills.get(worker);
    }

    Set<Vacancy> getVacancies() {
        HashSet<Vacancy> vacancies = new HashSet<>();

        for (Vacancy vacancy : employersVacancies.keySet()) {
            if (vacancy.isActive())
                vacancies.add(vacancy);
        }

        return vacancies;
    }

    HashSet<Skill> getAllSkills(UUID token) throws ServerException {
        getUser(token);
        return allSkills;
    }

    HashSet<Vacancy> getEmployerVacancies(UUID token) throws ServerException {
        User user = getUser(token);
        if (!user.getClass().getName().equals(Employer.class.getName()))
            throw new ServerException(ServerError.USER_IS_NOT_A_EMPLOYER);

        HashSet<Vacancy> vacancies = new HashSet<>();
        for (Vacancy vacancy : employersVacancies.keySet()) {
            if (employersVacancies.get(vacancy).equals(token))
                vacancies.add(vacancy);
        }

        return vacancies;
    }

    Set<WorkerView> getWorkersWithNecessarySkills(Vacancy vacancy, UUID token) throws ServerException {
        if (!employersVacancies.get(vacancy).equals(token))
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);

        HashSet<WorkerView> workersWithNecessarySkill = new HashSet<>();
        HashMap<Skill, Boolean> necessarySkill = vacancyRequirements.get(vacancy);

        for (Worker worker : workers.keySet()) {
            if (worker.isActive()) {
                HashMap<String, Integer> skills = workersSkills.get(worker);
                int count = 0;

                for (Skill vacancyRequirement : necessarySkill.keySet()) {
                    if (necessarySkill.get(vacancyRequirement)) {
                        count--;
                        Integer level = skills.get(vacancyRequirement.getName());
                        if (level != null && level >= vacancyRequirement.getLevel())
                            count++;
                    }
                }

                if (count == 0) {
                    workersWithNecessarySkill.add(
                            new WorkerView(
                                    worker.getEmail(),
                                    worker.getFirstName(),
                                    worker.getLastName(),
                                    worker.getPatronymic(),
                                    worker.getLogin())
                    );
                }
            }
        }

        return workersWithNecessarySkill;
    }

    Set<WorkerView> getWorkersWithAllSkills(Vacancy vacancy, UUID token) throws ServerException {
        if (!employersVacancies.get(vacancy).equals(token))
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);

        HashSet<WorkerView> workersWithNecessarySkill = new HashSet<>();
        HashMap<Skill, Boolean> necessarySkill = vacancyRequirements.get(vacancy);

        for (Worker worker : workers.keySet()) {
            if (worker.isActive()) {
                HashMap<String, Integer> skills = workersSkills.get(worker);
                int count = 0;

                for (Skill vacancyRequirement : necessarySkill.keySet()) {
                    Integer level = skills.get(vacancyRequirement.getName());
                    if (level != null && level >= vacancyRequirement.getLevel())
                        count++;
                }

                if (count == necessarySkill.size()) {
                    workersWithNecessarySkill.add(
                            new WorkerView(
                                    worker.getEmail(),
                                    worker.getFirstName(),
                                    worker.getLastName(),
                                    worker.getPatronymic(),
                                    worker.getLogin())
                    );
                }
            }
        }

        return workersWithNecessarySkill;
    }

    Set<WorkerView> getWorkersWithAnyLevelSkills(Vacancy vacancy, UUID token) throws ServerException {
        if (!employersVacancies.get(vacancy).equals(token))
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);

        HashSet<WorkerView> workersWithNecessarySkill = new HashSet<>();
        HashMap<Skill, Boolean> necessarySkill = vacancyRequirements.get(vacancy);

        for (Worker worker : workers.keySet()) {
            if (worker.isActive()) {
                HashMap<String, Integer> skills = workersSkills.get(worker);
                int count = 0;

                for (Skill vacancyRequirement : necessarySkill.keySet()) {
                    Integer level = skills.get(vacancyRequirement.getName());
                    if (level != null)
                        count++;
                }

                if (count == necessarySkill.size()) {
                    workersWithNecessarySkill.add(
                            new WorkerView(
                                    worker.getEmail(),
                                    worker.getFirstName(),
                                    worker.getLastName(),
                                    worker.getPatronymic(),
                                    worker.getLogin())
                    );
                }
            }
        }

        return workersWithNecessarySkill;
    }

    Set<WorkerView> getWorkersWithOneSkills(Vacancy vacancy, UUID token) throws ServerException {
        if (!employersVacancies.get(vacancy).equals(token))
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);

        HashSet<WorkerView> workersWithNecessarySkill = new HashSet<>();
        HashMap<Skill, Boolean> necessarySkill = vacancyRequirements.get(vacancy);

        for (Worker worker : workers.keySet()) {
            if (worker.isActive()) {
                HashMap<String, Integer> skills = workersSkills.get(worker);
                boolean found = false;

                for (Skill vacancyRequirement : necessarySkill.keySet()) {
                    Integer level = skills.get(vacancyRequirement.getName());
                    if (level != null && level >= vacancyRequirement.getLevel()) {
                        found = true;
                        break;
                    }

                }

                if (found) {
                    workersWithNecessarySkill.add(
                            new WorkerView(
                                    worker.getEmail(),
                                    worker.getFirstName(),
                                    worker.getLastName(),
                                    worker.getPatronymic(),
                                    worker.getLogin())
                    );
                }
            }
        }

        return workersWithNecessarySkill;
    }


    HashSet<Vacancy> getActiveEmployerVacancies(UUID token) throws ServerException {
        User user = getUser(token);
        if (!user.getClass().getName().equals(Employer.class.getName()))
            throw new ServerException(ServerError.USER_IS_NOT_A_EMPLOYER);

        HashSet<Vacancy> vacancies = new HashSet<>();
        for (Vacancy vacancy : employersVacancies.keySet()) {
            if (employersVacancies.get(vacancy).equals(token) & vacancy.isActive())
                vacancies.add(vacancy);
        }

        return vacancies;
    }

    HashSet<Vacancy> getNonActiveEmployerVacancies(UUID token) throws ServerException {
        User user = getUser(token);
        if (!user.getClass().getName().equals(Employer.class.getName()))
            throw new ServerException(ServerError.USER_IS_NOT_A_EMPLOYER);

        HashSet<Vacancy> vacancies = new HashSet<>();
        for (Vacancy vacancy : employersVacancies.keySet()) {
            if (employersVacancies.get(vacancy).equals(token) & !vacancy.isActive())
                vacancies.add(vacancy);
        }

        return vacancies;
    }

    HashMap<Skill, Boolean> getVacancyRequirements(Vacancy vacancy) {
        return vacancyRequirements.get(vacancy);
    }

    void addVacancyRequirement(Vacancy vacancy, Skill skill, Boolean necessary, UUID token) throws ServerException {
        if (vacancyRequirements.containsKey(vacancy) && !vacancyRequirements.get(vacancy).containsKey(skill)) {
            if (employersVacancies.get(vacancy).equals(token)) {
                vacancyRequirements.get(vacancy).put(skill, necessary);
                addToAllSkills(skill);
                return;
            }

            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);
        }

        throw new ServerException(ServerError.VACANCY_REQUIREMENT_ALREADY_EXIST);
    }

    void removeVacancyRequirement(Vacancy vacancy, Skill skill, UUID token) throws ServerException {
        if (vacancyRequirements.containsKey(vacancy) && vacancyRequirements.get(vacancy).containsKey(skill)) {
            if (employersVacancies.get(vacancy).equals(token)) {
                vacancyRequirements.get(vacancy).remove(skill);
                return;
            }

            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);
        }

        throw new ServerException(ServerError.VACANCY_REQUIREMENT_IS_NOT_EXIST);
    }

    void replaceVacancyRequirement(Vacancy vacancy, Skill skill, Boolean necessary, UUID token) throws ServerException {
        if (vacancyRequirements.containsKey(vacancy) && vacancyRequirements.get(vacancy).containsKey(skill)) {
            if (employersVacancies.get(vacancy).equals(token)) {
                vacancyRequirements.get(vacancy).remove(skill);
                vacancyRequirements.get(vacancy).put(skill, necessary);
                return;
            }

            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);
        }

        throw new ServerException(ServerError.VACANCY_REQUIREMENT_IS_NOT_EXIST);
    }

    void addVacancy(Vacancy vacancy, UUID token) throws ServerException {
        User user = getUser(token);
        if (!user.getClass().getName().equals(Employer.class.getName()))
            throw new ServerException(ServerError.USER_IS_NOT_A_EMPLOYER);

        if (!employersVacancies.containsKey(vacancy)) {
            employersVacancies.put(vacancy, token);
            vacancyRequirements.put(vacancy, new HashMap<>());
            return;
        }

        throw new ServerException(ServerError.VACANCY_ALREADY_EXIST);
    }

    void removeVacancy(Vacancy vacancy, UUID token) throws ServerException {
        if (employersVacancies.containsKey(vacancy)) {
            if (employersVacancies.get(vacancy).equals(token)) {
                employersVacancies.remove(vacancy);
                vacancyRequirements.remove(vacancy);
                return;
            }
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);
        }

        throw new ServerException(ServerError.VACANCY_IS_NOT_EXIST);
    }

    void editVacancy(Vacancy newVacancy, UUID token) throws ServerException {
        Vacancy vacancy = getVacancyById(newVacancy.getId());
        if (employersVacancies.get(vacancy).equals(token)) {
            employersVacancies.remove(vacancy);
            employersVacancies.put(newVacancy, token);

            HashMap<Skill, Boolean> hashMap = vacancyRequirements.get(vacancy);
            vacancyRequirements.remove(vacancy);
            vacancyRequirements.put(vacancy, hashMap);
            return;
        }
        throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);
    }

    private Vacancy getVacancyById(UUID id) throws ServerException {
        for (Vacancy vacancy : employersVacancies.keySet()) {
            if (vacancy.getId().equals(id)) {
                return vacancy;
            }
        }

        throw new ServerException(ServerError.VACANCY_IS_NOT_EXIST);
    }

    private Worker getWorkerByLogin(String login) {
        for (Worker worker : workers.keySet()) {
            if (worker.getLogin().equals(login))
                return worker;
        }

        return null;
    }

    public UUID getWorkerToken(Worker worker) {
        return workers.get(worker);
    }

    private Worker getWorker(UUID token) {
        for (Worker worker : workers.keySet()) {
            if (workers.get(worker).equals(token))
                return worker;
        }

        return null;
    }

    private Employer getEmployer(UUID token) {
        for (Employer employer : employers.keySet()) {
            if (employers.get(employer).equals(token))
                return employer;
        }

        return null;
    }

    HashMap<String, Integer> getWorkerSkills(UUID token) {
        return workersSkills.get(getWorker(token));
    }

    void addWorkerSkill(Skill skill, UUID token) throws ServerException {
        Worker worker = getWorker(token);

        if (worker == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);

        if (workersSkills.containsKey(worker) && !workersSkills.get(worker).containsKey(skill.getName())) {
            workersSkills.get(worker).put(skill.getName(), skill.getLevel());
            addToAllSkills(skill);
            return;
        }

        throw new ServerException(ServerError.SKILL_ALREADY_EXIST);
    }

    void removeWorkerSkill(Skill skill, UUID token) throws ServerException {
        Worker worker = getWorker(token);

        if (worker == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);

        if (workersSkills.containsKey(worker) && workersSkills.get(worker).containsKey(skill.getName())) {
            workersSkills.get(worker).remove(skill.getName());
            return;
        }

        throw new ServerException(ServerError.SKILL_IS_NOT_EXIST);
    }

    void editWorkerSkill(Skill skill, UUID token) throws ServerException {
        Worker worker = getWorker(token);

        if (worker == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);

        if (workersSkills.containsKey(worker) && workersSkills.get(worker).containsKey(skill.getName())) {
            workersSkills.get(worker).remove(skill.getName());
            workersSkills.get(worker).put(skill.getName(), skill.getLevel());
            return;
        }

        throw new ServerException(ServerError.SKILL_IS_NOT_EXIST);
    }

    //пополнение базы. любые новые вакансии будут добавлены
    private void addToAllSkills(Skill skill) {
        allSkills.add(skill);
    }

    private Boolean containUserByLogin(User user) {
        return workers.keySet().stream().anyMatch(s -> s.getLogin().equals(user.getLogin()))
                || employers.keySet().stream().anyMatch(s -> s.getLogin().equals(user.getLogin()));
    }

    private Boolean containUserByToken(UUID token) {
        boolean containInEmployers = employers.values().contains(token);

        boolean containInWorkers = workers.values().contains(token);

        return containInEmployers || containInWorkers;
    }


    User getUser(UUID token) throws ServerException {

        Worker worker = getWorker(token);
        Employer employer = getEmployer(token);

        if (worker == null && employer == null)
            throw new ServerException(ServerError.USER_NOT_EXIST);

        return worker == null ? employer : worker;
    }

    private UUID getUserToken(User user) throws ServerException {
        return loginUser(new UserLogin(user.getLogin(), user.getPassword()));
    }

    UUID loginUser(UserLogin userLogin) throws ServerException {
        for (Employer employer : employers.keySet()) {
            if (userLogin.getLogin().equals(employer.getLogin()) & userLogin.getPassword().equals(employer.getPassword())) {
                return employers.get(employer);
            }
        }

        for (Worker worker : workers.keySet()) {
            if (userLogin.getLogin().equals(worker.getLogin()) & userLogin.getPassword().equals(worker.getPassword())) {
                return workers.get(worker);
            }
        }

        throw new ServerException(ServerError.USER_NOT_EXIST);
    }

    void logOutUser(UUID token) throws ServerException {
        if (containUserByToken(token)) {
            return;
        }

        throw new ServerException(ServerError.USER_NOT_EXIST);
    }

    void addUser(User user, UUID token) throws ServerException {

        if (!containUserByLogin(user)) {
            if (user.getClass().equals(Employer.class)) {
                employers.put((Employer) user, token);
            } else {
                workers.put((Worker) user, token);
                workersSkills.put((Worker) user, new HashMap<>());
            }
            return;
        }

        throw new ServerException(ServerError.USER_ALREADY_EXIST);
    }

    void replaceUser(User user, UUID token) throws ServerException {
        if (!token.equals(getUserToken(user)))
            throw new ServerException(ServerError.REQUEST_IS_NOT_ALLOW);

        removeUser(token);
        addUser(user, token);
    }

    void removeUser(UUID token) throws ServerException {
        User user = getUser(token);

//        if (user == null) {
//            throw new ServerException(ServerError.USER_NOT_EXIST);
//        }

        if (user.getClass().equals(Employer.class)) {
            employers.remove(user);
        } else {
            workers.remove(user);
        }
    }

    HashSet<Vacancy> getVacanciesWithAllSkills(UUID token) throws ServerException {
        User user = getUser(token);

        if (!user.getClass().equals(Worker.class)) {
            throw new ServerException(ServerError.USER_IS_NOT_A_WORKER);
        }

        HashSet<Vacancy> vacancies = new HashSet<>();
        HashMap<String, Integer> workerSkills = workersSkills.get(user);

        for (Vacancy vacancy : vacancyRequirements.keySet()) {
            if (vacancy.isActive()) {
                HashMap<Skill, Boolean> requirements = vacancyRequirements.get(vacancy);
                int count = 0;

                for (Skill skill : requirements.keySet()) {
                    Integer level = workerSkills.get(skill.getName());

                    if (level != null && level >= skill.getLevel())
                        count++;
                }

                if (count == requirements.size())
                    vacancies.add(vacancy);
            }
        }
        return vacancies;
    }

    HashSet<Vacancy> getVacanciesWithNecessarySkills(UUID token) throws ServerException {
        User user = getUser(token);

        if (!user.getClass().equals(Worker.class)) {
            throw new ServerException(ServerError.USER_IS_NOT_A_WORKER);
        }

        HashSet<Vacancy> vacancies = new HashSet<>();
        HashMap<String, Integer> workerSkills = workersSkills.get(user);

        for (Vacancy vacancy : vacancyRequirements.keySet()) {
            if (vacancy.isActive()) {
                HashMap<Skill, Boolean> requirements = vacancyRequirements.get(vacancy);
                int count = 0;

                for (Skill skill : requirements.keySet()) {
                    if (requirements.get(skill)) {
                        count--;
                        Integer level = workerSkills.get(skill.getName());

                        if (level != null && level >= skill.getLevel())
                            count++;
                    }
                }

                if (count == 0)
                    vacancies.add(vacancy);
            }
        }
        return vacancies;
    }

    HashSet<Vacancy> getVacanciesWithAnyLevelSkills(UUID token) throws ServerException {
        User user = getUser(token);

        if (!user.getClass().equals(Worker.class)) {
            throw new ServerException(ServerError.USER_IS_NOT_A_WORKER);
        }

        HashSet<Vacancy> vacancies = new HashSet<>();
        HashMap<String, Integer> workerSkills = workersSkills.get(user);

        for (Vacancy vacancy : vacancyRequirements.keySet()) {
            if (vacancy.isActive()) {
                HashMap<Skill, Boolean> requirements = vacancyRequirements.get(vacancy);
                int count = 0;

                for (Skill skill : requirements.keySet()) {
                    Integer level = workerSkills.get(skill.getName());

                    if (level != null)
                        count++;
                }

                if (count == requirements.size())
                    vacancies.add(vacancy);
            }
        }
        return vacancies;
    }

//    private <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
//        Comparator<K> valueComparator = new Comparator<K>() {
//            public int compare(K k1, K k2) {
//                return map.get(k1).compareTo(map.get(k2));
//            }
//        };
//        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
//        sortedByValues.putAll(map);
//        return sortedByValues;
//    }

    Set<Vacancy> getVacanciesWithOneSkills(UUID token) throws ServerException {
        User user = getUser(token);

        if (!user.getClass().equals(Worker.class)) {
            throw new ServerException(ServerError.USER_IS_NOT_A_WORKER);
        }

        HashMap<Vacancy, Integer> vacancies = new HashMap<>();

        HashMap<String, Integer> workerSkills = workersSkills.get(user);

        for (Vacancy vacancy : vacancyRequirements.keySet()) {
            if (vacancy.isActive()) {
                HashMap<Skill, Boolean> requirements = vacancyRequirements.get(vacancy);
                int count = 0;

                for (Skill skill : requirements.keySet()) {
                    Integer level = workerSkills.get(skill.getName());

                    if (level != null && level >= skill.getLevel())
                        count++;
                }

                if (count > 0)
                    vacancies.put(vacancy, count);
            }
        }

        LinkedHashMap<Vacancy, Integer> outPut = vacancies.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (obj1, obj2) -> obj1,
                        LinkedHashMap::new
                ));

        return outPut.keySet();
    }

}
