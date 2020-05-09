package net.thumbtack.school.hiring.models;

public class HireWorkerTransfer {
    private Worker worker;
    private Vacancy vacancy;

    public HireWorkerTransfer(Worker worker, Vacancy vacancy) {
        setWorker(worker);
        setVacancy(vacancy);
    }

    public Worker getWorker() {
        return worker;
    }

    private void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    private void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
}
