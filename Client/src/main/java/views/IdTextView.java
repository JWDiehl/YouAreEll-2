package views;

import models.Id;

public class IdTextView {
    //Declares a private variable that will hold the ID
    private Id id;

    //Constructor that initializes instance of IDTextView
    public IdTextView(Id idToDisplay) {

        this.id = idToDisplay;
    }

    //toString() method of the object class
    @Override public String toString() {

        return this.id.toString();
    } 
}