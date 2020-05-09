package net.thumbtack.school.ttschool;

import java.io.Serializable;
import java.util.Objects;

public class Trainee implements Serializable {
    private static final long serialVersionUID = 35098454568987545L;

    private String firstName, lastName;
    private int rating;

    public Trainee(String firstName, String lastName, int rating) throws TrainingException {
        setFirstName( firstName );
        setLastName( lastName );
        setRating( rating );
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws TrainingException {
        if ( firstName != null && !firstName.equals( "" ) ) {
            this.firstName = firstName;
        }
        else {
            throw new TrainingException( TrainingErrorCode.TRAINEE_WRONG_FIRSTNAME );
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws TrainingException {
        if ( (lastName != null) && (!lastName.equals( "" )) ) {
            this.lastName = lastName;
        }
        else {
            throw new TrainingException( TrainingErrorCode.TRAINEE_WRONG_LASTNAME );
        }
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) throws TrainingException {
        if ( rating >= 1 & rating <= 5 ) {
            this.rating = rating;
        }
        else {
            throw new TrainingException( TrainingErrorCode.TRAINEE_WRONG_RATING );
        }
    }

    public String getFullName() {
        return String.format( "%s %s", firstName, lastName );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Trainee trainee = (Trainee) o;
        return rating == trainee.rating &&
                Objects.equals( firstName, trainee.firstName ) &&
                Objects.equals( lastName, trainee.lastName );
    }

    @Override
    public int hashCode() {

        return Objects.hash( firstName, lastName, rating );
    }
}
